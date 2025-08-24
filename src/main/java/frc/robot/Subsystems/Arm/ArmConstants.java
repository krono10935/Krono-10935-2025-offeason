package frc.robot.Subsystems.Arm;

import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.BasicMotorConfig;

public class ArmConstants {
    public static double ARM_LENGTH = 60; //cm
    public static double ARM_MOUNT_HEIGHT = 50; //cm
    public static int ARM_MOTOR_ID = 1;
    public static double ARM_WEIGHT = 5; //kg
    public static double ARM_MOTOR_GEAR_RATIO;
    public static BasicMotorConfig config = new BasicMotorConfig();
    static {
        config.motorConfig.name = "Arm Motor";
        config.motorConfig.id = 1;
        config.motorConfig.inverted = false;
        config.motorConfig.idleMode = BasicMotor.IdleMode.BRAKE;
        //config.motorConfig.gearRatio

        config.pidConfig.kP = 0.1;
        config.pidConfig.kI = 0.1;
        config.pidConfig.kD = 0.1;

        //config.simulationConfig.kV
        //config.simulationConfig.kA
    }
}
