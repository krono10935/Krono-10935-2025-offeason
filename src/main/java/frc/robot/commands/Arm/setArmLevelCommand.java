// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Arm;

import java.util.logging.Level;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm.ArmConstants;
import frc.robot.subsystems.Arm.ArmSubsystem;
import frc.robot.subsystems.Arm.ArmConstants.ArmLevel;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class setArmLevelCommand extends Command {
  /** Creates a new setArmLevelCommand. */
  ArmSubsystem arm;
  ArmLevel desiredLevel;
  public setArmLevelCommand(ArmSubsystem arm, ArmLevel desiredLevel) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(arm);
    this.arm = arm;
    this.desiredLevel = desiredLevel;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    arm.setAngleByLevel(desiredLevel);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
  
  // set arm level
  public void setLevel(ArmLevel desiredLevel) {
    arm.setAngleByLevel(desiredLevel);
  } 
}
