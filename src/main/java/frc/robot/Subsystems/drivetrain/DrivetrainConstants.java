package frc.robot.subsystems.drivetrain;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.subsystems.drivetrain.swerve.module.SwerveModuleConstants;

public class DrivetrainConstants {
    public enum HolonomicType{
        SWERVE,
        MECANUM
    }

    public static final HolonomicType HOLONOMIC_TYPE = HolonomicType.SWERVE;

    public static final Pose2d startPose2d = new Pose2d(0,0, new Rotation2d());

    public static final double MAX_LINEAR_SPEED = 4; // TODO find max linear speed (m/s)
    public static final double MIN_LINEAR_SPEED = 1; // m/s
    public static final double MAX_ANGULAR_SPEED = MAX_LINEAR_SPEED / SwerveModuleConstants.FRONT_LEFT.TRANSLATION.getNorm();
    public static final double MIN_ANGULAR_SPEED = MIN_LINEAR_SPEED / SwerveModuleConstants.FRONT_LEFT.TRANSLATION.getNorm();

}
