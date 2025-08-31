package frc.robot.subsystems.Arm;

import io.github.captainsoccer.basicmotor.rev.BasicSparkMAX;

public class ArmRealMotorIO extends ArmIO{

    public ArmRealMotorIO() {
        motor = new BasicSparkMAX(ArmConstants.config);

    }
}
