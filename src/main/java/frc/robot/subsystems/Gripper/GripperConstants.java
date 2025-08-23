package frc.robot.subsystems.Gripper;

import edu.wpi.first.math.system.plant.DCMotor;
import io.github.captainsoccer.basicmotor.BasicMotorConfig;
import io.github.captainsoccer.basicmotor.BasicMotor.IdleMode;
import io.github.captainsoccer.basicmotor.rev.BasicSparkConfig;
import io.github.captainsoccer.basicmotor.controllers.Controller.ControlMode;

public class GripperConstants {
    public static final boolean START_WITH_CORAL = false;
    public static final boolean START_WITH_ALGAE = false;

    public static final double WHEEL_RADIUS = 0; // in meters (3 inches)

    public static final double WHEEL_CIRCUMFERENCE_METERS = WHEEL_RADIUS * 2 * Math.PI;

    public static final double MAX_VELOCITY = 0; // Maximum velocity in rotations per second

    public static final double ALGAE_TORQUE = 0;
    public static final ControlMode ALGAE_MODE = ControlMode.TORQUE;
    public static final double CORAL_INTAKE_POWER = 0;
    public static final double CORAL_EJECT_POWER = 0;
    public static final ControlMode CORAL_MODE = ControlMode.POSITION;

    public static final BasicMotorConfig motorConfig = new BasicSparkConfig();
    static {
        motorConfig.motorConfig.name = "Gripper motor";
        motorConfig.motorConfig.id = 1; // Example ID, change as needed
        motorConfig.motorConfig.inverted = false; // Set to true if the motor is inverted
        motorConfig.motorConfig.idleMode = IdleMode.COAST;
        motorConfig.motorConfig.gearRatio = 1; // Example gear ratio, change as needed
        motorConfig.motorConfig.motorType = DCMotor.getNEO(1);

        motorConfig.pidConfig.kP = 0; // Proportional gain
        motorConfig.pidConfig.kI = 0; // Integral gain
        motorConfig.pidConfig.kD = 0; // Derivative gain

        motorConfig.feedForwardConfig.setpointFeedForward = 0; // Feedforward for setpoint
        motorConfig.feedForwardConfig.frictionFeedForward = 0; // Friction feedforward

        motorConfig.constraintsConfig.maxOutput = 0; // Maximum output in Volts
        motorConfig.constraintsConfig.minOutput = 0; // Minimum output in Volts

        motorConfig.simulationConfig.kV = 0;
        motorConfig.simulationConfig.kA = 0;

        motorConfig.profileConfig.maximumMeasurementVelocity = 0;
        motorConfig.profileConfig.maximumMeasurementAcceleration = 0;
    }
}
