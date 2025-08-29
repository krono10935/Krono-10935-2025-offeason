package frc.robot.subsystems.drivetrain.swerve.module;

import io.github.captainsoccer.basicmotor.ctre.talonfx.BasicTalonFX;
import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.signals.SensorDirectionValue;

import edu.wpi.first.math.kinematics.SwerveModuleState;
import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.BasicMotor.IdleMode;
import io.github.captainsoccer.basicmotor.controllers.Controller.ControlMode;

public class SwerveModuleBasic extends SwerveModuleIO {

    private final BasicMotor drivingMotor;
    private final BasicMotor steeringMotor;

    private final CANcoder canCoder;

    public SwerveModuleBasic(SwerveModuleConstants constants){
        super(constants);
        drivingMotor = new BasicTalonFX(constants.DRIVING_CONFIG);
        steeringMotor = new BasicTalonFX(constants.STEERING_CONFIG);

        canCoder = createCANcoder(constants);

        ((BasicTalonFX) steeringMotor).useRemoteCanCoder(canCoder, constants.STEERING_CONFIG.motorConfig.gearRatio);

        canCoder.getMagnetHealth().setUpdateFrequency(4);
        canCoder.optimizeBusUtilization();
    }

    @Override
    public void setTargetState(SwerveModuleState targetState) {
        drivingMotor.setControl(targetState.speedMetersPerSecond,ControlMode.VELOCITY);
        steeringMotor.setControl(targetState.angle.getRotations(),ControlMode.POSITION);
    }

    @Override
    protected double getDriveVelocity() {
        return drivingMotor.getVelocity();
    }

    @Override
    protected double getDriveDistance() {
        return drivingMotor.getPosition();
    }

    @Override
    protected double getSteerAngle() {
        return steeringMotor.getPosition();
    }

    @Override
    public void setBrakeMode(boolean isBrake) {
        IdleMode idleMode = isBrake ? IdleMode.BRAKE : IdleMode.COAST;

        drivingMotor.setIdleMode(idleMode);
        steeringMotor.setIdleMode(idleMode);
    }

    private static CANcoder createCANcoder(SwerveModuleConstants constants){
        CANcoder encoder = new CANcoder(constants.CANCODER_ID);
        var config = new CANcoderConfiguration();
        config.MagnetSensor.AbsoluteSensorDiscontinuityPoint = 0.5;
        config.MagnetSensor.SensorDirection = SensorDirectionValue.CounterClockwise_Positive;
        config.MagnetSensor.MagnetOffset = constants.ZERO_OFFSET;

        encoder.getConfigurator().apply(config);
        return encoder;
    }

    @Override
    public void update(){
        super.update();
        Logger.recordOutput("basic module/" + constants.name() + "/magenet health", canCoder.getMagnetHealth().getName());
    }
}
