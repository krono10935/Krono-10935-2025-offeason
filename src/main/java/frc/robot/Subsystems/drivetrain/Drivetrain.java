package frc.robot.Subsystems.drivetrain;


import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Subsystems.drivetrain.mecanum.Mecanum;
import frc.robot.Subsystems.drivetrain.swerve.Swerve;
import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.Logger;

public abstract class Drivetrain extends SubsystemBase {

    @AutoLog
    public static class DrivetrainInputs {
        public Rotation2d gyroAngle = Rotation2d.kZero;
        public ChassisSpeeds speeds = new ChassisSpeeds();
    }
    // With eager singleton initialization, any static variables/fields used in the 
    // constructor must appear before the "INSTANCE" variable so that they are initialized 
    // before the constructor is called when the "INSTANCE" variable initializes.

    /**
     * The Singleton instance of this DrivetrainSubsystem. Code should use
     * the {@link #getInstance()} method to get the single instance (rather
     * than trying to construct an instance of this class.)
     */
    private static Drivetrain INSTANCE;

    private final DrivetrainInputsAutoLogged inputs = new DrivetrainInputsAutoLogged();

    /**
     * Returns the Singleton instance of this DrivetrainSubsystem. This static method
     * should be used, rather than the constructor, to get the single instance
     * of this class. For example: {@code DrivetrainSubsystem.getInstance();}
     */
    @SuppressWarnings("WeakerAccess")
    public static Drivetrain getInstance() {
        if (INSTANCE == null) {
            INSTANCE = switch (DrivetrainConstants.HOLONOMIC_TYPE) {
                case SWERVE -> new Swerve();
                case MECANUM -> new Mecanum();
            };
        }
        return INSTANCE;
    }

    /**
     * Creates a new instance of this DrivetrainSubsystem. This constructor
     * is private since this class is a Singleton. Code should use
     * the {@link #getInstance()} method to get the singleton instance.
     */
    protected Drivetrain() {
        // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
        //       in the constructor or in the robot coordination class, such as RobotContainer.
        //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
        //       such as SpeedControllers, Encoders, DigitalInputs, etc.
    }

    /**
     * drives the robot at relative speed
     *
     * @param speeds the target speed of the robot
     */
    public void drive(ChassisSpeeds speeds) {
        var discreteSpeeds = ChassisSpeeds.discretize(speeds, 0.02);
        setChassisSpeed(discreteSpeeds);
        Logger.recordOutput("drive train/target speed", discreteSpeeds);
    }

    protected abstract void setChassisSpeed(ChassisSpeeds speeds);


    public Rotation2d getGyroAngle() {
        return inputs.gyroAngle;
    }

    public ChassisSpeeds getChassisSpeeds() {
        return inputs.speeds;
    }

}

