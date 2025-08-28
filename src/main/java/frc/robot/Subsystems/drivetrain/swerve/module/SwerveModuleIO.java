package frc.robot.Subsystems.drivetrain.swerve.module;

import edu.wpi.first.math.kinematics.SwerveModuleState;

public abstract class SwerveModuleIO {

    protected SwerveModuleState targetState = new SwerveModuleState();
    public SwerveModuleState getTargetState(){
        return targetState;
    }

    public void run(SwerveModuleState targetState){}


}
