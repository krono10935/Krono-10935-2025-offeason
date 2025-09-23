package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public class Constants {
    public static final BooleanSupplier isRedSupplier = () -> true;

    public static class FieldConstants{
        static{
            final Pose2d feederPose = new Pose2d(0, 0, null) ;
        }
    }
}
