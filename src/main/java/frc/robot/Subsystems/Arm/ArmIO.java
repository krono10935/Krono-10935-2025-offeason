package frc.robot.Subsystems.Arm;

import org.littletonrobotics.junction.AutoLog;

import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.controllers.Controller;

public interface ArmIO {
    @AutoLog
    public static class ArmInputs {
        public boolean atSetPoint;
        public double currentAngle;
    }


    public void setAngle(double radians);

    public void resetEncoder();

    public void update(ArmInputs inputs);

    public double convertFromRadiansToRotations(double radians);

    public double convertFromRotationsToRadians(double rotations);
}
