// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Subsystems.Arm.ArmSubsystem;
import frc.robot.commands.setArmPositionCommand;
import frc.robot.commands.Gripper.HoldCommand;
import frc.robot.subsystems.Gripper.Gripper;

public class RobotContainer {
  Gripper gripper;

  public RobotContainer() {
    configureBindings();
    gripper = new Gripper();

    gripper.setDefaultCommand(new HoldCommand(gripper));
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
