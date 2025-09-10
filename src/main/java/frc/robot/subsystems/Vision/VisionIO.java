package frc.robot.subsystems.Vision;

import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.LogTable.LogValue;

import edu.wpi.first.math.geometry.Pose3d;

public interface VisionIO {

    public class VisionFrame {
        public boolean hasTarget;
        public double timeStampSeconds;
        public double latency;
        public Pose3d targetPose;
        public double targetPoseAmbiguity;
        public double avrageDistanceToTargetsMeters;
        public int numTargets;

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


    public VisionFrame emptyFrame = new VisionFrame(false, 0, 0, new Pose3d(), 1, 0, 0);

    @AutoLog
    public static class VisionInputs {
        public boolean isConnected = false;
        public VisionFrame[] visionFrames = new VisionFrame[0];
        public int[] targetIDs = new int[0];
    }

    public void updateInputs(VisionInputs inputs);
}

