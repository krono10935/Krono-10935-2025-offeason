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

        config.simulationConfig.kV = 0;
        config.simulationConfig.kA = 0;
        config.simulationConfig.armSimConfig.simulateGravity = true;
        config.simulationConfig.armSimConfig.armlengthMeters = 0.55;
        config.simulationConfig.armSimConfig.startingAngle = 0;

    }


    public enum desiredAngles {
        L1(Math.PI),
        L2(0.5*Math.PI),
        L3(2*Math.PI);

        public double angle; // Angle in radians

        private desiredAngles(double angle){
            this.angle = angle;
        }
    }
}
