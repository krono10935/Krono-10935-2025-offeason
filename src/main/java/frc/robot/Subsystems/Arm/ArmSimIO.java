package frc.robot.subsystems.Arm;

import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.sim.arm.BasicArmSim;

public class ArmSimIO extends ArmIO{


    BasicMotor armMotorSim;

    public ArmSimIO() {
        motor = new BasicArmSim(ArmConstants.config);
    }
}
