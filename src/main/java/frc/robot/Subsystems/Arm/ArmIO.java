package frc.robot.Subsystems.Arm;

import org.littletonrobotics.junction.AutoLog;

public interface ArmIO {
    @AutoLog
    class ArmInputs {
        public boolean atSetPoint;
        public double currentAngle;
    }

    void setAngle(double angle);

    void resetEncoder();

    void update(ArmInputs inputs);

}
