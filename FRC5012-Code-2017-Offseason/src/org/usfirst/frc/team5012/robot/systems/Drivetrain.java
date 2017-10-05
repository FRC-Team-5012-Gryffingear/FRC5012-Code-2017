package org.usfirst.frc.team5012.robot.systems;

import org.usfirst.frc.team5012.robot.Constants;

import com.ctre.CANTalon;
import com.ctre.PigeonImu;

/**
 * Class that represents the drivetrain subsystem and associated sensors
 * @author jgermita
 *
 */
public class Drivetrain {

	private CANTalon lefta = null;
	private CANTalon leftb = null;

	private CANTalon righta = null;
	private CANTalon rightb = null;

	private PigeonImu imu = null;

	/**
	 * Constructor
	 * @param la left A CAN ID
	 * @param lb left B CAN ID
	 * @param ra etc..
	 * @param rb
	 * @param gp Pigeon CAN ID
	 */
	public Drivetrain(int la, int lb, int ra, int rb, int gp) {

		lefta = configureTalon(new CANTalon(la), CANTalon.TalonControlMode.PercentVbus, false, Constants.RampRates.DRIVETRAIN_RAMP_RATE);
		leftb = configureTalon(new CANTalon(lb), CANTalon.TalonControlMode.PercentVbus, false, Constants.RampRates.DRIVETRAIN_RAMP_RATE);
		

		righta = configureTalon(new CANTalon(ra), CANTalon.TalonControlMode.PercentVbus, false, Constants.RampRates.DRIVETRAIN_RAMP_RATE);
		rightb = configureTalon(new CANTalon(rb), CANTalon.TalonControlMode.PercentVbus, false, Constants.RampRates.DRIVETRAIN_RAMP_RATE);
		

		imu = new PigeonImu(gp);
		
	}

	/**
	 * Basic left/right tankdrive method
	 * @param leftv
	 * @param rightv
	 */
	public void tankDrive(double leftv, double rightv) {

		lefta.set(-leftv);
		leftb.set(-leftv);
	
		righta.set(rightv);
		rightb.set(rightv);

	}

	/**
	 * Tank drive, except it takes a 2 element array as its input.
	 * @param input
	 */
	public void tankDrive(double[] input) {

		tankDrive(input[0], input[1]);
	}
	

    private double old_wheel = 0.0;
    private double neg_inertia_accumulator = 0.0;

	/**
	 * Cheesy drive / steering wheel drive method
	 * @param wheel
	 * @param throttle
	 * @param quickTurn
	 */
	public void cheesyDrive(double wheel, double throttle, boolean quickTurn) {
        double left_pwm, right_pwm, overPower;
        double sensitivity = 1.2;
        double angular_power;
        double linear_power;
        double wheelNonLinearity;
        
        wheel = -wheel;
        double neg_inertia = wheel - old_wheel;
        old_wheel = wheel;

        wheelNonLinearity = Constants.CD_WHEEL_NONLIN_HIGH;        //Used to be .9 higher is less sensitive
        // Apply a sin function that's scaled to make it feel better
        wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / Math.sin(Math.PI / 2.0 * wheelNonLinearity);

        double neg_inertia_scalar;
        neg_inertia_scalar = Constants.CD_NEG_INERTIA;
        sensitivity = Constants.CD_SENS_LOW;
        
        if (wheel * neg_inertia > 0) {
            neg_inertia_scalar = Constants.CD_NEG_INERTIA*1.66;
        } else {
            if (Math.abs(wheel) > 0.65) {
                neg_inertia_scalar = Constants.CD_NEG_INERTIA*3.33;
            } else {
                neg_inertia_scalar = Constants.CD_NEG_INERTIA;
            }
        }
        sensitivity = Constants.CD_SENS_HIGH; //lower is less sensitive

      /*  if (Math.abs(throttle) > 0.1) {
            sensitivity = .9 - (.9 - sensitivity) / Math.abs(throttle);
        }
    */
        //neg_inertia_scalar *= .4;
        double neg_inertia_power = neg_inertia * neg_inertia_scalar;
        if (Math.abs(throttle) >= 0.05 || quickTurn) {
            neg_inertia_accumulator += neg_inertia_power;
        }
        wheel = wheel + neg_inertia_accumulator;
        if (neg_inertia_accumulator > 1) {
            neg_inertia_accumulator -= 1;
        } else if (neg_inertia_accumulator < -1) {
            neg_inertia_accumulator += 1;
        } else {
            neg_inertia_accumulator = 0;
        }

        linear_power = throttle;

        if (quickTurn) {
            overPower = 1.0;
            sensitivity = 1.0;
            angular_power = wheel;
        } else {
            overPower = 0.0;
            angular_power = Math.abs(throttle) * wheel * sensitivity;
            
            //angular_power *= -Math.signum(linear_power);	// Car steering code. comment out to disable. 
        	//angular_power *= -1;
        }
        
        right_pwm = left_pwm = linear_power;
        left_pwm += angular_power;
        right_pwm -= angular_power;

        if (left_pwm > 1.0) {
            right_pwm -= overPower * (left_pwm - 1.0);
            left_pwm = 1.0;
        } else if (right_pwm > 1.0) {
            left_pwm -= overPower * (right_pwm - 1.0);
            right_pwm = 1.0;
        } else if (left_pwm < -1.0) {
            right_pwm += overPower * (-1.0 - left_pwm);
            left_pwm = -1.0;
        } else if (right_pwm < -1.0) {
            left_pwm += overPower * (-1.0 - right_pwm);
            right_pwm = -1.0;
            
        }
        tankDrive((left_pwm), (right_pwm));
    }

	/**
	 * Returns the yaw angle value from the pigeon
	 * @return
	 */
	public double getYaw() {
		double[] ypr = new double[3];
		imu.GetYawPitchRoll(ypr);
		
		return ypr[0];
	}
	
	/**
	 * gets the yaw RATE value from the pigeon
	 * @return
	 */
	public double getRawRate() {
		double[] xyz = new double[3];
		imu.GetRawGyro(xyz);
		
		return xyz[2];
		
	}

	/**
	 * Resets yaw value to 0
	 */
	public void resetGyro() {
		imu.SetYaw(0);
		
	}

	/**
	 * Configures uninstantiated talons
	 * @param in
	 * @param mode
	 * @param brakeState
	 * @param rampRate
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
