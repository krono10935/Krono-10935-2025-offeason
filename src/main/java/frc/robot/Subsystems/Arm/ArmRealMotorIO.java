package frc.robot.Subsystems.Arm;

import edu.wpi.first.math.geometry.Pose3d;
import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.controllers.Controller;
import io.github.captainsoccer.basicmotor.rev.BasicSparkMAX;

public class ArmRealMotorIO implements ArmIO{

    BasicMotor armMotor;

    public ArmRealMotorIO() {
        armMotor = new BasicSparkMAX(ArmConstants.config);

    }

    @Override
    public void setAngle(double angle) {
        armMotor.setControl(angle, Controller.ControlMode.POSITION);

    }

    @Override
    public void resetEncoder() {

        armMotor.resetEncoder(0);
    }

    @Override
    public void update(ArmInputs inputs) {
        inputs.atSetPoint = armMotor.atSetpoint();
        inputs.currentAngle = armMotor.getPosition();
    }
}
