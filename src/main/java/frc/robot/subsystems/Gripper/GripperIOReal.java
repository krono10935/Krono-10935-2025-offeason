package frc.robot.subsystems.Gripper;

import io.github.captainsoccer.basicmotor.rev.BasicSparkMAX;

import io.github.captainsoccer.basicmotor.BasicMotor;

public class GripperIOReal extends GripperIO {
    private final BasicMotor motor;

    public GripperIOReal() {
        motor = new BasicSparkMAX(GripperConstants.motorConfig);
    }

    @Override
    public void updateInputs(GripperInputs inputs) {
        inputs.temperature = motor.getSensorData().temperature(); // In celsius
    }
}
