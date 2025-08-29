// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.subsystems.drivetrain.DrivetrainConstants;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class DriveCommand extends Command {
  /** Creates a new DriveCommand. */
  private final Drivetrain drivetrain;
  private final CommandXboxController controller;

  private static final double MAX_LINEAR_SPEED = DrivetrainConstants.MAX_LINEAR_SPEED;
  private static final double MIN_LINEAR_SPEED = DrivetrainConstants.MIN_LINEAR_SPEED;
  private static final double MAX_ANGULAR_SPEED = DrivetrainConstants.MAX_ANGULAR_SPEED;
  private static final double MIN_ANGULAR_SPEED = DrivetrainConstants.MIN_ANGULAR_SPEED;
  

  private static final double DEADBAND = 0.1;

  public DriveCommand(Drivetrain drivetrain, CommandXboxController controller) {
    this.drivetrain = drivetrain;
    this.controller = controller;
    addRequirements(drivetrain);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = lerp(controller.getRightTriggerAxis());
    double angularSpeed = angularLerp(controller.getRightTriggerAxis());

    double xSpeed = deadband(-controller.getLeftX()) * speed;
    double ySpeed = deadband(-controller.getLeftY()) * speed;
    double thetaSpeed = deadband(-controller.getRightX()) * angularSpeed;

    drivetrain.drive(ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, thetaSpeed, drivetrain.getGyroAngle()));
  }

  private static double lerp(double value){
    return MIN_LINEAR_SPEED + (MAX_LINEAR_SPEED - MIN_LINEAR_SPEED) * value;
  }

  private static double angularLerp(double value){
    return MIN_ANGULAR_SPEED + (MAX_ANGULAR_SPEED - MIN_ANGULAR_SPEED) * value;
  }

  private static double deadband(double value){
    if (Math.abs(value) < DEADBAND){
      return 0;
    }

    return value;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.drive(new ChassisSpeeds());
  }
}
