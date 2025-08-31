package frc.robot.Subsystems.drivetrain;


import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Subsystems.drivetrain.gyro.GyroIO;
import frc.robot.Subsystems.drivetrain.gyro.GyroIONavx;

import frc.robot.Subsystems.drivetrain.gyro.GyroIOSim;
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
        if(RobotBase.isReal())
            gyroIO = new GyroIONavx();
        else
            gyroIO = new GyroIOSim(this::getChassisSpeeds);
    }

    /**
     * Drives the robot at relative speed
     *
     * @param speeds the target speed of the robot
     */
    public void drive(ChassisSpeeds speeds) {
        var discreteSpeeds = ChassisSpeeds.discretize(speeds, 0.02);
        setChassisSpeed(discreteSpeeds);
        Logger.recordOutput("drivetrain/target speed", discreteSpeeds);
    }

    /**
     * Forwards the speeds to the implementation
     *
     * @param speeds the target speed of the robot
     */
    protected abstract void setChassisSpeed(ChassisSpeeds speeds);

    /**
     * Return the latest gyro angle
     * (counter clockwise positive)
     *
     * @return the gyro angle
     */
    public Rotation2d getGyroAngle() {
        return inputs.gyroAngle;
    }

    /**
     * Return the latest speeds of the robot
     *
     * @return speeds
     */
    public ChassisSpeeds getChassisSpeeds() {
        return inputs.speeds;
    }

    /**
     * Adds the vision measurement
     *
     * @param pose      the position where the vision think the robot is there
     * @param timestamp the time when the pose was taken
     * @param stdDevs   A Vector with 3 parameters in the following order:
     *                  X standard deviation (in meters).
     *                  Y standard deviation (in meters).
     *                  Theta standard deviation (in radians).
     */
    public abstract void addVisionMeasurement(Pose2d pose, double timestamp, Matrix<N3, N1> stdDevs);

    /**
     * Return the latest position of the robot
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

        String currentCommand = getCurrentCommand() == null ? "None" : getCurrentCommand().getName();

        Logger.recordOutput("drivetrain/current command", currentCommand);
    }

    /**
     * Update the inputs from the sensors
     * @param inputs the inputs that stores the data
     */
    protected abstract void updateInputs(DrivetrainInputs inputs);

    /**
     * Reset the gyro and the pose estimator states
     * @param newPose
     */
    public void reset(Pose2d newPose){
        gyroIO.reset(newPose.getRotation());
        resetPose(newPose);
    }

    /**
     * Resets the pose of the pose estimator
     * @param newPose
     */
    protected abstract void resetPose(Pose2d newPose);
}

