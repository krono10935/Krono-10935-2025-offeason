package frc.robot.Subsystems.Arm;
import java.util.function.Function;

import frc.robot.Subsystems.Arm.ArmConstants.ArmFeedForwardInputs;
public class ArmFeedForward implements Function<Double,Double> {
    double Kg;
    
    public ArmFeedForward(double kg) {
        this.Kg=Kg;
    }

    @Override
    public Double apply(Double Position) {
        
        return Double.valueOf(  Kg * Math.cos((Double)Position));
        
    }

    
}
