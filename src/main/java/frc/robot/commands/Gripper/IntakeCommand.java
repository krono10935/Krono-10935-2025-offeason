// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Gripper;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Gripper.Gripper;
import frc.robot.subsystems.Gripper.GripperConstants;
import frc.robot.subsystems.Gripper.GripperConstants.GamePiece;

public class IntakeCommand extends Command {
  /** Creates a new IntakeCommand. */
  private Gripper gripper;
  private final GamePiece gamePiece;
  public IntakeCommand(Gripper gripper, GamePiece gamePiece) {
    this.gripper = gripper;
    this.gamePiece = gamePiece;
    addRequirements(gripper);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    switch (gamePiece) {
      case None, Algae, Unknown:
        
        break;
    
      case Coral:

        gripper.setPercentOutput(GripperConstants.CORAL_INTAKE_POWER);

      default:

        break;
    }
  }

  @Override
  public boolean isFinished(){
    return true;
  }

  @Override
  public void end(boolean interuptted){
    if (interuptted){
      gripper.setGamePiece(GamePiece.Unknown);
    } else {
      gripper.setGamePiece(gamePiece);

      switch (gamePiece){
        case None, Unknown:

          gripper.stopMotor();

        case Coral:

          gripper.keepPosition();

        case Algae:

          gripper.setTorque(GripperConstants.ALGAE_TORQUE);

        default:

          gripper.stopMotor();

      }
    }
  }
}