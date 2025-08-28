package frc.robot.Subsystems.drivetrain;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public class DrivetrainConstants {
    public enum HolonomicType{
        SWERVE,
        MECANUM
    }

    public static final HolonomicType HOLONOMIC_TYPE = HolonomicType.SWERVE;

    public static final Pose2d startPose2d = new Pose2d(0,0, new Rotation2d());

}
