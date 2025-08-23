package frc.robot.subsystems.Gripper;

import org.littletonrobotics.junction.AutoLog;

public interface GripperIO {
    @AutoLog
    class GripperInputs {
        double temperature;
    }

    public void setTorque(double torque);

    public void keepPosition();

    public void setPercentOutput(double percent);

    public void stopMotor();

    public void updateInputs(GripperInputs inputs);
}
