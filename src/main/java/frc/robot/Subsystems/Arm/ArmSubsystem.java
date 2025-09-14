package frc.robot.Subsystems.Arm;

import edu.wpi.first.math.geometry.Rotation2d;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
    private final ArmIO io;
    private final ArmInputsAutoLogged inputs = new ArmInputsAutoLogged();
    private ArmConstants.ArmLevel targetLevel;


    public ArmSubsystem() {
        io = RobotBase.isReal() ? new ArmRealMotorIO() : new ArmSimIO();

    }


    @Override
    public void periodic() {
        io.update(inputs);
        Logger.processInputs(getName(), inputs);

        String currentCommandName = (getCurrentCommand() == null) ? "Null" : getCurrentCommand().getName();
        Logger.recordOutput("Arm/CurrentCommand", currentCommandName);
    }


    public void setAngleByLevel(ArmConstants.ArmLevel level) {
        if(level == ArmConstants.ArmLevel.UNKNOWN)
            return;
        io.setMotorAngle(level.angle);
        targetLevel = level;
        Logger.recordOutput("Arm/Target Level", level.name());
    }

    public Rotation2d currentAngle() {
        return inputs.currentAngle;
    }

    public boolean atSetPoint() {
        return inputs.atSetPoint;
    }

    public ArmConstants.ArmLevel getTargetLevel(){
        return targetLevel;
    }


}

