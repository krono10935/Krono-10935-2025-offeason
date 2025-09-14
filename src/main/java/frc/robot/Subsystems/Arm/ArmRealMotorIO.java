package frc.robot.Subsystems.Arm;

import edu.wpi.first.math.geometry.Rotation2d;
import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.controllers.Controller;
import io.github.captainsoccer.basicmotor.controllers.Controller.ControlMode;
import io.github.captainsoccer.basicmotor.rev.BasicSparkMAX;


public class ArmRealMotorIO implements ArmIO{

    private final BasicMotor motor;

    public ArmRealMotorIO() {
        motor = new BasicSparkMAX(ArmConstants.config);

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
