// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.HoldCommand;
import frc.robot.subsystems.Gripper.Gripper;
import frc.robot.subsystems.Gripper.GripperConstants.GamePiece;

public class RobotContainer {
  Gripper gripper;

  public RobotContainer() {
    configureBindings();
    gripper = new Gripper();

    new Trigger(()->true).onTrue(new HoldCommand(gripper, GamePiece.None));
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
