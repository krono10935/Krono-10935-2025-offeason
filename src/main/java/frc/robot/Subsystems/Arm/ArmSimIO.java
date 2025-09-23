package frc.robot.subsystems.Arm;

import edu.wpi.first.math.geometry.Rotation2d;
import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.controllers.Controller.ControlMode;
import io.github.captainsoccer.basicmotor.sim.arm.BasicArmSim;

public class ArmSimIO implements ArmIO{


    BasicMotor motor;

    
    public ArmSimIO() {
        motor = new BasicArmSim(ArmConstants.config);
        
    }
 @Override
    public void resetEncoder() {
        motor.resetEncoder(0);
    }
    @Override
    public void update(ArmInputs inputs) {
        inputs.atSetPoint = motor.atSetpoint();
        inputs.currentAngle = Rotation2d.fromRotations(motor.getPosition());
        
    }

    @Override
    public void setMotorAngle(Rotation2d Angle) {
        motor.setControl(Angle.getRotations(), ControlMode.POSITION);
    }

   
}
