// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Arm;

import static edu.wpi.first.units.Units.Rotation;

import java.util.logging.Level;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.Arm.ArmConstants;
import frc.robot.Subsystems.Arm.ArmSubsystem;
import frc.robot.Subsystems.Arm.ArmConstants.ArmLevel;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class setArmLevelCommand extends Command {
  /** Creates a new setArmLevelCommand. */
  ArmSubsystem arm;
  ArmLevel desiredLevel;
  boolean falling;
  boolean isCoast;
  double fallingError = 0.15;
  
  public setArmLevelCommand(ArmSubsystem arm, ArmLevel desiredLevel) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(arm);
    System.out.println(desiredLevel == null);
    this.arm = arm;
    this.desiredLevel = desiredLevel;
    falling = false;
    isCoast=false;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(desiredLevel.angle.getRotations()> arm.getCurrentAngle().getRotations()){
      arm.setAngleByLevel(desiredLevel);
      falling = false;
    }
    else {
      arm.stop();
      falling= true;
      if(desiredLevel == ArmLevel.HOME){
        arm.setCoast();
        isCoast=true;
      }
    }
  
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //arm.setArmVelocity(-0.001);

    if(desiredLevel == ArmLevel.HOME 
    && arm.getCurrentAngle().getRotations() - arm.getTargetLevel().angle.getRotations()<=fallingError && 
    isCoast){
      arm.setBrake();
      isCoast=false;
    }
    Logger.recordOutput("setArmLevelCommand/target angle", desiredLevel.angle.getDegrees());
    Logger.recordOutput("setArmLevelCommand/error", arm.getTargetLevel().angle.getDegrees()-  arm.getCurrentAngle().getDegrees());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if (falling & Math.abs(
      arm.getCurrentAngle().getRotations() - desiredLevel.angle.getRotations())
       <= ArmConstants.config.slot0Config.pidConfig.tolerance)
       arm.setAngleByLevel(desiredLevel);    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //System.out.println(arm.getCurrentAngle().getDegrees());
    if (falling) return arm.getCurrentAngle().getRotations() <= desiredLevel.angle.getRotations();
    return arm.isAtSetPoint();
  }
  
  // set arm level
  public void setLevel(ArmLevel desiredLevel) {
    arm.setAngleByLevel(desiredLevel);
  } 
}
