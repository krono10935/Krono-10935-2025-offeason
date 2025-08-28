// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems.drivetrain.gyro;

import edu.wpi.first.math.geometry.Rotation2d;

/** Add your docs here. */
public interface GyroIO {
    
    public static class GyroIOInputs{
        public Rotation2d angle = new Rotation2d();
        public double yaw = 0;
        public double pitch = 0;
        public double roll = 0;
        public double yawRate = 0;
        public double accelerationX = 0;
        public double accelerationY = 0;

    }

    public double getAngle();


    public void updateInputs(GyroIOInputs inputs);
}
