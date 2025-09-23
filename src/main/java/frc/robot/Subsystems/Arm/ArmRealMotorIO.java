package frc.robot.subsystems.Arm;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.controllers.Controller.ControlMode;
import io.github.captainsoccer.basicmotor.rev.BasicSparkMAX;

public class ArmRealMotorIO implements ArmIO{

    private final BasicMotor motor;

    public ArmRealMotorIO() {
        motor = new BasicSparkMAX(ArmConstants.config);
        DutyCycleEncoder armDutyCycleEncoder = new DutyCycleEncoder(ArmConstants.DUTY_CYCLE_ENCODER_PORT);
        motor.resetEncoder(armDutyCycleEncoder.get()+ArmConstants.DUTY_CYCLE_ENCODER_ZERO_OFFSET);
        

    }

    @Override
    public void resetEncoder() {
        motor.resetEncoder(0);
    }
    @Override
    public void update(ArmInputs inputs) {
        inputs.atSetPoint = motor.atSetpoint();
        inputs.currentAngle = Rotation2d.fromRotations(motor.getPosition());
        
    }

    @Override
    public void setMotorAngle(Rotation2d Angle) {
        motor.setControl(Angle.getRotations(), ControlMode.POSITION);
    }
}
