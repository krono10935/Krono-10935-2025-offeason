package frc.robot.Subsystems.Gripper;

import edu.wpi.first.wpilibj.DigitalInput;
import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.controllers.Controller.ControlMode;
import io.github.captainsoccer.basicmotor.sim.motor.BasicMotorSim;

public class GripperIOSim implements GripperIO {
    private final BasicMotor motor;
    //private final DigitalInput beamBreak;

    public GripperIOSim() {
        motor = new BasicMotorSim(GripperConstants.motorConfig);
        //beamBreak = new DigitalInput(GripperConstants.BEAM_BREAK_CHANNEL);
    }

    @Override
    public void setTorque(double torque) {
        // Use slot 1 of the PIDF config for torque
        motor.setControl(torque, ControlMode.TORQUE, 1);
    }

    @Override
    public boolean getBeamBreak() {
        // In our current sim implementation we do not have the ablity to get coral.
        return false;//beamBreak.get();
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
