// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.Arm.setArmLevelCommand;
import frc.robot.commands.Gripper.HoldCommand;
import frc.robot.commands.Gripper.IntakeCommand;
import frc.robot.commands.Gripper.ReleaseCommand;
import frc.robot.commands.drivetrain.DriveCommand;
import frc.robot.commands.drivetrain.FinishPathCommand;
import frc.robot.subsystems.Arm.ArmSubsystem;
import frc.robot.subsystems.Arm.ArmConstants.ArmLevel;
import frc.robot.subsystems.Gripper.Gripper;
import frc.robot.subsystems.Gripper.GripperConstants.GamePiece;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.subsystems.drivetrain.swerve.Swerve;
import io.github.captainsoccer.basicmotor.gains.PIDGains;


public class RobotContainer {
  public static ArmSubsystem armSubsystem;
  public static Gripper gripper;
  private SendableChooser<Command> autoChooser;
  public static Drivetrain drivetrain;
  public static CommandXboxController driveController;



  public RobotContainer() {
    armSubsystem = new ArmSubsystem();
    armSubsystem.setDefaultCommand(new setArmLevelCommand(armSubsystem, ArmLevel.L1));
    gripper = new Gripper();
    gripper.setDefaultCommand(new ReleaseCommand(gripper));
    drivetrain = new Swerve(Constants.isRedSupplier);
    driveController = new CommandXboxController(0);
    drivetrain.setDefaultCommand(new FinishPathCommand(drivetrain, new PIDGains(), new PIDGains()));
    
    autoChooser = AutoBuilder.buildAutoChooser();

    SmartDashboard.putData("Auto Chooser", autoChooser);
    
    configureBindings();
  }

  private void configureBindings() {}

  public Command getAutonomousCommand() {
    try {
      PathPlannerPath path = PathPlannerPath.fromPathFile("a");
      return AutoBuilder.followPath(path);
    } catch (Exception e){
      DriverStation.reportError("Big oops:" + e.getMessage(), e.getStackTrace());
      return Commands.none();
    }
  }
}
