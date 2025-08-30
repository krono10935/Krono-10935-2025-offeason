package frc.robot.Subsystems.Arm;

import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.BasicMotorConfig;

public class ArmConstants {
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


    public enum desiredAngles {
        L1,
        L2,
        L3,
    }
}
