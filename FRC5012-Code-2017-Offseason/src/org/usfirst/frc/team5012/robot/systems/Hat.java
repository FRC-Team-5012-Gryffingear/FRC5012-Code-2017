package org.usfirst.frc.team5012.robot.systems;

import org.usfirst.frc.team5012.robot.Constants;
import org.usfirst.frc.team5012.robot.util.DigitalInput;

import com.ctre.CANTalon;

/**
 * Class that represents the intake subsystem and associated sensors and motors
 * @author jgermita
 *
 */
public class Hat {

	// Instance of intake motor object
    private CANTalon HatMotor = null;
    
    
    
    

    /**
     * Constructor
     * @param im intake motor CAN ID
     * @param abs bump switch DIO port
     */
    public Hat(int im) {

        HatMotor = configureTalon(new CANTalon(im), CANTalon.TalonControlMode.PercentVbus, false, Constants.RampRates.INTAKE_RAMP_RATE);
     
        

    }

    /**
     * Set voltage output to motor
     * @param in throttle signalt to motor - -1.0 to 1.0
     */
    public void set(double in) {
        HatMotor.set(in);
    }
    private CANTalon configureTalon(CANTalon in, CANTalon.TalonControlMode mode, boolean brakeState, double rampRate) {
        in.changeControlMode(mode);
        in.setVoltageRampRate(rampRate);
        in.enableBrakeMode(brakeState);
        in.enableControl();
        in.clearStickyFaults();

        System.out.println("[CANTalon]" + in.getDescription() + " Initialized at device ID: " + in.getDeviceID());
        return in;
    }
}
    
    
    

    