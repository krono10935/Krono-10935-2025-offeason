package frc.robot.Subsystems.drivetrain.swerve;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import frc.robot.Subsystems.drivetrain.Drivetrain;
import frc.robot.Subsystems.drivetrain.swerve.module.SwerveModuleBasic;
import frc.robot.Subsystems.drivetrain.swerve.module.SwerveModuleConstants;
import frc.robot.Subsystems.drivetrain.swerve.module.SwerveModuleIO;

public class Swerve extends Drivetrain {
    private final SwerveModuleIO[] io = new SwerveModuleIO[4];

    private final SwerveDriveKinematics kinematics;

    public Swerve(){
        for(int i=0;i<4;i++){
            io[i] = new SwerveModuleBasic();
        }
        kinematics = new SwerveDriveKinematics(SwerveModuleConstants.getModuleTranslations());
    }

    @Override
    protected void setChassisSpeed(ChassisSpeeds speeds) {
        var targetSpeeds = kinematics.toWheelSpeeds(speeds);
    }

    @Override
    public void addVisionMeasurement(Pose2d pose, double timestamp, Matrix<N3, N1> stdDevs) {

    }

    @Override
    public Pose2d getEstimatedPosition() {
        return null;
    }

    @Override
    protected void updateInputs(DrivetrainInputs inputs) {

    }
}
