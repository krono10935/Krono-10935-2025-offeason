package frc.robot.Subsystems.Arm;

import edu.wpi.first.math.geometry.Rotation2d;
import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Subsystems.Arm.ArmConstants.ArmLevel;
import frc.robot.Subsystems.Arm.ArmInputsAutoLogged;

public class ArmSubsystem extends SubsystemBase {
    private final ArmIO io;
    private final ArmInputsAutoLogged inputs = new ArmInputsAutoLogged();
    private ArmConstants.ArmLevel targetLevel;
    private static final Alert unknownArmLevelAlert = new Alert("Attempted to set arm level to unknown angle",AlertType.kWarning);


    public ArmSubsystem() {
        io = RobotBase.isReal() ? new ArmRealMotorIO() : new ArmSimIO();
    }

    public ArmLevel getCurrentLevel(){
        Rotation2d angle = inputs.currentAngle;
        if (angle.getDegrees() == ArmLevel.HOME.angle.getDegrees()){
            return ArmLevel.HOME;
        }

        if (angle.getDegrees() == ArmLevel.L1.angle.getDegrees()){
            return ArmLevel.L1;
        }
        
        if(angle.getDegrees() == ArmLevel.L2.angle.getDegrees()){
            return ArmLevel.L2;
        }

        if (angle.getDegrees() == ArmLevel.L3.angle.getDegrees()){
            return ArmLevel.L3;
        }
        
        return ArmLevel.UNKNOWN;   
    }


    @Override
    public void periodic() {
        io.update(inputs);
        Logger.processInputs(getName(), inputs);

        String currentCommandName = (getCurrentCommand() == null) ? "Null" : getCurrentCommand().getName();
        Logger.recordOutput("Arm/CurrentCommand", currentCommandName);
        
        Logger.recordOutput("Arm/currentLevel", getCurrentLevel().name());
        Logger.recordOutput("Arm/current angle", inputs.currentAngle.getDegrees());
        
    }


    public void setAngleByLevel(ArmConstants.ArmLevel level) {
        if(level == ArmConstants.ArmLevel.UNKNOWN){
            unknownArmLevelAlert.set(true);
            return;
        }
        unknownArmLevelAlert.set(false);
        io.setMotorAngle(level.angle);
        targetLevel = level;
        Logger.recordOutput("Arm/Target Level", level.name());
    }

    public Rotation2d getCurrentAngle() {
        return inputs.currentAngle;
    }

    public boolean isAtSetPoint() {
        return inputs.atSetPoint;
    }

    public ArmConstants.ArmLevel getTargetLevel(){
        return targetLevel;
    }


}

