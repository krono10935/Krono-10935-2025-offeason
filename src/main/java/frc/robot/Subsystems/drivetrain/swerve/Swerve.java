package frc.robot.Subsystems.drivetrain.swerve;

import org.photonvision.EstimatedRobotPose;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import frc.robot.Subsystems.drivetrain.Drivetrain;
import frc.robot.Subsystems.drivetrain.DrivetrainConstants;
import frc.robot.Subsystems.drivetrain.gyro.GyroIO;
import frc.robot.Subsystems.drivetrain.gyro.GyroIOReal;
import frc.robot.Subsystems.drivetrain.swerve.module.SwerveModuleBasic;
import frc.robot.Subsystems.drivetrain.swerve.module.SwerveModuleConstants;
import frc.robot.Subsystems.drivetrain.swerve.module.SwerveModuleIO;

public class Swerve extends Drivetrain {
    private final SwerveModuleIO[] io = new SwerveModuleIO[4];
    private final SwerveModulePosition[] modulePositions = new SwerveModulePosition[4];

    private final SwerveDriveKinematics kinematics;

    private final GyroIO gyroIO;

    private final SwerveDrivePoseEstimator poseEstimator;

    private Pose2d estimatedRobotPose;
    public Swerve(){
        gyroIO = new GyroIOReal();

        for(int i=0;i<4;i++){
            io[i] = new SwerveModuleBasic();
            modulePositions[i] = io[i].getPosition();
        }

        kinematics = new SwerveDriveKinematics(SwerveModuleConstants.getModuleTranslations());

        poseEstimator = new SwerveDrivePoseEstimator(kinematics, gyroIO.getAngle(), modulePositions, DrivetrainConstants.startPose2d);
    }

    @Override
    protected void setChassisSpeed(ChassisSpeeds speeds) {
        var targetSpeeds = kinematics.toWheelSpeeds(speeds);
        for (int i=0;i<4;i++){
            io[i].setTargetState(targetSpeeds[i]);
        }
    }

    @Override
    public void addVisionMeasurement(Pose2d pose, double timestamp, Matrix<N3, N1> stdDevs) {
        poseEstimator.addVisionMeasurement(pose, timestamp, stdDevs);
    }

    @Override
    public Pose2d getEstimatedPosition() {
        estimatedRobotPose = poseEstimator.getEstimatedPosition();
        return estimatedRobotPose;
    }

    @Override
    protected void updateInputs(DrivetrainInputs inputs) {
        inputs.gyroAngle = gyroIO.getAngle();
        SwerveModuleState[] moduleStates = new SwerveModuleState[4];
        for (int i=0;i<4;i++){
            moduleStates[i] = io[i].getTargetState();
        }
        inputs.speeds = kinematics.toChassisSpeeds(moduleStates);
    }
}
