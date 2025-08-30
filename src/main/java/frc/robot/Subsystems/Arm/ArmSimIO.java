package frc.robot.Subsystems.Arm;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.controllers.Controller;
import io.github.captainsoccer.basicmotor.gains.ControllerGains;
import io.github.captainsoccer.basicmotor.sim.BasicSimSystem;
import io.github.captainsoccer.basicmotor.sim.arm.BasicArmSim;
import io.github.captainsoccer.basicmotor.sim.motor.BasicMotorSim;

public class ArmSimIO implements ArmIO{


    BasicMotor armMotorSim;

    public ArmSimIO() {
        armMotorSim = new BasicArmSim(ArmConstants.config
        );
    }

    @Override
    public void setAngle(double angle) {

        armMotorSim.setControl(angle, Controller.ControlMode.POSITION);

    }


    @Override
    public void resetEncoder() {

        armMotorSim.resetEncoder(0);
    }


    @Override
    public void update(ArmInputs inputs) {
        inputs.atSetPoint = armMotorSim.atSetpoint();
        inputs.currentAngle = armMotorSim.getPosition();
    }
}
