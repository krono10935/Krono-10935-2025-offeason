package frc.robot.Subsystems.drivetrain;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Subsystems.drivetrain.swerve.module.SwerveModuleConstants;

public class DrivetrainConstants {
    public enum HolonomicType{
        SWERVE,
        MECANUM
    }

    public static final HolonomicType HOLONOMIC_TYPE = HolonomicType.SWERVE;

    public static final Pose2d startPose2d = new Pose2d(2,7, new Rotation2d());

    public static final double MAX_LINEAR_SPEED = 4; // TODO find max linear speed (m/s)
    public static final double MIN_LINEAR_SPEED = 1; // m/s
    public static final double MAX_ANGULAR_SPEED = MAX_LINEAR_SPEED / SwerveModuleConstants.FRONT_LEFT.TRANSLATION.getNorm(); // degrees/s
    public static final double MIN_ANGULAR_SPEED = MIN_LINEAR_SPEED / SwerveModuleConstants.FRONT_LEFT.TRANSLATION.getNorm(); // degrees/s

    public static boolean shouldFlipPath(){
        var currentAlliance = DriverStation.getAlliance();

        // if (currentAlliance.isPresent()) {
        //     return currentAlliance.get() == DriverStation.Alliance.Red;
        // }
        // If no alliance is set, do not flip the path.
        return false;
    }

    public static Pose2d[][] reef = new Pose2d[][] {
            {   // Right
                    new Pose2d(5.3, 4, Rotation2d.fromDegrees(180.0)),
                    new Pose2d(4.9, 4.7, Rotation2d.fromDegrees(-120.0)),
                    new Pose2d(4.1, 4.7, Rotation2d.fromDegrees(-60.0)),
                    new Pose2d(3.7, 4, Rotation2d.fromDegrees(0.0)),
                    new Pose2d(4.1, 3.3, Rotation2d.fromDegrees(60.0)),
                    new Pose2d(4.9, 3.3, Rotation2d.fromDegrees(120.0))
            },
            {   // Left
                    new Pose2d(5.7, 4, Rotation2d.fromDegrees(180.0)),
                    new Pose2d(5, 5, Rotation2d.fromDegrees(-120.0)),
                    new Pose2d(3.9, 5, Rotation2d.fromDegrees(-60.0)),
                    new Pose2d(3.3, 4, Rotation2d.fromDegrees(0.0)),
                    new Pose2d(3.9, 3, Rotation2d.fromDegrees(60.0)),
                    new Pose2d(5, 3, Rotation2d.fromDegrees(120.0))
            }
    };
    public static Pose2d[] mid_reef = new Pose2d[] {
        // mid
        new Pose2d(3.5, 4, Rotation2d.fromDegrees(0.0)), // panel 0
        new Pose2d(4, 3.15, Rotation2d.fromDegrees(60.0)), // panel 1
        new Pose2d(4.95, 3.15, Rotation2d.fromDegrees(120.0)), // panel 2
        new Pose2d(5.5, 4, Rotation2d.fromDegrees(180.0)), // panel 3
        new Pose2d(4.95, 4.85, Rotation2d.fromDegrees(240.0)), // panel 4
        new Pose2d(4, 4.85, Rotation2d.fromDegrees(300)), // panel 5
    };

}
