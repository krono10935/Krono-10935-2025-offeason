package frc.robot.Subsystems.drivetrain.swerve.module;

import io.github.captainsoccer.basicmotor.ctre.talonfx.BasicTalonFX;

import io.github.captainsoccer.basicmotor.BasicMotor;

public class SwerveModuleBasic extends SwerveModuleIO {
    private final BasicMotor drivingMotor;
    private final BasicMotor steeringMotor;

    public SwerveModuleBasic(SwerveModuleConstants constants){
        drivingMotor = new BasicTalonFX(constants.drivingConfig);
        steeringMotor = new BasicTalonFX(constants.steeringConfig);
    }
}
