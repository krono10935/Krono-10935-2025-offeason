package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.math.geometry.Pose2d;

public class Constants {
    public static final BooleanSupplier isRedSupplier = () -> true;
    public static final double INCH_TO_CM = 2.54;
    
    public static class FieldConstants{
        public static final Pose2d feederPose = new Pose2d(0, 0, null);
        public static final Pose2d reefPose = new Pose2d(0, 0, null);
        
    }
}
