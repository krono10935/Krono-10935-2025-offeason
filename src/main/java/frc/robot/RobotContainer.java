// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Subsystems.Arm.ArmSubsystem;
import frc.robot.Subsystems.Arm.ArmConstants.ArmLevel;
import frc.robot.commands.setArmLevelCommand;


public class RobotContainer {
  public RobotContainer() {
    ArmSubsystem armSubsystem = new ArmSubsystem();
    armSubsystem.setDefaultCommand(new setArmLevelCommand(armSubsystem, ArmLevel.L1));
    configureBindings();
    
    
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
