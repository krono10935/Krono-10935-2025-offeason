package frc.robot.Subsystems.Arm;

import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.controllers.Controller;
import io.github.captainsoccer.basicmotor.controllers.Controller.ControlMode;
import io.github.captainsoccer.basicmotor.measurements.Measurements;
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
    public void setMotorPos(double pos){
        motor.setControl(pos, ControlMode.PROFILED_POSITION);
    }

    @Override
    public void update(ArmInputs inputs) {
        inputs.atSetPoint = motor.atSetpoint();
        inputs.currentPos = motor.getPosition();
    }

    @Override
    public boolean atSetPoint(){
        return motor.atSetpoint();
    }

   
}
