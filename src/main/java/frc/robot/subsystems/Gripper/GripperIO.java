package frc.robot.subsystems.Gripper;

import org.littletonrobotics.junction.AutoLog;

import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.controllers.Controller.ControlMode;

public abstract class GripperIO {
    @AutoLog
    public static class GripperInputs {
        double temperature;
    }

    public BasicMotor motor;

    /**
     * Set torque for the grip on algae
     * @param torque
     */
    public void setTorque(double torque){
        motor.setControl(torque, ControlMode.TORQUE, 1);
    } 

    /**
     * Keep your position to hold the coral
     */
    public void keepPosition() {
        // Use slot 0 of the PIDF config for the position mode
        motor.setControl(motor.getPosition(), ControlMode.POSITION);
    }

    /**
     * Set percent output for intake or ejection of coral
     * @param percent
     */
    public void setPercentOutput(double percent) {
        motor.setPrecentOutput(percent);
    }

    /**
     * Used to let go of the algae
     */
    public void stopMotor() {
        motor.stop();
    }

    /**
     * Update the GripperInputsAutologged
     * @param inputs
     */
    public abstract void updateInputs(GripperInputs inputs);
}
