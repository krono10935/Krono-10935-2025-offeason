package frc.robot;

public class Utils {
    public static boolean isEqual(double x, double y, double epsilon){
        return Math.abs(x - y) < epsilon;
    }
}
