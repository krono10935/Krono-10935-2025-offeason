package frc.robot.Subsystems.Arm;
import java.util.function.Function;
import edu.wpi.first.math.util.Units;
import lombok.AllArgsConstructor;



@AllArgsConstructor
public class ArmFeedForward implements Function<Double,Double> {
    private double Kg;
    
    @Override
    public Double apply(Double Position) {
        return Double.valueOf(Kg * Math.sin(Units.rotationsToRadians(Position)));
        
    }
}
