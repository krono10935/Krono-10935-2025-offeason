package frc.robot.Subsystems.Arm;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
    /**
     * The Singleton instance of this ArmSubsystem. Code should use
     * the {@link #getInstance()} method to get the single instance (rather
     * than trying to construct an instance of this class.)
     */

    private final ArmIO io;
    private final ArmInputsAutoLogged inputs  = new ArmInputsAutoLogged();
    private static ArmSubsystem INSTANCE;

    /**
     * Returns the Singleton instance of this ArmSubsystem. This static method
     * should be used, rather than the constructor, to get the single instance
     * of this class. For example: {@code ArmSubsystem.getInstance();}
     */
    @SuppressWarnings("WeakerAccess")
    public static ArmSubsystem getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ArmSubsystem();
        }
        return INSTANCE;
    }

    /**
     * Creates a new instance of this ArmSubsystem. This constructor
     * is private since this class is a Singleton. Code should use
     * the {@link #getInstance()} method to get the singleton instance.
     */
    private ArmSubsystem() {
        io = RobotBase.isReal() ? new ArmRealMotorIO() : new ArmSimIO();

        // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
        //       in the constructor or in the robot coordination class, such as RobotContainer.
        //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
        //       such as SpeedControllers, Encoders, DigitalInputs, etc.
        io.resetEncoder();
    }


    @Override
    public void periodic() {
        io.update(inputs);
    }

    public void setAngle(double angle) {
        io.setAngle(angle);
    }

    public double currentAngle() {
        return inputs.currentAngle;
    }


}

