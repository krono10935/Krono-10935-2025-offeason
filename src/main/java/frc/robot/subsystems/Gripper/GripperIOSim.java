package frc.robot.subsystems.Gripper;

import io.github.captainsoccer.basicmotor.sim.motor.BasicMotorSim;

public class GripperIOSim extends GripperIO {
    public GripperIOSim(){
        motor = new BasicMotorSim(GripperConstants.motorConfig);
    }

    @Override
    public void updateInputs(GripperInputs inputs) {
        // TODO Auto-generated method stub
        
    }
}
