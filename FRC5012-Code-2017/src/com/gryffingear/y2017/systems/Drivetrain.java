package com.gryffingear.y2017.systems;

import com.ctre.CANTalon;
import com.ctre.PigeonImu;
import com.gryffingear.y2017.config.Constants;
import com.gryffingear.y2017.utilities.GryffinMath;

public class Drivetrain {

	private CANTalon lefta = null;
	private CANTalon leftb = null;
	private CANTalon leftc = null;

	private CANTalon righta = null;
	private CANTalon rightb = null;
	private CANTalon rightc = null;

	private PigeonImu imu = null;

	public Drivetrain(int la, int lb, int lc, int ra, int rb, int rc, int gp) {

		lefta = configureTalon(new CANTalon(la), CANTalon.TalonControlMode.PercentVbus, false, Constants.Drivetrain.DRIVETRAIN_RAMP_RATE);
		leftb = configureTalon(new CANTalon(lb), CANTalon.TalonControlMode.PercentVbus, false, Constants.Drivetrain.DRIVETRAIN_RAMP_RATE);
		leftc = configureTalon(new CANTalon(lc), CANTalon.TalonControlMode.PercentVbus, false, Constants.Drivetrain.DRIVETRAIN_RAMP_RATE);

		righta = configureTalon(new CANTalon(ra), CANTalon.TalonControlMode.PercentVbus, false, Constants.Drivetrain.DRIVETRAIN_RAMP_RATE);
		rightb = configureTalon(new CANTalon(rb), CANTalon.TalonControlMode.PercentVbus, false, Constants.Drivetrain.DRIVETRAIN_RAMP_RATE);
		rightc = configureTalon(new CANTalon(rc), CANTalon.TalonControlMode.PercentVbus, false, Constants.Drivetrain.DRIVETRAIN_RAMP_RATE);

		imu = new PigeonImu(gp);
		
	}

	public void tankDrive(double leftv, double rightv) {

		lefta.set(-leftv);
		leftb.set(-leftv);
		leftc.set(-leftv);

		righta.set(rightv);
		rightb.set(rightv);
		rightc.set(rightv);

	}

	public void tankDrive(double[] input) {

		tankDrive(input[0], input[1]);
	}
	

    private double old_wheel = 0.0;
    private double neg_inertia_accumulator = 0.0;

	
	public void cheesyDrive(double wheel, double throttle) {
        double left_pwm, right_pwm, overPower;
        double sensitivity = 1.2;
        double angular_power;
        double linear_power;
        double wheelNonLinearity;
        boolean quickTurn = Math.abs(throttle) < .05;//Math.abs(wheel) > .375 &&
        wheel = -wheel;
        double neg_inertia = wheel - old_wheel;
        old_wheel = wheel;

        if (true) {
            wheelNonLinearity = Constants.CD_WHEEL_NONLIN_HIGH;        //Used to be .9 higher is less sensitive
            // Apply a sin function that's scaled to make it feel bette
//            wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
            wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
        } else {
            wheelNonLinearity = Constants.CD_WHEEL_NONLIN_LOW;
            wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
            wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
            wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
        }

        double neg_inertia_scalar;
        if (true) {
            neg_inertia_scalar = Constants.CD_NEG_INERTIA;
            sensitivity = Constants.CD_SENS_LOW;
        } else {
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

            if (Math.abs(throttle) > 0.1) {
                sensitivity = .9 - (.9 - sensitivity) / Math.abs(throttle);
            }
        }
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

        if ((!GryffinMath.isInBand(throttle, -0.2, 0.2) || !(GryffinMath.isInBand(wheel, -0.65, 0.65))) && quickTurn) {
            overPower = 1.0;
            if (true) {
                sensitivity = 1.0;
            } else {
                sensitivity = 1.0;
            }
            sensitivity = 1.0;
            angular_power = wheel;
        } else {
            overPower = 0.0;
            angular_power = Math.abs(throttle) * wheel * sensitivity;
        }

        if(quickTurn) {
            angular_power = GryffinMath.signedSquare(angular_power, 1);   //make turning less sensitive under quickturn
            if(Math.abs(angular_power) >= .745) {
            //    angular_power = 1.0*EagleMath.signum(angular_power);
            }
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

	public double getYaw() {
		double[] ypr = new double[3];
		imu.GetYawPitchRoll(ypr);
		
		return ypr[0];
	}
	
	public double getRawRate() {
		double[] xyz = new double[3];
		imu.GetRawGyro(xyz);
		
		return xyz[2];
		
	}

	public void resetGyro() {
		imu.SetYaw(0);
		
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
