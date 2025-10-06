package frc.robot.Subsystems.Gripper;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.wpilibj.DigitalInput;

public interface GripperIO {
    @AutoLog
    class GripperInputs {
        public double temperature;
        public boolean seeCoral;
    }

    /**
     * Set torque for the grip on algae
     * @param torque
     */
    public void setTorque(double torque); 

    /**
     * Set your position
     */
    public void setPosition(double position);

    /**
     * Get your position to hold the coral
     */
    public double getPosition();

    /**
     * Set percent output for intake or ejection of coral
     * @param percent
     */
    public void setPercentOutput(double percent);

    /**
     * Used to let go of the algae
     */
    public void stopMotor();

    /**
     * Get the beam break sensor
     * @return
     */
    // public boolean getBeamBreak();

    /**
     * Update the GripperInputsAutologged
     * @param inputs
     */
    public void updateInputs(GripperInputs inputs);
}
