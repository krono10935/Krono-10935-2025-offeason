package frc.robot.subsystems.Gripper;

import org.littletonrobotics.junction.AutoLog;

public interface GripperIO {
    @AutoLog
    class GripperInputs {
        double temperature;
    }

    /**
     * Set torque for the grip on algae
     * @param torque
     */
    public void setTorque(double torque); 

    /**
     * Keep your position to hold the coral
     */
    public void keepPosition();

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
     * Update the GripperInputsAutologged
     * @param inputs
     */
    public void updateInputs(GripperInputs inputs);
}
