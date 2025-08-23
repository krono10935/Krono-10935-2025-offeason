package frc.robot.subsystems.Gripper;

import io.github.captainsoccer.basicmotor.rev.BasicSparkMAX;

import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.controllers.Controller.ControlMode;

public class GripperIOReal implements GripperIO {
    private final BasicMotor motor;

    public GripperIOReal() {
        motor = new BasicSparkMAX(GripperConstants.motorConfig);
    }

    @Override
    public void setTorque(double torque) {
        motor.setControl(torque, ControlMode.TORQUE);
    }

    @Override
    public void keepPosition() {
        motor.setControl(motor.getPosition(), ControlMode.POSITION);
    }

    @Override
    public void setPercentOutput(double percent) {
        motor.setPrecentOutput(percent);
    }

    @Override
    public void stopMotor() {
        motor.stop();
    }

    @Override
    public void updateInputs(GripperInputs inputs) {

    }
}
