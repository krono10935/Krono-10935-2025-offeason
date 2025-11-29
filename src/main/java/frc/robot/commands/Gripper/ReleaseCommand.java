// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Gripper;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Gripper.Gripper;
import frc.robot.Subsystems.Gripper.GripperConstants;
import frc.robot.Subsystems.Gripper.GripperConstants.GamePiece;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ReleaseCommand extends Command {
  private Gripper gripper;
  private GamePiece gamePiece;
  public ReleaseCommand(Gripper gripper) {
    this.gripper = gripper;
    gamePiece = gripper.getGamePiece();
    addRequirements(gripper);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    gamePiece = gripper.getGamePiece();

    switch (gamePiece){
      case None, Unknown, Algae:
        
        gripper.stopMotor();
        gripper.setGamePiece(GamePiece.None);

      case Coral:

        gripper.setPercentOutput(GripperConstants.CORAL_EJECT_POWER);
        gripper.setGamePiece(GamePiece.None);

    }
  }

  @Override
  public boolean isFinished(){
    return !gripper.seeCoral();
  }

  @Override
  public void end(boolean interrupted){
    gripper.stopMotor();
  }
}
