package frc.robot.Subsystems.Vision;

import edu.wpi.first.math.geometry.Pose3d;
import org.littletonrobotics.junction.AutoLog;

public interface VisionIO {

    /**
     * Updates the vision inputs.
     * @param inputs The vision inputs to be updated.
     */
    void update(VisionInputs inputs);

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
    {
        /**
         * An empty VisionFrame instance representing no detected target.
         */
        public static final VisionFrame EMPTY = new VisionFrame(false,
         0.0,
         0.0,
         new Pose3d(),
         1.0,
         0.0,
         0);
    }

    /**
     * Represents the inputs for the vision system.
    */
    @AutoLog
    public static class VisionInputs{
        /**
         * Indicates if the vision system is connected.
         */ 
        public boolean isConnected = false;
        /**
         * An array of vision frames detected by the vision system.
         */
        public VisionFrame[] visionFrames = new VisionFrame[0];
        /**
         * An array of target IDs detected by the vision system.
         */
        public int[] targetIDs = new int[0];
    }

}
