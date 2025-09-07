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

        hasCoral = GripperConstants.START_WITH_CORAL; // Change to be based on whether or not the robot is on field
        hasAlgae = GripperConstants.START_WITH_ALGAE;
    }

    /**
     * Make the Gripper grab a coral
     */
    public void grabCoral(){
        io.setPercentOutput(GripperConstants.CORAL_INTAKE_POWER);
    }

    /**
     * Make the gripper hold the coral in the current position
     */
    public void holdCoral(){
        io.keepPosition();
    }

    /**
     * Releases a held coral
     */
    public void releaseCoral(){
        io.setPercentOutput(GripperConstants.CORAL_EJECT_POWER);
    }

    /**
     * Holds an algae
     */
    public void holdAlgae(){
        io.setTorque(GripperConstants.ALGAE_TORQUE); // Use torque to grip better the algae
    }

    /**
     * Release a held algae
     */
    public void releaseAlgae(){
        stopMotor();
    }

    /**
     * Stop motor
     */
    public void stopMotor(){
        io.stopMotor();
    }

    /**
     * Does the gripper have a coral held
     * @return boolean for if the Gripper is holding a coral
     */
    public boolean getHasCoral(){
        return hasCoral;
    }

    /**
     * Used to make the Gripper know if it is holding a coral.
     * @param hasCoral
     */
    public void setHasCoral(boolean hasCoral){
        this.hasCoral = hasCoral;
    }

    /**
     * Does the gripper have an algae held
     * @return boolean for if the Gripper is holding a coral
     */
    public boolean getHasAlgae(){
        return hasAlgae;
    }

    /**
     * Used to tell the Gripper if it is holding an algae
     * @param hasAlgae
     */
    public void setHasAlgae(boolean hasAlgae){
        this.hasAlgae = hasAlgae;
    }

    public boolean isMotorOverheating(){
        return inputs.temperature >= GripperConstants.MAX_MOTOR_TEMPERATURE;
    }

    @Override
    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs(getName(), inputs);

        String currentCommandName = getCurrentCommand() == null ? "Null" : getCurrentCommand().getName();
        Logger.recordOutput("Gripper/Current Command ", currentCommandName);
    }
}
