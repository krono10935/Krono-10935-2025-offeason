package frc.robot.Subsystems.Vision;

import edu.wpi.first.math.geometry.Pose3d;

public interface VisionIO {
    /**
     * Represents a frame of vision data.
     * This record contains information about the target detected in the frame,
     *@param hasTarget Indicates if a target was detected in the frame.
     *@param timeStampSeconds The timestamp of the frame in seconds.
     *@param latency The latency of the frame in seconds.
     *@param estimatedTheyThemPose The estimated pose of the target in the frame.
     *@param poseAmbiguity The ambiguity of the estimated pose, where 0 is no ambiguity and 1 is maximum ambiguity.
     *@param averageTargetDistanceMeters The average distance to the target in meters.
     *@param targetCount The number of targets detected in the frame.
     */
    record VisionFrame(
       boolean hasTarget,
       double timeStampSeconds,
       double latency,
       Pose3d estimatedTheyThemPose,
       double poseAmbiguity,
       double averageTargetDistanceMeters,
       int targetCount
    )
    {}

}
