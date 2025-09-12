package frc.robot.subsystems.Arm;

import org.littletonrobotics.junction.AutoLog;

import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.controllers.Controller;

public interface ArmIO {
    @AutoLog
    public static class ArmInputs {
        public boolean atSetPoint;
        public double currentPos; //absolute
    }

    /*
     * reset the encoder of the arm motor
     */
    public void resetEncoder();

    /*
     * update the auto logged inputs
     */
    public void update(ArmInputs inputs);

    /*
     * set the armMotor position (absolute)
     */
    public void setMotorPos(double pos);

    /*
     * is the arm at it's setpoint
     */
    public boolean atSetPoint();


}
