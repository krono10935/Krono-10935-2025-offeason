package frc.robot.Subsystems.Arm;

import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.controllers.Controller;
import io.github.captainsoccer.basicmotor.sim.arm.BasicArmSim;

public class ArmSimIO implements ArmIO{


    BasicMotor motor;


    public ArmSimIO() {
        motor = new BasicArmSim(ArmConstants.config);
    }


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

    public double convertFromRadiansToRotations(double radians){
        return 2 * Math.PI * radians;
    }

    public double convertFromRotationsToRadians(double rotations){
        return rotations / 2 * Math.PI;
    }
}
