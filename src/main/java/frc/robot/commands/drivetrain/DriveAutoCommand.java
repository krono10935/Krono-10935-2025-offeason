// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import static edu.wpi.first.units.Units.Seconds;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.units.measure.Time;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Utils;
import frc.robot.Subsystems.drivetrain.Drivetrain;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class DriveAutoCommand extends WaitCommand {
  /** Creates a new DriveAutoCommand. */
  private Drivetrain dt;
  private double speed;
  private double targetRotation;
  PIDController rotationPID = new PIDController(0.5, 0, 0);

  
  public DriveAutoCommand(Drivetrain dt,  double dis, double time) {
    // Use addRequirements() here to declare subsystem dependencies.
    super(time);
    this.dt=dt;
    addRequirements(dt);
    speed = Utils.calculateXSpeedForAuto(dis, time);
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    targetRotation=dt.getGyroAngle().getRadians();
    rotationPID.setSetpoint(targetRotation);
    rotationPID.enableContinuousInput(-Math.PI, Math.PI);
   
    super.initialize();
  }

  // Called once the command ends or is interrupted. 
  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    dt.drive(new ChassisSpeeds());
  }

  @Override 
  public void execute(){
    dt.drive(new ChassisSpeeds(
      speed,0,rotationPID.calculate(dt.getGyroAngle().getRadians())));
      super.execute();
  }

  // Returns true when the command should end.
  
}