package frc.robot.Subsystems.Arm;

import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.controllers.Controller;
import io.github.captainsoccer.basicmotor.controllers.Controller.ControlMode;
import io.github.captainsoccer.basicmotor.rev.BasicSparkMAX;


public class ArmRealMotorIO implements ArmIO{

    BasicMotor motor;

    public ArmRealMotorIO() {
        motor = new BasicSparkMAX(ArmConstants.config);

    }

    @Override
    public void resetEncoder() {
        motor.resetEncoder(0);
    }
    @Override
    public void update(ArmInputs inputs) {
        inputs.atSetPoint = motor.atSetpoint();
        inputs.currentPos= motor.getPosition();
    }
    @Override
    public void setMotorPos(double pos){
        motor.setControl(pos, ControlMode.PROFILED_POSITION);
    }

    @Override
    public boolean atSetPoint(){
        return motor.atSetpoint();
    }
    

   
}
