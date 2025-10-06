package frc.robot.Subsystems.Arm;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.BasicMotorConfig;
import io.github.captainsoccer.basicmotor.BasicMotorConfig.MotorConfig;
import io.github.captainsoccer.basicmotor.gains.ConstraintsGains.ConstraintType;
import io.github.captainsoccer.basicmotor.rev.BasicSparkConfig;
import frc.robot.ArmCalculator;
import frc.robot.Constants;

public class ArmConstants {
    public static BasicMotorConfig config = RobotBase.isReal()?new BasicSparkConfig():new BasicMotorConfig();
    
    //port for AbsEncoder which resets pos in the beginning of the game
    public static Integer DUTY_CYCLE_ENCODER_PORT =0 ;
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
        config.motorConfig.id = 20;
        config.motorConfig.inverted = false;
        config.motorConfig.idleMode = BasicMotor.IdleMode.COAST;
        config.motorConfig.gearRatio = 45;
        config.motorConfig.unitConversion=1;
        config.motorConfig.motorType= DCMotor.getNEO(1);
        

    
        




        //dummy values for pid
        config.slot0Config.pidConfig.kP = 14;
        config.slot0Config.pidConfig.kI = 0.07;
        config.slot0Config.pidConfig.kD = 0;
        config.slot0Config.pidConfig.tolerance=0.001;
        config.slot0Config.pidConfig.iZone=0.05;
        config.slot0Config.pidConfig.iMaxAccum=0.25;
        Double Kg = 0.84;
        config.constraintsConfig.constraintType = ConstraintType.LIMITED;
        config.constraintsConfig.minValue = 0 + ArmConstants.DUTY_CYCLE_ENCODER_ZERO_OFFSET;
        config.constraintsConfig.maxValue = 0.4 + ArmConstants.DUTY_CYCLE_ENCODER_ZERO_OFFSET;
        config.slot0Config.profileConfig.maximumMeasurementVelocity= 0.5;
        config.slot0Config.profileConfig.maximumMeasurementAcceleration = 0.35;
        config.constraintsConfig.rampRate = 0.05;
        config.constraintsConfig.maxOutput = 6;
        config.constraintsConfig.minOutput=-6;

        // config.slot0Config.feedForwardConfig.customFeedForward = new ArmFeedForward(0.84);
        config.slot0Config.feedForwardConfig.customFeedForward = 
        (position) ->
         Math.sin(Units.rotationsToRadians(position)) * 0.4;
        
        


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
        HOME(0.05),
        L1(0.2),
        L2(0.25),
        L3(0.35),
        UNKNOWN (0.144),
        CoralIntakeLevel(0.2)
        ;
        //L1(17.88 * Constants.INCH_TO_CM),
        // L2(31.12 * Constants.INCH_TO_CM),
        // L3(38.95 *  Constants.INCH_TO_CM),
        // CoralIntakeLevel(0),
        // UNKNOWN(67); //tuff

        public final double height;
        public final Rotation2d angle;
        public final Pose2d[][] panels;

        ArmLevel(double height){
            this.height = height;
            this.angle = Rotation2d.fromRotations(height);
            this.panels = new Pose2d[6][2];

            // for(int i = 0; i < 6; i++){
            //     System.out.println(this.name());
            //     this.panels[i] = ArmCalculator.coordinateTranslation2d(height, i);
            // }
        }
    }

    

    
}
