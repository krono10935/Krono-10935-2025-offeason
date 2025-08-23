package frc.robot.subsystems.Gripper;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;


public class Gripper extends SubsystemBase{
    private final GripperIO io;
    private final GripperInputsAutoLogged inputs = new GripperInputsAutoLogged();
    private boolean hasCoral;
    private boolean hasAlgae;

    public Gripper(){
        if (Robot.isReal()){
            io = new GripperIOReal();
        } else {
            io = new GripperIOSim();
        }

        hasCoral = GripperConstants.START_WITH_CORAL; 
        hasAlgae = GripperConstants.START_WITH_ALGAE;
    }

    public void grabCoral(){
        io.setPercentOutput(GripperConstants.CORAL_INTAKE_POWER);
    }

    public void holdCoral(){
        io.keepPosition();
    }

    public void releaseCoral(){
        io.setPercentOutput(GripperConstants.CORAL_EJECT_POWER);
    }

    public void holdAlgae(){
        io.setTorque(GripperConstants.ALGAE_TORQUE);
    }

    public void releaseAlgae(){
        io.stopMotor();
    }

    public boolean getHasCoral(){
        return hasCoral;
    }

    public void setHasCoral(boolean hasCoral){
        this.hasCoral = hasCoral;
    }

    public boolean getHasAlgae(){
        return hasAlgae;
    }

    public void setHasAlgae(boolean hasAlgae){
        this.hasAlgae = hasAlgae;
    }

    @Override
    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs(getName(), inputs);

        String currentCommandName = getCurrentCommand() == null ? "Null" : getCurrentCommand().getName();
        Logger.recordOutput("Gripper/Current Command ", currentCommandName);
    }
}
