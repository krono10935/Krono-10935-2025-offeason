// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import java.util.function.Supplier;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.Arm.setArmLevelCommand;
import frc.robot.commands.Gripper.HoldCommand;
import frc.robot.commands.Gripper.IntakeCommand;
import frc.robot.commands.Gripper.ReleaseCommand;
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
  private static Command intakeCoralSequence;
  private static Command scoreCoralSequence;
  private static Command intakeCoralNoPP;



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
    configureCommands();
  }

  private void configureBindings() {}

  private void configureCommands(){
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
        () -> armSubsystem.isAtSetPoint() && !armSubsystem.getTargetLevel().equals( ArmLevel.CoralIntakeLevel))
      );
      //comment
    
      intakeCoralNoPP  = new SequentialCommandGroup(
        new setArmLevelCommand(armSubsystem, ArmLevel.CoralIntakeLevel),
      // Step three: run intake until coral is detected
      new IntakeCommand(gripper, GamePiece.Coral),
      // Step four: hold the coral and retract the arm to home
      new ParallelCommandGroup(new HoldCommand(gripper).onlyIf(() -> gripper.getGamePiece() == GamePiece.None),
      new setArmLevelCommand(armSubsystem, ArmLevel.HOME))
      .until(
        () -> armSubsystem.isAtSetPoint() && !armSubsystem.getTargetLevel().equals( ArmLevel.CoralIntakeLevel))
      );



    Supplier<Pose2d> desiredPanel = () -> Constants.FieldConstants.reefPose; // Dummy, replace by the driverstation's selection for panel
    // Align to the desired reef panel
    Command alignToReefPanel = drivetrain.driveToPosCommand(desiredPanel.get())
    .andThen(new FinishPathCommand(drivetrain, new PIDGains(), new PIDGains()));
    
    Supplier<ArmLevel> scoreLevelSupplier = () -> ArmLevel.HOME; // Dummy, replace by the driverstation's selection for the scoring level
    
    // Score coral sequence
    scoreCoralSequence = new SequentialCommandGroup(
      // Step one: align to the desired reef panel
      alignToReefPanel,
      // Step two: set arm to the desired scoring level
      new setArmLevelCommand(armSubsystem, scoreLevelSupplier.get()),
      // Step three: release the coral
      new ReleaseCommand(gripper),
      drivetrain.runBackCommand(),
      new setArmLevelCommand(armSubsystem, ArmLevel.HOME)
      
    );
  }

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
