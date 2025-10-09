// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Gripper;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Gripper.Gripper;
import frc.robot.Subsystems.Gripper.GripperConstants;
import frc.robot.Subsystems.Gripper.GripperConstants.GamePiece;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
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
    // System.out.println(gripper.seeCoral());
    return gripper.seeCoral();
  }

  @Override
  public void end(boolean interuptted){
    if (interuptted){
      gripper.setGamePiece(GamePiece.Unknown);
      gripper.keepPosition();
    } else {
      gripper.setGamePiece(gamePiece);

      gripper.keepPosition();


      // switch (gamePiece){
      //   case None, Unknown:

      //     gripper.stopMotor();

      //   case Coral:

      //       System.out.println("KEEPPP");

      //   case Algae:

      //     gripper.setTorque(GripperConstants.ALGAE_TORQUE);

      //   default:

      //     gripper.stopMotor();

      // }
    }
  }
}