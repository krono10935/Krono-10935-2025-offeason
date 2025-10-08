package frc.robot.Subsystems.Gripper;

import edu.wpi.first.math.system.plant.DCMotor;
import io.github.captainsoccer.basicmotor.BasicMotorConfig;
import io.github.captainsoccer.basicmotor.BasicMotor.IdleMode;
import io.github.captainsoccer.basicmotor.BasicMotorConfig.SlotConfig;
import io.github.captainsoccer.basicmotor.rev.BasicSparkConfig;
import io.github.captainsoccer.basicmotor.controllers.Controller.ControlMode;

public class GripperConstants {
    public enum GamePiece{
        None,
        Coral,
        Algae,
        Unknown;
    }

    public static final double MAX_MOTOR_TEMPERATURE = 40;
    public static final boolean START_WITH_CORAL = true;

    public static final double WHEEL_RADIUS = 0; // in meters 

    public static final double WHEEL_CIRCUMFERENCE_METERS = WHEEL_RADIUS * 2 * Math.PI;

    public static final double MAX_VELOCITY = 1; // Maximum velocity in rotations per second

    public static final double ALGAE_TORQUE = 0; // Target torque for the gripper holding algae
    public static final ControlMode ALGAE_MODE = ControlMode.TORQUE; 
    public static final double CORAL_INTAKE_POWER = -0.21; // Target power in percentage for the coral's intake
    public static final double CORAL_EJECT_POWER = -0.21; // Target power in precentage for the coral's ejection'
    public static final double WEAK_CORAL_EJECT_POWER = CORAL_EJECT_POWER * 0.8; // Target power in precentage for the coral's ejection
    public static final ControlMode CORAL_MODE = ControlMode.POSITION;

    public static final int BEAM_BREAK_CHANNEL = 9; // Placeholder, set to the correct channel

    public static final BasicMotorConfig motorConfig = new BasicSparkConfig();
    static {
        motorConfig.motorConfig.name = "Gripper motor";
        motorConfig.motorConfig.id = 19; // PLaceholder
        motorConfig.motorConfig.inverted = false; // Set to true if the motor is inverted
        motorConfig.motorConfig.idleMode = IdleMode.COAST; // Brake may cause issues with the coral moving around while being held
        motorConfig.motorConfig.gearRatio = 1; // Placeholder
        motorConfig.motorConfig.unitConversion=1;
        motorConfig.motorConfig.motorType = DCMotor.getNEO(1);

        ((BasicSparkConfig)motorConfig).currentLimitConfig.stallCurrentLimit = 20;
        ((BasicSparkConfig)motorConfig).currentLimitConfig.secondaryCurrentLimit = 25;


        SlotConfig positionConfig = motorConfig.slot0Config;
        positionConfig.pidConfig.kP = 10; // Proportional gain
        positionConfig.pidConfig.kI = 0; // Integral gain
        positionConfig.pidConfig.kD = 0; // Derivative gain
        
        SlotConfig torqueConfig = motorConfig.slot1Config;
        torqueConfig.pidConfig.kP = 0; // Proportional gain
        torqueConfig.pidConfig.kI = 0; // Integral gain
        torqueConfig.pidConfig.kD = 0; // Derivative gain

        // motorConfig.constraintsConfig.maxOutput = 6; // Maximum output in Volts
        // motorConfig.constraintsConfig.minOutput = -12; // Minimum output in Volts
        motorConfig.simulationConfig.kV = 0;
        motorConfig.simulationConfig.kA = 0;
        motorConfig.simulationConfig.momentOfInertia = 1;
    }
}
