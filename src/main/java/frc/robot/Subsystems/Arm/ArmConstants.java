package frc.robot.subsystems.Arm;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.BasicMotorConfig;
<<<<<<< Updated upstream
=======
import frc.robot.ArmCalculator;
import frc.robot.Constants;
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

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
<<<<<<< Updated upstream
        HOME(Rotation2d.fromDegrees(0)),
        L1(Rotation2d.fromDegrees(180)),
        L2(Rotation2d.fromDegrees(90)),
        L3(Rotation2d.fromDegrees(360)),
        CoralIntakeLevel(Rotation2d.fromDegrees(169)),
        UNKNOWN(Rotation2d.fromDegrees(67)); //tuff
=======
        HOME(0),
        L1(46.99 * Constants.INCH_TO_CM),
        L2(38.95 * Constants.INCH_TO_CM),
        L3(31.12 *  Constants.INCH_TO_CM),
        CoralIntakeLevel(0),
        UNKNOWN(67); //tuff
>>>>>>> Stashed changes

        public final Rotation2d angle; // Angle in radians

        ArmLevel(Rotation2d angle){
            this.angle=angle;
        }
    }

    
}
