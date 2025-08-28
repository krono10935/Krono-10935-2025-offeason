package frc.robot.Subsystems.drivetrain.swerve.module;

import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public abstract class SwerveModuleIO {

    private SwerveModulePosition position;
    protected SwerveModuleState targetState = new SwerveModuleState();
    public SwerveModuleState getTargetState(){
        return targetState;
    }

    public void setTargetState(SwerveModuleState targetState){
        this.targetState = targetState;
    }

    public SwerveModulePosition getPosition(){
        return position;
    }
}
