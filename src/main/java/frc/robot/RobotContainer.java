// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.Supplier;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Subsystems.Arm.ArmConstants;
import frc.robot.Subsystems.Arm.ArmSubsystem;
import frc.robot.Subsystems.Arm.ArmConstants.ArmLevel;
import frc.robot.Subsystems.Gripper.Gripper;
import frc.robot.Subsystems.Gripper.GripperConstants.GamePiece;
import frc.robot.Subsystems.Vision.Vision;
import frc.robot.Subsystems.drivetrain.Drivetrain;
import frc.robot.Subsystems.drivetrain.swerve.Swerve;
import frc.robot.commands.Arm.setArmLevelCommand;
import frc.robot.commands.Gripper.HoldCommand;
import frc.robot.commands.Gripper.IntakeCommand;
import frc.robot.commands.Gripper.ReleaseCommand;
import frc.robot.commands.drivetrain.DriveCommand;
import frc.robot.commands.drivetrain.FinishPathCommand;
import io.github.captainsoccer.basicmotor.gains.PIDGains;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class RobotContainer {
  public static ArmSubsystem armSubsystem;
  public static Gripper gripper;
  private SendableChooser<Command> autoChooser;
  public static Drivetrain drivetrain;
  public static CommandXboxController driveController;
  private static Vision vision;
  private static Command intakeCoralSequence;
  private static Command scoreCoralSequence;
  private static Command intakeCoralNoPP;
  private static Command resetGyroCommand;

  public RobotContainer() {
   
   

    //armSubsystem = new ArmSubsystem();
    // armSubsystem.setDefaultCommand(new setArmLevelCommand(armSubsystem,
    // ArmLevel.L1));
    gripper = new Gripper();
    System.out.println("gripper good");

    //gripper.setDefaultCommand(new ReleaseCommand(gripper));
    drivetrain = new Swerve(Constants.isRedSupplier);
    driveController = new CommandXboxController(0);
    //vision = new Vision(drivetrain::addVisionMeasurement, drivetrain::getEstimatedPosition);
    // drivetrain.setDefaultCommand(new FinishPathCommand(drivetrain, new
    // PIDGains(), new PIDGains()));
    drivetrain.setDefaultCommand(new DriveCommand(drivetrain, driveController));

    //autoChooser = AutoBuilder.buildAutoChooser();
    
    resetGyroCommand = new InstantCommand(() -> drivetrain.reset(new Pose2d(
        drivetrain.getEstimatedPosition().getTranslation(), new Rotation2d())));
    //SmartDashboard.putData("Auto Chooser", autoChooser);
    


    //configureCommands();
    configureBindings();
  }

  private void configureBindings() {
   driveController.a().onTrue(resetGyroCommand);

   driveController.rightBumper().onTrue(new SequentialCommandGroup(
           new InstantCommand(() -> {Pose2d target = drivetrain.getClosestReefBySide();
               drivetrain.setPathFinalPose(drivetrain.addArmMeasurement(target, ArmLevel.L1.height,true));}), // right side
           new FinishPathCommand(drivetrain, new PIDGains(5, 0, 1), new PIDGains(1, 0, 0))
           )
    );

    driveController.leftBumper().onTrue(new SequentialCommandGroup(new InstantCommand(() -> {
              Pose2d target = drivetrain.getClosestReefBySide();
              drivetrain.setPathFinalPose(drivetrain.addArmMeasurement(target, ArmLevel.L1.height,false)); // left side
            }),
                    new FinishPathCommand(drivetrain, new PIDGains(5, 0, 0), new PIDGains(1, 0, 0))
            )
    );

    //driveController.b().onTrue(scoreCoralSequence);
    // driveController.x().onTrue(new setArmLevelCommand(armSubsystem, ArmLevel.L2));
    // driveController.y().onTrue(new setArmLevelCommand(armSubsystem, ArmLevel.L3));
    // driveController.b().onTrue(new setArmLevelCommand(armSubsystem, ArmLevel.HOME));
    driveController.b().toggleOnTrue(new HoldCommand(gripper));
    driveController.x().onTrue(new IntakeCommand(gripper, GamePiece.Coral));
    driveController.y().whileTrue(new ReleaseCommand(gripper));
  
  }

  private void configureCommands() {
    // Get to feeder and align yourself to it
    Command alignToFeeder = drivetrain.driveToPosCommand(Constants.FieldConstants.feederPose)
        .andThen(new FinishPathCommand(drivetrain, new PIDGains(), new PIDGains()));

    // Intake coral sequence
    intakeCoralSequence = new SequentialCommandGroup(
        // Step one: get to feeder and align
        alignToFeeder,
        // Step two: set arm to coral intake level
        new setArmLevelCommand(armSubsystem, ArmLevel.CoralIntakeLevel),
        // Step three: run intake until coral is detected
        new IntakeCommand(gripper, GamePiece.Coral),
        // Step four: hold the coral and retract the arm to home
        new ParallelCommandGroup(new HoldCommand(gripper).onlyIf(() -> gripper.getGamePiece() == GamePiece.None),
            new setArmLevelCommand(armSubsystem, ArmLevel.HOME))
            .until(
                () -> armSubsystem.isAtSetPoint() && !armSubsystem.getTargetLevel().equals(ArmLevel.CoralIntakeLevel)));
    // comment

    intakeCoralNoPP = new SequentialCommandGroup(
        new setArmLevelCommand(armSubsystem, ArmLevel.CoralIntakeLevel),
        // Step three: run intake until coral is detected
        new IntakeCommand(gripper, GamePiece.Coral),
        // Step four: hold the coral and retract the arm to home
        new ParallelCommandGroup(new HoldCommand(gripper).onlyIf(() -> gripper.getGamePiece() == GamePiece.None),
            new setArmLevelCommand(armSubsystem, ArmLevel.HOME))
            .until(
                () -> armSubsystem.isAtSetPoint() && !armSubsystem.getTargetLevel().equals(ArmLevel.CoralIntakeLevel)));

    Supplier<Pose2d> desiredPanel = () -> ArmConstants.ArmLevel.L1.panels[0][0]; // Dummy, replace by the driverstation's
                                                                             // selection for panel
    // Align to the desired reef panel
    Command alignToReefPanel = drivetrain.driveToPosCommand(desiredPanel.get())
        .andThen(new FinishPathCommand(drivetrain, new PIDGains(), new PIDGains()));

    Supplier<ArmLevel> scoreLevelSupplier = () -> ArmLevel.HOME; // Dummy, replace by the driverstation's selection for
                                                                 // the scoring level

    // Score coral sequence
    scoreCoralSequence = new SequentialCommandGroup(
        new InstantCommand(() -> System.out.println("Scoring to " + scoreLevelSupplier.get().name())),
        // Step one: align to the desired reef panel
        alignToReefPanel, 
       
        // Step two: set arm to the desired scoring level
        new setArmLevelCommand(armSubsystem, ArmLevel.L1),
        new InstantCommand(() -> System.out.println("arm moving")),
        // Step three: release the coral
        new ReleaseCommand(gripper),
        
        new setArmLevelCommand(armSubsystem, ArmLevel.HOME)

    );
  }

  public Command getAutonomousCommand() {
    try {
      // PathPlannerPath path = PathPlannerPath.fromPathFile("a");
      return AutoBuilder.buildAuto("abc");

    } catch (Exception e) {
      DriverStation.reportError("Big oops:" + e.getMessage(), e.getStackTrace());
      return Commands.none();
    }
  }
}
