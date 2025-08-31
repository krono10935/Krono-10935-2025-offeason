package frc.robot.subsystems.Arm;

import org.littletonrobotics.junction.AutoLog;

import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.controllers.Controller;

public abstract class ArmIO {
    @AutoLog
    public static class ArmInputs {
        public boolean atSetPoint;
        public double currentAngle;
    }

    protected BasicMotor motor;

    public void setAngle(double radians) {
        motor.setControl(convertFromRadiansToRotations(radians), Controller.ControlMode.POSITION);

    }

    public void resetEncoder() {
        motor.resetEncoder(0);
    }

    public void update(ArmInputs inputs) {
        inputs.atSetPoint = motor.atSetpoint();
        inputs.currentAngle = convertFromRotationsToRadians(motor.getPosition());
    }

    private double convertFromRadiansToRotations(double radians){
        return 2 * Math.PI * radians;
    }

    private double convertFromRotationsToRadians(double rotations){
        return rotations / 2 * Math.PI;
    }

}
