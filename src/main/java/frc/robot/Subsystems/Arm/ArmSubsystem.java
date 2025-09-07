package frc.robot.Subsystems.Arm;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Subsystems.Arm.ArmInputsAutoLogged;

public class ArmSubsystem extends SubsystemBase {
    private final ArmIO io;
    private final ArmInputsAutoLogged inputs  = new ArmInputsAutoLogged();

    
    public ArmSubsystem() {
        io = RobotBase.isReal() ? new ArmRealMotorIO() : new ArmSimIO();
        io.resetEncoder();
    }


    @Override
    public void periodic() {
        io.update(inputs);
        Logger.processInputs(getName(), inputs);

        String currentCommandName = (getCurrentCommand() ==null) ? "Null" : getCurrentCommand().getName();
        Logger.recordOutput("Arm/CurrentCommand", currentCommandName);;
    }

    public void setPos(double pos) {
        io.setMotorPos(pos);
    }

    public void setPosByLevel(ArmConstants.desiredPositions level){
        io.setMotorPos(level.pos);
    }

    public double currentPos() {
        return inputs.currentPos;
    }

    public boolean atSetPoint(){
        return io.atSetPoint();
    }


}

