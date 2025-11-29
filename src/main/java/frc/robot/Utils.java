package frc.robot;

public class Utils {
    public static boolean isEqual(double x, double y, double epsilon){
        return Math.abs(x - y) < epsilon;
    }

    public static double calculateXSpeedForAuto(double dis, double time){
        return dis / time;
    }
}
