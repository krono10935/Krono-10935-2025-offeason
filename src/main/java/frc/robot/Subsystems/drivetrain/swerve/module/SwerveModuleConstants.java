package frc.robot.Subsystems.drivetrain.swerve.module;

import edu.wpi.first.math.geometry.Translation2d;
import io.github.captainsoccer.basicmotor.ctre.talonfx.BasicTalonFXConfig;

public enum SwerveModuleConstants {
    FRONT_LEFT(0,1), 
    FRONT_RIGHT(2,3), 
    BACK_LEFT(4,5), 
    BACK_RIGHT(6,7);

    public final BasicTalonFXConfig steeringConfig = new BasicTalonFXConfig();
    public final BasicTalonFXConfig drivingConfig = new BasicTalonFXConfig();

    public final Translation2d translation2d = new Translation2d();


    private SwerveModuleConstants(int steerID, int driveID){
        steeringConfig.motorConfig.id = steerID;   
        drivingConfig.motorConfig.id = driveID;
    }


    public static Translation2d[] getModuleTranslations(){
        return new Translation2d[]{
            FRONT_LEFT.translation2d,
            FRONT_RIGHT.translation2d,
            BACK_LEFT.translation2d,
            BACK_RIGHT.translation2d
        };
    }
}
