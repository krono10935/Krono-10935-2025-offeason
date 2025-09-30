package frc.robot.subsystems.Arm;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.BasicMotorConfig;
import frc.robot.ArmCalculator;

public class ArmConstants {
    public static BasicMotorConfig config = new BasicMotorConfig();
    
    //port for AbsEncoder which resets pos in the beginning of the game
    public static Integer DUTY_CYCLE_ENCODER_PORT =1 ;
    public static Double DUTY_CYCLE_ENCODER_ZERO_OFFSET = 0.0;

    public static class ArmFeedForwardInputs{
        Rotation2d AngularVelocity;
        Rotation2d AngularPositon;
        Rotation2d AngularAccelaration;
        public ArmFeedForwardInputs(Rotation2d AngularVelocity, Rotation2d AngularPosition, Rotation2d AngularAccelaration ){
            this.AngularVelocity = AngularVelocity;
            this.AngularPositon = AngularPosition;
            this.AngularAccelaration = AngularAccelaration;
        }

    }
    

    static {
        //port for AbsEncoder which resets pos in the beginning of the game
    
        
        //basic motor info
        config.motorConfig.name = "Arm Motor";
        config.motorConfig.id = 1;
        config.motorConfig.inverted = false;
        config.motorConfig.idleMode = BasicMotor.IdleMode.BRAKE;
        config.motorConfig.gearRatio = 46;




        //dummy values for pid
        config.slot0Config.pidConfig.kP = 5;
        config.slot0Config.pidConfig.kI = 2;
        config.slot0Config.pidConfig.kD = 1;
        config.slot0Config.pidConfig.tolerance=0.01;
        Double Kg = 0.5;

        config.slot0Config.feedForwardConfig.customFeedForward = new ArmFeedForward(Kg);
        
        


        //dummy values for sim
        config.simulationConfig.kA= 0;
        config.simulationConfig.kV=0;
        config.simulationConfig.armSimConfig.simulateGravity = false;
        config.simulationConfig.armSimConfig.armlengthMeters = 0.55;
        config.simulationConfig.armSimConfig.startingAngle = 0;
        config.simulationConfig.velocityStandardDeviation=0.01;
        config.simulationConfig.positionStandardDeviation=0.01;
        config.simulationConfig.momentOfInertia=0.01;
        

    }


    public enum ArmLevel {
        HOME(0),
        L1(0),
        L2(0),
        L3(0),
        CoralIntakeLevel(0),
        UNKNOWN(67); //tuff

        public final double height;
        public final Rotation2d angle;
        public final Translation2d[] panels;

        ArmLevel(double height){
            this.height = height;
            this.angle = ArmCalculator.armAngle(height);
            this.panels = new Translation2d[6];

            for(int i = 0; i < 6; i++){
                this.panels[i] = ArmCalculator.coordinateTranslation2d(height, i);
            }
        }
    }

    
}
