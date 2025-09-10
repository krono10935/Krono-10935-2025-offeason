package frc.robot.subsystems.Vision;

import static edu.wpi.first.units.Units.Seconds;

import java.lang.ModuleLayer.Controller;
import java.lang.StackWalker.Option;
import java.util.Optional;
import java.util.function.Supplier;

import org.opencv.aruco.EstimateParameters;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.RobotController;
import frc.robot.subsystems.Vision.VisionConstants.CamerasConstants;

public class VisionIOPhoton implements VisionIO {

    private final PhotonCamera camera;
    private final PhotonPoseEstimator poseEstimator;
    private final Supplier<Pose2d> lastPoseSupplier;

    public VisionIOPhoton(CamerasConstants camerasConstants, Supplier<Pose2d> lastPoseSupplier) {
        this.camera = new PhotonCamera(camerasConstants.CAMERA_NAME);
        this.poseEstimator = new PhotonPoseEstimator(
                VisionConstants.FIELD_LAYOUT,
                camerasConstants.MAIN_STRATEGY,
                camerasConstants.ROBOT_TO_CAMERA);
        poseEstimator.setMultiTagFallbackStrategy(camerasConstants.ALTERNATE_STRATEGY);
        this.lastPoseSupplier = lastPoseSupplier;
    }

    public VisionFrame getNewFrame(PhotonPipelineResult result){
        if(!result.hasTargets()){
            return emptyFrame;
        }

        poseEstimator.setReferencePose(lastPoseSupplier.get());

        Optional<EstimatedRobotPose> estimatedPose = poseEstimator.update(result);

        if(estimatedPose.isEmpty()){
            return emptyFrame;
        }

        EstimatedRobotPose pose = estimatedPose.get();

                // Default to "invalid" values
                double ambiguity = Double.NaN;
                double avgDistance = 0.0;

                if (!result.getTargets().isEmpty()) {
                    // Compute average distance safely
                    avgDistance = result.getTargets().stream()
                        .mapToDouble(t -> t.getBestCameraToTarget().getTranslation().getNorm())
                        .average()
                        .orElse(0.0);

                    // Ambiguity depends on single vs multiple targets
                    if (result.getTargets().size() > 1 && result.multitagResult.isPresent()) {
                        ambiguity = result.multitagResult.get().estimatedPose.ambiguity;
                    } else {
                        ambiguity = result.getTargets().get(0).getPoseAmbiguity();
                    }
                }

        return new VisionFrame(
            true,
            result.getTimestampSeconds(),
            RobotController.getMeasureTime().in(Seconds) - result.getTimestampSeconds(),
            pose.estimatedPose,
            ambiguity,
            avgDistance,
            result.getTargets().size()
            
            
        );
        

    }

    @Override
    public void updateInputs(VisionInputs inputs) {
        inputs.isConnected = camera.isConnected();
        
        if(!inputs.isConnected){
            inputs.visionFrames = new VisionFrame[]{emptyFrame};
            inputs.targetIDs = new int[0];
            return;
        }

        var result = camera.getAllUnreadResults();

        if (result.isEmpty()) {
            inputs.visionFrames = new VisionFrame[]{emptyFrame};
            inputs.targetIDs = new int[0];
            return;
        }

        inputs.visionFrames = result.stream()
            .map(this::getNewFrame)
            .toArray(VisionFrame[]::new);

        inputs.targetIDs = result.stream()
            .flatMap(r -> r.getTargets().stream())
            .mapToInt(t -> t.getFiducialId())
            .toArray();
    }

}
