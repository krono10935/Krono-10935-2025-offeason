package frc.robot.subsystems.drivetrain.swerve.module;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public abstract class SwerveModuleIO {

    public final SwerveModuleConstants constants;

    private final SwerveModuleState currentState = new SwerveModuleState();

    private final SwerveModulePosition position = new SwerveModulePosition();

    protected SwerveModuleIO(SwerveModuleConstants constants){
        this.constants = constants;
    }

    public abstract void setTargetState(SwerveModuleState targetState);

    public SwerveModuleState getState(){
        return currentState;
    }

    public SwerveModulePosition getPosition(){
        return position;
    }

    public void update(){
        currentState.angle = Rotation2d.fromRotations(getSteerAngle());
        position.angle = currentState.angle;
        currentState.speedMetersPerSecond = getDriveVelocity();
        position.distanceMeters = getDriveDistance();

    }

    protected abstract double getDriveVelocity();
    protected abstract double getDriveDistance();
    protected abstract double getSteerAngle();

    public abstract void setBrakeMode(boolean isBrake);

}
