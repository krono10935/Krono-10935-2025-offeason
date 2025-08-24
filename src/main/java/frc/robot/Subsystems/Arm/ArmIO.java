package frc.robot.Subsystems.Arm;

import edu.wpi.first.math.geometry.Pose3d;
import org.littletonrobotics.junction.AutoLog;

public interface ArmIO {


    @AutoLog
    class ArmInputs {
        boolean atSetPoint;
    }


    double currentAngle();

    Pose3d getGripperPose();

    void setAngle(double angle);

    void resetEncoder();

    void update(ArmInputs inputs);

}
