package frc.robot.Subsystems.Vision;

import static edu.wpi.first.units.Units.Seconds;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.math.geometry.Pose2d; 
import edu.wpi.first.wpilibj.RobotController;
import frc.robot.Subsystems.Vision.VisionConstants.CamerasConstants;

public class VisionIOPhoton implements VisionIO {
        /**
         * the camera
         */
        private final PhotonCamera camera;
        /**
         * the pose estimator
         */
        private final PhotonPoseEstimator poseEstimator;
        /**
         * the refrence pose supplier
         */
        private final Supplier<Pose2d> referencePoseSupplier;

        /**
         * this build the poseEstimator using PhotonVision
         * @param camerasConstants refrence to the Constants
         * @param referencePoseSupplier the refrence pose supplier
         */
        public VisionIOPhoton(CamerasConstants camerasConstants, Supplier<Pose2d> referencePoseSupplier){
        
            camera = new PhotonCamera(camerasConstants.CAMERA_NAME);
            poseEstimator = new PhotonPoseEstimator(VisionConstants.FIELD_LAYOUT, camerasConstants.MAIN_STRATEGY, camerasConstants.ROBOT_TO_CAMERA );
            poseEstimator.setMultiTagFallbackStrategy(camerasConstants.ALTERNATE_STRATEGY);
            this.referencePoseSupplier = referencePoseSupplier;
        }

    /**
     * Generate a vision frame from a PhotonVision result.
     *
     * @param result the photon result containing detected targets and pose info
     * @return a VisionFrame object representing the processed vision data
     */
    private VisionFrame generateFrame(PhotonPipelineResult result) {
        /**
         * If there are no detected targets in the result, return an empty vision frame.
         */
        if (!result.hasTargets()) {
            return VisionFrame.EMPTY;
        }

        /**
         * Update the pose estimator with the current reference pose (from a supplier).
         */
        poseEstimator.setReferencePose(referencePoseSupplier.get());

        /**
         * Attempt to estimate the robot's pose using the photon result.
         */
        Optional<EstimatedRobotPose> estimatedPose = poseEstimator.update(result);

        /**
         * If no pose estimation was possible, return an empty vision frame.
         */
        if (estimatedPose.isEmpty()) {
            return VisionFrame.EMPTY;
        }

        /**
         * Extract the valid estimated robot pose.
         */
        EstimatedRobotPose pose = estimatedPose.get();

        /**
         * Variables to store pose ambiguity and average distance to detected targets.
         */
        double poseAmbiguity;
        double TargetDistanceSumMeters = 0.0;

        /**
         * If the multitag solver was used (multiple tags detected together).
         */
        if (result.multitagResult.isPresent()) {
            /**
             * Get ambiguity value from the multitag result.
             */
            poseAmbiguity = result.multitagResult.get().estimatedPose.ambiguity;

            /**
             * Compute the average distance to all detected targets.
             */
            for (var target : result.targets) {
                double distanceToTag = target.getBestCameraToTarget()
                                            .getTranslation()
                                            .getNorm();
                TargetDistanceSumMeters += distanceToTag;
            }
        } 
        /**
         * If only a single tag was detected.
         */
        else {
            /**
             * Use ambiguity from the first target.
             */
            poseAmbiguity = result.getTargets().get(0).poseAmbiguity;

            /**
             * Compute the distance to the first target.
             */
            TargetDistanceSumMeters = result.getTargets().get(0)
                                                .getBestCameraToTarget()
                                                .getTranslation()
                                                .getNorm();
        }

        /**
         * Return a VisionFrame containing:
         * - a flag indicating vision data is valid
         * - the timestamp from PhotonVision
         * - the latency between PhotonVision result and current robot time
         * - the estimated robot pose
         * - the pose ambiguity
         * - the average distance to targets
         * - the number of detected targets
         */
        return new VisionFrame(
            true,
            result.getTimestampSeconds(),
            RobotController.getMeasureTime().in(Seconds) - result.getTimestampSeconds(),
            pose.estimatedPose,
            poseAmbiguity,
            TargetDistanceSumMeters / result.getTargets().size(),
            result.getTargets().size()
        );
    }

    /**
     * Update vision inputs with the latest camera data.
     *
     * @param inputs the VisionInputs object to populate with camera information
     */
    @Override
    public void update(VisionInputs inputs) {
        /**
         * Check if the camera is connected.
         */
        inputs.isConnected = camera.isConnected();

        /**
         * If the camera is not connected, set visionFrames to an empty frame and return.
         */
        if (!inputs.isConnected) {
            inputs.visionFrames = new VisionFrame[]{VisionFrame.EMPTY};
            return;
        }

        /**
         * Retrieve all unread results from the camera.
         */
        var result = camera.getAllUnreadResults();

        /**
         * Initialize the array of vision frames to match the number of results.
         */
        inputs.visionFrames = new VisionFrame[result.size()];
        /**
         * List to collect all detected target IDs.
         */
        ArrayList<Integer> ids = new ArrayList<>();
        /**
         * Process each PhotonVision result into a VisionFrame.
         */
        for (int i = 0; i < result.size(); i++) {
            /**
             * Convert the result into a VisionFrame and store it.
             */
            inputs.visionFrames[i] = generateFrame(result.get(i));

            /**
             * Collect the fiducial IDs of all detected targets in this result.
             */
            for (var target : result.get(i).targets) {
                ids.add(target.getFiducialId());
            }

            
        }
        /**
             * Store the collected target IDs as an int array in the inputs.
             */
        inputs.targetIDs = ids.stream().mapToInt(Integer::intValue).toArray();
    }


}
