package frc.robot;

import java.util.concurrent.TransferQueue;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;

public class ArmCalculator {

    private static final double ARM_LENGTH = 61; // cm
    private static final double INCH_TO_CM = Constants.INCH_TO_CM;
    private static final Translation2d REEF_CENTER = new Translation2d(176.25 * INCH_TO_CM, 406);
    private static final Translation2d ARM_TO_ROBOT = new Translation2d(0,0);//18.6, 1); // cm
    private static final double DISTANCE_FROM_CENTER_TO_WALL_REEF = 32.75 * INCH_TO_CM;
    private static final double ARM_FLOOR_OFFSET = 85; // cm
    private static final double TUBE_OFFSET = 6.47 * INCH_TO_CM; // cm
    private static final double ROBOT_WIDTH_HALF = 100; // cm, untested

    /**
     * 
     * @param heightCm of the place you are trying to reach
     * @param panel number, 0 being directly in front of the driver station, going clockwise
     * @return
     */
    public static Pose2d[] coordinateTranslation2d(double heightCm, int panel) {
        // System.out.println("Height: " + calcHeight(heightCm));
        double distance = calcDistance(calcHeight(heightCm)); // Find the distance between the robot and the reef center
        Translation2d midCoords = calcCoordinates(distance, panel); // Find the coordinates of the robot on the field based on the distance and the panel

        return calcCoordinatesFromMid(midCoords, panel); // Return the coordinates of the robot on the field
    }

    public static Rotation2d armAngle(double heightCm){
        return Rotation2d.fromDegrees(calcAngle(calcHeight(heightCm))); // Find the arm angle based on the height of the the point attempted to reach
    }

    private static double rad2Deg(double rad) {
        return rad * (180.0 / Math.PI); // Standard formula for conversion between radians and degrees
    }

    private static double deg2Rad(double deg) {
        return deg / (180.0 / Math.PI); // Standard formula for conversion between degrees and radians
    }

    private static double calcAngle(double heightCm) {
        System.out.println("height: " + heightCm);
        System.err.println("modunigger: " + (heightCm / ARM_LENGTH) % 1);
        System.out.println("deg:" +rad2Deg(Math.asin((heightCm / ARM_LENGTH) % 1) + Math.PI / 2));
        
        return rad2Deg(Math.asin((heightCm / ARM_LENGTH) % 1) + Math.PI / 2); // Find the angle of the arm based on the height and arm length using trigonometry
    }

    private static double calcDistance(double heightCm) {
        double det = ARM_LENGTH * ARM_LENGTH - heightCm * heightCm;
        if (Math.signum(det) < 0) return ROBOT_WIDTH_HALF;
        return Math.sqrt(det); // Find the distance between the robot and the reef center using the Pythagorean theorem
    }

    private static Translation2d calcCoordinates(double distance, int panel) {
        // Panel 0 is the panel in front of the driver station, panels increment clockwise
        // System.out.println("Dis" + distance);
        double angle = deg2Rad(panel * 60.0);
        // System.out.println("Angle: " + angle);
        double dx = (distance + DISTANCE_FROM_CENTER_TO_WALL_REEF) * Math.cos(angle);
        double dy = (distance + DISTANCE_FROM_CENTER_TO_WALL_REEF) * Math.sin(angle);
        // System.out.println("dx" + dx + " dy" + dy);

        return new Translation2d(REEF_CENTER.getX() - dx - ARM_TO_ROBOT.getX(), REEF_CENTER.getY() + dy - ARM_TO_ROBOT.getY());
    }

    private static Pose2d[] calcCoordinatesFromMid(Translation2d midTranslation, int panel) {
        // Panel 0 is the panel in front of the driver station, panels increment clockwise
        double angle = deg2Rad(90 + panel * 60.0);

        double poseAngle = -60 * panel;
        double x1 = midTranslation.getX() - TUBE_OFFSET * Math.cos(angle);
        double y1 = midTranslation.getY() + TUBE_OFFSET * Math.sin(angle);

        double x2 = midTranslation.getX() + TUBE_OFFSET * Math.cos(angle);
        double y2 = midTranslation.getY() - TUBE_OFFSET * Math.sin(angle);

        Pose2d pose1 = new Pose2d(x1 / 100, y1 / 100, Rotation2d.fromDegrees(poseAngle));
        Pose2d pose2 = new Pose2d(x2 / 100, y2 / 100, Rotation2d.fromDegrees(poseAngle));

        // System.out.println("angle" + pose1.getRotation().getDegrees());

        return new Pose2d[] {pose1, pose2};
    }

    private static double calcHeight(double floorOffsetCm) {
        return floorOffsetCm - ARM_FLOOR_OFFSET; // Calculate the height of the point attempted to reach from the arm height as the floor
    }


}
