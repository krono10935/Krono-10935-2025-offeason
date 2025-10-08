package frc.robot.Subsystems.Arm;

import org.littletonrobotics.junction.Logger;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import io.github.captainsoccer.basicmotor.BasicMotor;
import io.github.captainsoccer.basicmotor.controllers.Controller.ControlMode;
import io.github.captainsoccer.basicmotor.measurements.Measurements;
import io.github.captainsoccer.basicmotor.rev.BasicSparkConfig;
import io.github.captainsoccer.basicmotor.rev.BasicSparkMAX;
import io.github.captainsoccer.basicmotor.rev.encoders.RevAbsoluteEncoder;

public class ArmRealMotorIO implements ArmIO{

    private final BasicMotor motor;
    final DutyCycleEncoder armDutyCycleEncoder;


    public ArmRealMotorIO() {
        motor = new BasicSparkMAX(ArmConstants.config);
        armDutyCycleEncoder =  new DutyCycleEncoder(ArmConstants.DUTY_CYCLE_ENCODER_PORT);

        // motor.resetEncoder(armDutyCycleEncoder.get()+ArmConstants.DUTY_CYCLE_ENCODER_ZERO_OFFSET);
        
        

    }

    @Override
    public void resetEncoder() {
        motor.resetEncoder(readAbsEncoderCorrectly());
    }
    @Override
    public void update(ArmInputs inputs) {
        inputs.atSetPoint = motor.atSetpoint();
        inputs.currentAngle = Rotation2d.fromRotations(motor.getPosition());
        Logger.recordOutput("Arm/absolute encoder offset", armDutyCycleEncoder.get());
        Logger.recordOutput( "Arm/asbolute encoder reading good" , readAbsEncoderCorrectly());

        
    }


    //BLACK MAGIC NO ONE MAY TOUCH BUT EYAL!!!
    public double readAbsEncoderCorrectly(){
        return Math.IEEEremainder(1-armDutyCycleEncoder.get()+ArmConstants.DUTY_CYCLE_ENCODER_ZERO_OFFSET-0.05,1);
    }

    public double getMotorPos(){
        return motor.getPosition();
    }

    @Override
    public void setMotorAngle(Rotation2d Angle) {
        motor.getController().reset(getMotorPos(), motor.getVelocity());
        motor.setControl(Angle.getRotations(), ControlMode.PROFILED_POSITION);
    }

    @Override 
    public void stop(){
        motor.stop();
    }
    @Override
    public Rotation2d getVelocity(){
        return Rotation2d.fromRotations(motor.getVelocity());
    }

    @Override
    public void setArmMotorDutyCycle(double duty){
        motor.setControl(duty, ControlMode.VELOCITY);
    }

    @Override
    public void setBrake(){
        motor.setIdleMode(io.github.captainsoccer.basicmotor.BasicMotor.IdleMode.BRAKE);
    }

    @Override
    public void setCoast(){
        motor.setIdleMode(io.github.captainsoccer.basicmotor.BasicMotor.IdleMode.COAST);
    }
    


    
}
