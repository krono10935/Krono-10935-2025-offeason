package frc.robot.Subsystems.Arm;

import edu.wpi.first.math.geometry.Rotation2d;
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

        config.pidConfig.kP = 100;
        config.pidConfig.kI = 10;
        config.pidConfig.kD = 1;

        config.simulationConfig.kV = 1;
        config.simulationConfig.kA = 1;
        config.simulationConfig.armSimConfig.simulateGravity = false;
        config.simulationConfig.armSimConfig.armlengthMeters = 0.55;
        config.simulationConfig.armSimConfig.startingAngle = 0;
        config.simulationConfig.velocityStandardDeviation=0.01;
        config.simulationConfig.positionStandardDeviation=0.01;
        config.simulationConfig.momentOfInertia=0.01;
        

    }


    public enum ArmLevel {
        HOME(Rotation2d.fromDegrees(0)),
        L1(Rotation2d.fromDegrees(180)),
        L2(Rotation2d.fromDegrees(90)),
        L3(Rotation2d.fromDegrees(360)),
        UNKNOWN(Rotation2d.fromDegrees(67)); //tuff

        public final Rotation2d angle; // Angle in radians

        ArmLevel(Rotation2d angle){
            this.angle=angle;
        }
    }
}
