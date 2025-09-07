// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Gripper.Gripper;
import frc.robot.subsystems.Gripper.GripperConstants.GamePiece;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class HoldCommand extends Command {
  /** Creates a new IntakeCommand. */
  Gripper gripper;
  GamePiece gamePiece;
  public HoldCommand(Gripper gripper, GamePiece gamePiece) {
    this.gripper = gripper;
    this.gamePiece = gamePiece;
    addRequirements(gripper);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    switch (gamePiece) {
      case None:
        
        break;
    
      case Coral:

        gripper.holdCoral();

      case Algae:

        gripper.holdAlgae();

      default:
      
        break;
    }
  }

  @Override
  public boolean isFinished(){
    switch (gamePiece){
      case None:

        return true;

      case Coral:

        return false;
      
      case Algae:

        return gripper.isMotorOverheating();

      default:
        return true;
    }
  }
}
