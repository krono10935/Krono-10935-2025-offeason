package frc.robot.Subsystems.drivetrain;


import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Subsystems.drivetrain.gyro.GyroIO;
import frc.robot.Subsystems.drivetrain.gyro.GyroIONavx;
import org.littletonrobotics.junction.AutoLog;
import org.littletonrobotics.junction.Logger;

public abstract class Drivetrain extends SubsystemBase {

    @AutoLog
    public static class DrivetrainInputs {
        public Rotation2d gyroAngle = Rotation2d.kZero;
        public ChassisSpeeds speeds = new ChassisSpeeds();
    }

    private final GyroIO gyroIO;

    private final DrivetrainInputsAutoLogged inputs = new DrivetrainInputsAutoLogged();


    protected Drivetrain() {
        // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
        //       in the constructor or in the robot coordination class, such as RobotContainer.
        //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
        //       such as SpeedControllers, Encoders, DigitalInputs, etc.
        gyroIO = new GyroIONavx();
    }

    /**
     * drives the robot at relative speed
     *
     * @param speeds the target speed of the robot
     */
    public void drive(ChassisSpeeds speeds) {
        var discreteSpeeds = ChassisSpeeds.discretize(speeds, 0.02);
        setChassisSpeed(discreteSpeeds);
        Logger.recordOutput("drivetrain/target speed", discreteSpeeds);
    }

    /**
     * forwards the speeds to the implementation
     *
     * @param speeds the target speed of the robot
     */
    protected abstract void setChassisSpeed(ChassisSpeeds speeds);

    /**
     * return the latest gyro angle
     * (counter clockwise positive)
     *
     * @return the gyro angle
     */
    public Rotation2d getGyroAngle() {
        return inputs.gyroAngle;
    }

    /**
     * return the latest speeds of the robot
     *
     * @return speeds
     */
    public ChassisSpeeds getChassisSpeeds() {
        return inputs.speeds;
    }

    /**
     * adds the vision measurement
     *
     * @param pose      the position the vision think the robot is there
     * @param timestamp the time when the pose was taken
     * @param stdDevs   A Vector with 3 parameters in the following order:
     *                  X standard deviation (in meters).
     *                  Y standard deviation (in meters).
     *                  Theta standard deviation (in radians).
     */
    public abstract void addVisionMeasurement(Pose2d pose, double timestamp, Matrix<N3, N1> stdDevs);

    /**
     * return the latest position of the robot
     *
     * @return the latest pose
     */
    public abstract Pose2d getEstimatedPosition();

    @Override
    public void periodic() {
        inputs.gyroAngle = gyroIO.update();
        updateInputs(inputs);
        Logger.processInputs("drivetrain", inputs);
        Logger.recordOutput("drivetrain/estimated pose", getEstimatedPosition());
    }

    /**
     * update the inputs from the sensors
     * @param inputs the inputs that stores the data
     */
    protected abstract void updateInputs(DrivetrainInputs inputs);

    public void reset(Pose2d newPose){
        gyroIO.reset(newPose.getRotation());
        resetPose(newPose);
    }

    protected abstract void resetPose(Pose2d newPose);
}

