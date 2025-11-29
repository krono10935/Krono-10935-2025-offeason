// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Subsystems.Arm.ArmConstants;
import frc.robot.Subsystems.Arm.ArmSubsystem;
import frc.robot.Subsystems.Arm.ArmConstants.ArmLevel;
import frc.robot.Subsystems.Gripper.Gripper;
import frc.robot.Subsystems.Gripper.GripperConstants;
import frc.robot.Subsystems.Gripper.GripperConstants.GamePiece;
import frc.robot.Subsystems.Vision.Vision;
import frc.robot.Subsystems.Vision.Vision.VisionConsumer;
import frc.robot.Subsystems.drivetrain.Drivetrain;
import frc.robot.Subsystems.drivetrain.swerve.Swerve;
import frc.robot.commands.Arm.setArmLevelCommand;
import frc.robot.commands.Gripper.IntakeCommand;
import frc.robot.commands.Gripper.ReleaseCommand;
import frc.robot.commands.Gripper.ReleaseCommandNoBeamBreak;
import frc.robot.commands.drivetrain.DriveAutoCommand;
import frc.robot.commands.drivetrain.DriveCommand;


public class RobotContainer {
  public static ArmSubsystem armSubsystem;
  public static Gripper gripper;
  private LoggedDashboardChooser<Command> autoChooser;
  public static Drivetrain drivetrain;
  public static CommandXboxController driveController;
  public static CommandXboxController operatorController;
  private static Command resetGyroCommand;
  private static boolean hasBeamBreak = true;
  private static BooleanSupplier hasBeamBreakSupplier = () -> hasBeamBreak;
  private static Vision vision;
  

  public RobotContainer() {
   
    Supplier<Pose2d> poseSupplier = () -> new Pose2d();
    vision = new Vision(VisionConsumer.NO_OP, poseSupplier);

    // armSubsystem = new ArmSubsystem();
    // gripper = new Gripper();

    // drivetrain = new Swerve(Constants.isRedSupplier);
    // driveController = new CommandXboxController(1);
    // operatorController = new CommandXboxController(0);
    
    // drivetrain.setDefaultCommand(new DriveCommand(drivetrain, driveController));
    


    // Command crossTheLine = new DriveAutoCommand(drivetrain, 2, 1);
    
    // autoChooser = new LoggedDashboardChooser<>("chooser");
    // autoChooser.addDefaultOption("Cross the line", crossTheLine );
    // autoChooser.addOption("L1 score",forwardAutoFactory(1.93, 0.9, ArmLevel.L1));
    // autoChooser.addOption("L2 score",forwardAutoFactory(5, 5, ArmLevel.L2));
    // autoChooser.addOption("L3 score",forwardAutoFactory(5, 5, ArmLevel.L3));

    // SmartDashboard.putData("chooser", autoChooser.getSendableChooser());

    // resetGyroCommand = new InstantCommand(() -> drivetrain.reset(new Pose2d(
    //     drivetrain.getEstimatedPosition().getTranslation(), new Rotation2d())));
   
    // configureBindings();
  }

  private void configureBindings() {
   driveController.a().onTrue(resetGyroCommand);
    
    new Trigger(hasBeamBreakSupplier)
        .and(operatorController.leftBumper())
        .onTrue(new ReleaseCommand(gripper));

    new Trigger(hasBeamBreakSupplier).negate()
        .and(operatorController.leftBumper())
        .whileTrue(new ReleaseCommandNoBeamBreak(gripper));

    operatorController.rightBumper().onTrue(new IntakeCommand(gripper, GamePiece.Coral));

    operatorController.a().onTrue(new setArmLevelCommand(armSubsystem, ArmLevel.HOME)
    .andThen
    (new InstantCommand(()
     -> armSubsystem.setAngleByLevel(ArmConstants.ArmLevel.HOME))));

    operatorController.x().onTrue(new setArmLevelCommand(armSubsystem, ArmLevel.L1));
    operatorController.y().onTrue(new setArmLevelCommand(armSubsystem, ArmLevel.L2));
    operatorController.b().onTrue(new setArmLevelCommand(armSubsystem, ArmLevel.L3));

    operatorController.leftTrigger(0.2).onTrue(new InstantCommand(() -> hasBeamBreak = !hasBeamBreak));

    Command dropSlowly = new InstantCommand(() -> gripper.setPercentOutput(GripperConstants.WEAK_CORAL_EJECT_POWER));
    operatorController.pov(180).onTrue(dropSlowly);
  
  }

  public Command forwardAutoFactory(double dis, double time, ArmLevel level){
    return new SequentialCommandGroup(
      new DriveAutoCommand(drivetrain, dis, time).alongWith(new setArmLevelCommand(armSubsystem, level)),
      new InstantCommand(() -> drivetrain.drive(new ChassisSpeeds())),

      new ReleaseCommand(gripper),

      new RunCommand(
        ()-> drivetrain.drive(new ChassisSpeeds(-1,0,0)), drivetrain)
        .withTimeout(0.5),

      new setArmLevelCommand(armSubsystem, ArmLevel.HOME),

      new RunCommand(
        ()-> drivetrain.drive(new ChassisSpeeds(1,0,0)), drivetrain)
        .withTimeout(0.5)
      
    );
  }

  public Command getAutonomousCommand(){
    return autoChooser.get();
  }

}
