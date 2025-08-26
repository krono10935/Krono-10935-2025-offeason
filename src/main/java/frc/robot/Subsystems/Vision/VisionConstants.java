// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems.Vision;

import org.photonvision.PhotonPoseEstimator;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;



public class VisionConstants {
    
    // enum with all the camera constants
    enum CamerasConstants {
        // Define the camera constants for the front camera
        FRONT_CAMERA(
            PhotonPoseEstimator.PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR,
            PhotonPoseEstimator.PoseStrategy.LOWEST_AMBIGUITY,
            "FrontCamera",
            new Transform3d(
                new Translation3d(0.2, 0.0, 0.5),
                new Rotation3d(0, 0, 0)
                
            ),
            0.1, // XY standard deviation
            0.1, // Theta standard deviation
             0.05,// Minimum XY standard deviation
              Math.toRadians(5)// Minimum Theta standard deviation
            
        );
        
       
        /**
         * The main strategy for the camera
         */
        public final PhotonPoseEstimator.PoseStrategy MAIN_STRATEGY;
        
       /**
        * The alternate strategy for the camera
        */
        public final PhotonPoseEstimator.PoseStrategy ALTERNATE_STRATEGY;

        /**
         * The name of the camera
         */
        public final String CAMERA_NAME;

        /**
         * The transform from the robot to the camera
         */ 
        public final Transform3d ROBOT_TO_CAMERA;

        /**
         * The standard deviation factor for XY
         */
        public final double XY_STD_FACTOR;

        /**
         * The standard deviation factor for Theta
         */
        public final double THETA_STD_FACTOR;
        /**
         * The minimum standard deviation for XY (in meters)
         */
        public final double MIN_XY_STD; 
        /**
         * The minimum standard deviation for Theta (in radians)
         */
        public final double MIN_THETA_STD; 

        // Constructor for the camera constants
        CamerasConstants(
        PhotonPoseEstimator.PoseStrategy mainStrategy,
        PhotonPoseEstimator.PoseStrategy alternateStrategy, String cameraName, 
        Transform3d robotToCamera,
        double xyStdFactor,
        double thetaStdFactor,
        double minXYStd,
        double minThetaStd) {
            
            this.MAIN_STRATEGY = mainStrategy;

            this.ALTERNATE_STRATEGY = alternateStrategy;

            this.CAMERA_NAME = cameraName;

            this.ROBOT_TO_CAMERA = robotToCamera;

            //TODO: determaine unit for standard deviation factor
            this.XY_STD_FACTOR = xyStdFactor;

            this.THETA_STD_FACTOR = thetaStdFactor;

            this.MIN_XY_STD = minXYStd;

            this.MIN_THETA_STD = minThetaStd;
        }


        
    }
    /**
     * The field layout for the 2025 FRC game "Reefscape"
     */
    public static final AprilTagFieldLayout FIELD_LAYOUT = AprilTagFieldLayout.loadField(AprilTagFields.k2025ReefscapeWelded);
}
