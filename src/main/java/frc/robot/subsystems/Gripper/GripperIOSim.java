package frc.robot.subsystems.Gripper;

import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.controllers.Controller.ControlMode;
import io.github.captainsoccer.basicmotor.sim.motor.BasicMotorSim;

public class GripperIOSim implements GripperIO {
private final BasicMotor motor;

    public GripperIOSim() {
        motor = new BasicMotorSim(GripperConstants.motorConfig);
    }

    @Override
    public void setTorque(double torque) {
        // Use slot 1 of the PIDF config for torque
        motor.setControl(torque, ControlMode.TORQUE, 1);
    }

    @Override
    public void keepPosition() {
        // Use slot 0 of the PIDF config for the position mode
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
