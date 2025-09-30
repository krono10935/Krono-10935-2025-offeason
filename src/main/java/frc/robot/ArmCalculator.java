package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class ArmCalculator {

    private static final double ARM_LENGTH = 20;
    private static final double INCH_TO_CM = 2.54;
    private static final double[] REEF_CENTER = {176 * INCH_TO_CM, 180 * INCH_TO_CM};
    private static final double DISTANCE_FROM_CENTER_TO_WALL_REEF = 32.75 * INCH_TO_CM;
    private static final double ARM_FLOOR_OFFSET = 0;

    /**
     * 
     * @param heightCm of the place you are trying to reach
     * @param panel number, 0 being directly in front of the driver station, going clockwise
     * @return
     */
    public static Translation2d coordinateTranslation2d(double heightCm, int panel) {
        double distance = calcDistance(calcHeight(heightCm)); // Find the distance between the robot and the reef center
        double[] coords = calcCoordinates(distance, panel); // Find the coordinates of the robot on the field based on the distance and the panel

        double xCm = coords[0];
        double yCm = coords[1];

        return new Translation2d(xCm, yCm);
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
        return rad2Deg(Math.asin(heightCm / ARM_LENGTH)); // Find the angle of the arm based on the height and arm length using trigonometry
    }

    private static double calcDistance(double heightCm) {
        return Math.sqrt(ARM_LENGTH * ARM_LENGTH - heightCm * heightCm); // Find the distance between the robot and the reef center using the Pythagorean theorem
    }

    private static double[] calcCoordinates(double distance, int panel) {
        // Panel 0 is the panel in front of the driver station, panels increment clockwise
        double angle = deg2Rad(panel * 60.0);
        double dx = (distance + DISTANCE_FROM_CENTER_TO_WALL_REEF) * Math.cos(angle);
        double dy = (distance + DISTANCE_FROM_CENTER_TO_WALL_REEF) * Math.sin(angle);

        double x = REEF_CENTER[0] - dx; // Subtract because how cosine works
        double y = REEF_CENTER[1] + dy; // Add because how sine works
        return new double[]{x, y};
    }

    private static double calcHeight(double floorOffsetCm) {
        return floorOffsetCm - ARM_FLOOR_OFFSET; // Calculate the height of the point attempted to reach from the arm height as the floor
    }
}
