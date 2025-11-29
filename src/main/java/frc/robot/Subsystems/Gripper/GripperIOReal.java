package frc.robot.Subsystems.Gripper;

import io.github.captainsoccer.basicmotor.rev.BasicSparkMAX;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.controllers.Controller.ControlMode;

public class GripperIOReal implements GripperIO {
    private final BasicMotor motor;
    private final DigitalInput beamBreak;

    public GripperIOReal() {
        motor = new BasicSparkMAX(GripperConstants.motorConfig);
        beamBreak = new DigitalInput(GripperConstants.BEAM_BREAK_CHANNEL);
    }

    @Override
    public void setTorque(double torque) {
        // Use slot 1 of the PIDF config for torque
        motor.setControl(torque, ControlMode.TORQUE, 1);
    }

    @Override
    public void setPosition(double position) {
        // Use slot 0 of the PIDF config for the position mode
        motor.setControl(position, ControlMode.POSITION);
    }

    @Override
    public double getPosition() {
        return motor.getPosition();
    }

    @Override
    public boolean getBeamBreak() {
        return beamBreak.get();
    }

    @Override
    public void setPercentOutput(double percent) {
        motor.setPercentOutput(percent);
    }

    @Override
    public void stopMotor() {
        motor.stop();
    }

    @Override
    public void updateInputs(GripperInputs inputs) {
        inputs.temperature = motor.getSensorData().temperature(); // In celsius
        inputs.seeCoral = !beamBreak.get(); // Beam break returns false when the beam is broken
        Logger.recordOutput("Gripper/BeamBreak", inputs.seeCoral);
        SmartDashboard.putBoolean("BeamBreak", getBeamBreak());
    }
}
