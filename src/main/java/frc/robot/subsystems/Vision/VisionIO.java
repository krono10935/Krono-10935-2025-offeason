package frc.robot.subsystems.Vision;

import org.littletonrobotics.junction.AutoLog;
import edu.wpi.first.math.geometry.Pose3d;

/**
 * Interface representing a vision camera system.
 * Implementations provide methods to update inputs and convert raw camera data
 * into structured vision frames for the robot code.
 */
public interface VisionIO {

    /**
     * Represents a single "frame" or snapshot of vision data from a camera.
     * Contains the estimated robot pose, target ambiguity, and metadata.
     */
    public class VisionFrame {
        /** True if at least one target was detected in this frame */
        public final boolean hasTarget;
        /** Timestamp when the frame was captured (seconds) */
        public final double timeStampSeconds;
        /** Latency between capture and processing (seconds) */
        public final double latency;
        /** Estimated 3D pose of the robot */
        public final Pose3d targetPose;
        /** Ambiguity/confidence of the pose estimation */
        public final double targetPoseAmbiguity;
        /** Average distance to all detected targets (meters) */
        public final double avrageDistanceToTargetsMeters;
        /** Number of fiducial targets detected in this frame */
        public final int numTargets;

        /**
         * Constructs a VisionFrame with the provided data.
         */
        public VisionFrame(boolean hasTarget, double timeStampSeconds, double latency, Pose3d targetPose,
                double targetPoseAmbiguity, double avrageDistanceToTargetsMeters, int numTargets) {
            this.hasTarget = hasTarget;
            this.timeStampSeconds = timeStampSeconds;
            this.latency = latency;
            this.targetPose = targetPose;
            this.targetPoseAmbiguity = targetPoseAmbiguity;
            this.avrageDistanceToTargetsMeters = avrageDistanceToTargetsMeters;
            this.numTargets = numTargets;
        }
    }

    /** Shared "empty" frame to represent no targets or invalid data */
    public VisionFrame EMPTY = new VisionFrame(false, 0, 0, new Pose3d(), 1, 0, 0);

    /**
     * Container for auto-logged vision data.
     * @see org.littletonrobotics.junction.AutoLog
     */
    @AutoLog
    public static class VisionInputs {
        /** True if the camera is currently connected */
        public boolean isConnected = false;
        /** Array of recent vision frames captured by this camera */
        public VisionFrame[] visionFrames = new VisionFrame[0];
        /** Array of all detected fiducial target IDs in the frames */
        public int[] targetIDs = new int[0];
    }

    /**
     * Updates the provided VisionInputs object with the latest camera data.
     *
     * @param inputs The VisionInputs instance to populate
     */
    public void updateInputs(VisionInputs inputs);
}
