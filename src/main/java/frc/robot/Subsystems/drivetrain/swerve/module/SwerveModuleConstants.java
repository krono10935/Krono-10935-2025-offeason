package frc.robot.subsystems.drivetrain.swerve.module;

import edu.wpi.first.math.geometry.Translation2d;
import io.github.captainsoccer.basicmotor.ctre.talonfx.BasicTalonFXConfig;

public enum SwerveModuleConstants {
    FRONT_LEFT(0,1), 
    FRONT_RIGHT(2,3), 
    BACK_LEFT(4,5), 
    BACK_RIGHT(6,7);

    public final BasicTalonFXConfig STEERING_CONFIG = new BasicTalonFXConfig();
    public final BasicTalonFXConfig DRIVING_CONFIG = new BasicTalonFXConfig();

    public final Translation2d TRANSLATION = new Translation2d();

    public final int CANCODER_ID; 
    public final double ZERO_OFFSET;


    private SwerveModuleConstants(int steerID, int driveID){
        STEERING_CONFIG.motorConfig.id = steerID;   
        DRIVING_CONFIG.motorConfig.id = driveID;
    }


    public static Translation2d[] getModuleTranslations(){
        return new Translation2d[]{
            FRONT_LEFT.TRANSLATION,
            FRONT_RIGHT.TRANSLATION,
            BACK_LEFT.TRANSLATION,
            BACK_RIGHT.TRANSLATION
        };
    }
}
