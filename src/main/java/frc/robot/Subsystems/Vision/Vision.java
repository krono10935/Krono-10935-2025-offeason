package frc.robot.Subsystems.Vision;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Subsystems.Vision.VisionConstants.CamerasConstants;

/**
 * The {@code Vision} subsystem is responsible for:
 * <ul>
 *   <li>Managing multiple vision cameras.</li>
 *   <li>Collecting and validating pose estimates from AprilTag detections.</li>
 *   <li>Rejecting invalid or ambiguous vision data.</li>
 *   <li>Passing valid pose measurements to the robot’s pose estimator.</li>
 *   <li>Logging diagnostic data for debugging and performance analysis.</li>
 * </ul>
 *
 * This subsystem integrates with AdvantageKit for logging and WPILib's field layout
 * for validating tag poses.
 */
public class Vision extends SubsystemBase {

  /**
   * A functional interface used by the {@link Vision} subsystem to provide validated
   * pose estimates to a consumer, typically the robot's pose estimator.
   */
  @FunctionalInterface
  public interface VisionConsumer {
      /**
       * Accepts a vision pose estimate.
       *
       * @param pose The estimated 2D pose of the robot on the field.
       * @param timeStamp The timestamp of when the measurement was captured, in seconds.
       * @param stdDevs A 3x1 matrix representing the standard deviations (x, y, theta)
       *                used to indicate uncertainty of the measurement.
       */
      void accept(Pose2d pose, double timeStamp, Matrix<N3, N1> stdDevs);
  }

  /**
   * Represents a single vision camera. Handles updating its inputs,
   * logging to AdvantageKit, and storing calibration constants.
   */
  private static class VisionCamera {
      /** The hardware/software interface for the camera (e.g., PhotonVision). */
      private final VisionIO camera;
      /** Auto-logged input container for this specific camera. */
      private final VisionInputsAutoLogged inputs;
      /** Calibration and tuning constants specific to this camera. */
      private final CamerasConstants constants;

      /**
       * Creates a {@code VisionCamera} from a provided IO implementation.
       *
       * @param camera The camera IO implementation (e.g., {@link VisionIOPhoton}).
       * @param cameraName Name of the camera for identification/logging.
       * @param constants Calibration constants for this camera.
       */
      public VisionCamera(VisionIO camera, String cameraName, CamerasConstants constants) {
          this.camera = camera;
          this.inputs = new VisionInputsAutoLogged();
          this.constants = constants;
      }

      /**
       * Creates a {@code VisionCamera} using constants and a reference pose supplier.
       *
       * @param constants The camera calibration constants.
       * @param lastPose A supplier providing the robot's current estimated pose.
       */
      public VisionCamera(CamerasConstants constants, Supplier<Pose2d> lastPose) {
          this(new VisionIOPhoton(constants, lastPose), constants.CAMERA_NAME, constants);
      }

      /**
       * Updates the camera inputs by polling the underlying IO implementation.
       */
      public void update() {
          camera.update(inputs);
          Logger.processInputs(constants.CAMERA_NAME, inputs);
      }
  }
  
  /** Array of all vision cameras active in this subsystem. */
  private final VisionCamera[] cameras;
  /** Consumer that receives validated pose data (e.g., PoseEstimator). */
  private final VisionConsumer consumer;

  /**
   * Constructs the {@code Vision} subsystem, initializing all cameras
   * defined in {@link CamerasConstants}.
   *
   * @param poseConsumer The consumer that will receive validated pose estimates.
   * @param lastPose Supplier providing the current reference robot pose.
   * @param ignoreFunnel A flag supplier to determine whether pose funneling should be ignored.
   */
  public Vision(VisionConsumer poseConsumer, Supplier<Pose2d> lastPose) {
      int numOfCameras = CamerasConstants.values().length;

      cameras = new VisionCamera[numOfCameras];
      CamerasConstants[] constants = CamerasConstants.values();

      for (int i = 0; i < numOfCameras; i++) {
          cameras[i] = new VisionCamera(constants[i], lastPose);
      }

      this.consumer = poseConsumer;
  }

  /**
   * Validates whether a given {@link Pose3d} lies within the physical field boundaries
   * and does not exceed the maximum allowable height deviation.
   *
   * @param pose The pose to validate.
   * @return True if the pose is inside the field and within height limits, false otherwise.
   */
  private boolean checkPoseLocation(Pose3d pose) {
      double poseX = pose.getX();
      double poseY = pose.getY();
      double poseZ = pose.getZ();

      if (poseX >= VisionConstants.FIELD_LAYOUT.getFieldLength() || poseX < 0) return false;
      if (poseY >= VisionConstants.FIELD_LAYOUT.getFieldWidth() || poseY < 0) return false;
      return !(Math.abs(poseZ) > VisionConstants.MAX_HEIGHT_DEV);
  }

  /**
   * Validates whether the ambiguity of a pose is acceptable, depending on whether
   * it comes from a single tag or multiple tags.
   *
   * @param ambiguity The ambiguity value (0.0 = perfect, 1.0 = highly ambiguous).
   * @param frame The vision frame containing target and pose information.
   * @return True if the ambiguity is below the threshold, false otherwise.
   */
  private boolean checkPoseAmbiguity(double ambiguity, VisionIO.VisionFrame frame) {
      boolean multiTag = frame.targetCount() > 1;
      if (multiTag) {
          return ambiguity <= VisionConstants.MAX_MULTI_AMBIGUTY;
      } else {
          return ambiguity <= VisionConstants.MAX_SINGLE_AMBIGUTY;
      }
  }

  /**
   * Computes the standard deviations (uncertainty) of a vision measurement
   * based on tag distance and tag count.
   *
   * @param averageTagDistance Average distance to the detected tags (metres).
   * @param tagCount The number of detected tags contributing to the measurement.
   * @param camera The camera that produced the measurement.
   * @return A 3x1 matrix containing (σx, σy, σθ).
   */
  private Matrix<N3, N1> getStdDevs(double averageTagDistance, double tagCount, VisionCamera camera) {
      double stdDevFactor = Math.pow(averageTagDistance, 2.0) / tagCount;

      double linearStdDev = Math.max(stdDevFactor * camera.constants.XY_STD_DEV_FACTOR, camera.constants.MIN_XY_STD_DEV);
      double angularStdDev = Math.max(stdDevFactor * camera.constants.THETA_STD_DEV_FACTOR, camera.constants.MIN_THETA_STD_DEV);

      return VecBuilder.fill(linearStdDev, linearStdDev, angularStdDev);
  }

  /**
   * The periodic update method:
   * <ul>
   *   <li>Updates all cameras.</li>
   *   <li>Logs camera inputs.</li>
   *   <li>Checks target visibility and retrieves their field locations.</li>
   *   <li>Validates estimated robot poses against field boundaries and ambiguity thresholds.</li>
   *   <li>Feeds valid poses into the {@link VisionConsumer} for pose estimation.</li>
   *   <li>Records both valid and rejected poses for diagnostics.</li>
   * </ul>
   */
  @Override
  public void periodic() {
      /** Collection of valid poses that passed all validation checks. */
      final ArrayList<Pose3d> validPoses = new ArrayList<>();
      /** Collection of poses rejected for being invalid or ambiguous. */
      final ArrayList<Pose3d> rejectedPoses = new ArrayList<>();
      /** List of detected tag locations in the field coordinate system. */
      final ArrayList<Translation3d> targetLocations = new ArrayList<>();

      for (VisionCamera camera : cameras) {
          camera.update();
          
          SmartDashboard.putBoolean(camera.constants.CAMERA_NAME + " Connected", camera.inputs.isConnected);

          for (int i = 0; i < camera.inputs.targetIDs.length; i++) {
              Optional<Pose3d> targetPose = VisionConstants.FIELD_LAYOUT.getTagPose(camera.inputs.targetIDs[i]);
              Consumer<Pose3d> addPoseToTargetLocations = pose3d -> targetLocations.add(pose3d.getTranslation());
              targetPose.ifPresent(addPoseToTargetLocations);
          }

          for (VisionIO.VisionFrame frame : camera.inputs.visionFrames) {
              if (!frame.hasTarget()) continue;

              if (!checkPoseLocation(frame.estimatedPose())) {
                  rejectedPoses.add(frame.estimatedPose());
                  continue;
              }

              if (!checkPoseAmbiguity(frame.poseAmbiguity(), frame)) {
                  rejectedPoses.add(frame.estimatedPose());
                  continue;
              }

              var stdDevs = getStdDevs(frame.averageTargetDistanceMeters(), frame.targetCount(), camera);

              consumer.accept(frame.estimatedPose().toPose2d(), frame.timeStampSeconds(), stdDevs);

              validPoses.add(frame.estimatedPose());
          }
      }
      Logger.recordOutput("Vision/Target Locations", targetLocations.toArray(new Translation3d[0]));
      Logger.recordOutput("Vision/Valid Poses", validPoses.toArray(new Pose3d[0]));
      Logger.recordOutput("Vision/Rejected Poses", rejectedPoses.toArray(new Pose3d[0]));
  }
}
