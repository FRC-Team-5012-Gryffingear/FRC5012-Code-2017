package org.usfirst.frc.team5012.robot.systems;

import org.usfirst.frc.team5012.robot.Constants;
import org.usfirst.frc.team5012.robot.util.DigitalInput;

import com.ctre.CANTalon;

/**
 * Class that represents the intake subsystem and associated sensors and motors
 * @author jgermita
 *
 */
public class Intake {

	// Instance of intake motor object
    private CANTalon intakeMotor = null;
    
    // Bump switch to detect gear acquisition
    private DigitalInput bumpSwitch = null;
    

    /**
     * Constructor
     * @param im intake motor CAN ID
     * @param abs bump switch DIO port
     */
    public Intake(int im, int abs) {

        intakeMotor = configureTalon(new CANTalon(im), CANTalon.TalonControlMode.PercentVbus, false, Constants.RampRates.INTAKE_RAMP_RATE);
     
        bumpSwitch = new DigitalInput(abs);

    }

    /**
     * Set voltage output to motor
     * @param in throttle signalt to motor - -1.0 to 1.0
     */
    public void set(double in) {
        intakeMotor.set(in);
    }
    
    /**
     * Gets the state of the bump switch at the bottom of the intake
     * indicates gear acquisition
     * @return
     */
    public boolean getBumpSwitch() {
        return !bumpSwitch.get();
    }

    /**
     * Configures and enables talon on instantiation. 
     * @param in instance of null CANTalon object
     * @param mode control mode to enter
     * @param brakeState brake/coast input
     * @param rampRate vRamp rate
     * @return
     */
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