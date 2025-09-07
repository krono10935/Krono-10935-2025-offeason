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
  private Gripper gripper;
  private GamePiece gamePiece;
  public HoldCommand(Gripper gripper) {
    this.gripper = gripper;
    gamePiece = gripper.getGamePiece();
    addRequirements(gripper);
  }

  public void initialize(){
    gamePiece = gripper.getGamePiece();
  }

  @Override
  public boolean isFinished(){
    if (gamePiece == GamePiece.Algae || gamePiece == GamePiece.Unknown){
      return gripper.isMotorOverheating();
    }
    return false;
  }
}
