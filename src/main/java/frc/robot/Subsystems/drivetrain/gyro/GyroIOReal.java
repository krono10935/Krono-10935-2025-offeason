package frc.robot.Subsystems.drivetrain.gyro;

import edu.wpi.first.math.geometry.Rotation2d;

public class GyroIOReal implements GyroIO {



    public GyroIOReal(){

    }

    @Override
    public Rotation2d getAngle(){
        return new Rotation2d(0,0);
    }

    @Override
    public void updateInputs(GyroIOInputs inputs){
        inputs.angle = new Rotation2d(0,0);
    }
    
}
