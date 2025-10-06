package frc.robot.Subsystems.Gripper;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.Subsystems.Gripper.GripperConstants.GamePiece;
import frc.robot.Subsystems.Gripper.GripperInputsAutoLogged;


public class Gripper extends SubsystemBase{
    private final GripperIO io;
    private final GripperInputsAutoLogged inputs = new GripperInputsAutoLogged();
    private GamePiece currGamePiece;

    public Gripper(){
        if (Robot.isReal()){
            io = new GripperIOReal();
        } else {
            io = new GripperIOSim();
        }

        currGamePiece = GripperConstants.START_WITH_CORAL ? GamePiece.Coral : GamePiece.None;
    }

    /**
     * Make the gripper hold in the current position
     */
    public void keepPosition(){
        io.setPosition(io.getPosition());
    }

    /**
     * Set percent output
     */
    public void setPercentOutput(double ejectPower){
        io.setPercentOutput(ejectPower);
    }

    /**
     * Sets torque
     */
    public void setTorque(double torque){
        io.setTorque(torque); // Use torque to grip better the algae
    }

    /**
     * Stop motor
     */
    public void stopMotor(){
        io.stopMotor();
    }

    /**
     * Which gamepiece is held
     * @return GamePiece for the piece held
     */
    public GamePiece getGamePiece(){
        // if (seeCoral()){
            // currGamePiece = GamePiece.Coral;
        // }

        return currGamePiece;
    }

    /**
     * Used to make the Gripper know what is being held
     * @param hasCoral
     */
    public void setGamePiece(GamePiece gamePiece){
        this.currGamePiece = gamePiece;
        Logger.recordOutput("Gripper/Gamepiece", gamePiece.name());
    }

    public boolean isMotorOverheating(){
        return inputs.temperature >= GripperConstants.MAX_MOTOR_TEMPERATURE;
    }

    // public boolean seeCoral(){
        // return !io.getBeamBreak();
    // }

    @Override
    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs(getName(), inputs);

        String currentCommandName = getCurrentCommand() == null ? "Null" : getCurrentCommand().getName();
        Logger.recordOutput("Gripper/Current Command ", currentCommandName);
    }
}
