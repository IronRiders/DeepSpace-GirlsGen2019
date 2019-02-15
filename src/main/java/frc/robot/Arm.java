package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DigitalInput;
 
public class Arm{
    private CANSparkMax spark;
    private CANEncoder encoder;
    private CANPIDController pid;
    private final double pConstant = 0.6;
    private final double iConstant = 0.001;
    private final double dConstant = 1.0;
    private final double fConstant = 0.0;
    private final int maxMotorAmps = 6;
    DigitalInput limitSwitch;
    int enocderPulsesPerRevolution = 100; //reminder:these are built in encoders that are different than the 1024 ones
    private final double mediumHatch = 18.5;
    private final double mediumCargo = 27.0;
    private final double highHatch = 46.5;
    private final double highCargo = 55.0;

    private final int mediumHatchRevolutions = 13;
    private final int mediumCargoRevolutions = 17;
    private final int highHatchRevolutions = 29;


    public Arm(int portNumber, int limitSwitchPort){
        spark = new CANSparkMax(portNumber, MotorType.kBrushless);
        encoder = spark.getEncoder();
        pid = spark.getPIDController();
        pid.setD(dConstant);
        pid.setP(pConstant);
        pid.setI(dConstant);
        pid.setFF(fConstant);
        spark.setSecondaryCurrentLimit(maxMotorAmps);
        limitSwitch = new DigitalInput(limitSwitchPort);


    }

    public void move(int numRevolutions){
        pid.setReference(numRevolutions, ControlType.kPosition);

    }
    public void pushySwitch(){
        // 1 is true(open) , zero is false(closed)
        if (limitSwitch.get()) {
            spark.set( -0.3);
        }   
        else {
            spark.set(0);
        }
    }

    public void stop(){
        spark.set(0);
    }
    public void getToMediumHatch(){
        move(mediumHatchRevolutions);
    }

    public void getToMediumCargo(){
        move(mediumCargoRevolutions);
    }

    public void getToHighHatch(){
        move(highHatchRevolutions);
    }

}
