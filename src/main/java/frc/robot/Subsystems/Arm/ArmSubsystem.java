package frc.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Subsystems.Arm.ArmInputsAutoLogged;

public class ArmSubsystem extends SubsystemBase {
    private final ArmIO io;
    private final ArmInputsAutoLogged inputs  = new ArmInputsAutoLogged();

    
    public ArmSubsystem() {
        io = RobotBase.isReal() ? new ArmRealMotorIO() : new ArmSimIO();

        // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
        //       in the constructor or in the robot coordination class, such as RobotContainer.
        //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
        //       such as SpeedControllers, Encoders, DigitalInputs, etc.
        io.resetEncoder();
    }


    @Override
    public void periodic() {
        io.update(inputs);
    }

    public void setAngle(double angle) {
        io.setAngle(angle);
    }

    public void setAngleByLevel(ArmConstants.desiredAngles level){
        io.setAngle(level.angle);
    }

    public double currentAngle() {
        return inputs.currentAngle;
    }
}

