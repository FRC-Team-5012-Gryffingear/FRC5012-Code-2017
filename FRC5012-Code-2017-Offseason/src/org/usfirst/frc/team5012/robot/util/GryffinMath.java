package org.usfirst.frc.team5012.robot.util;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * A set of math utilities for team 5012.
 * 
 * @author Jeremy
 *
 */
public class GryffinMath {

	/**
	 * Compares two numbers to see if they are within an acceptable tolerance of
	 * eachother.
	 * 
	 * @param a
	 * @param b
	 * @param tolerance
	 * @return
	 */
	public static boolean equalsTolerance(double a, double b, double tolerance) {

		return Math.abs(a - b) < tolerance;
	}

	/**
	 * Scales an input of a range between istart and istop to a range between
	 * ostart and ostop
	 *
	 * @param value
	 *            input value
	 * @param istart
	 *            input value's lower limit
	 * @param istop
	 *            input value's upper limit
	 * @param ostart
	 *            output value's lower limit
	 * @param ostop
	 *            output value's upper limit
	 * @return the scaled value
	 */
	public static double map(double value, double istart, double istop, double ostart, double ostop) {

		return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
	}

	/**
	 * Checks to see if input is between low and high
	 *
	 * @param input
	 *            input value
	 * @param low
	 *            lower threshold
	 * @param high
	 *            upper threshold
	 * @return true if low < input < high
	 */
	public static boolean isInBand(double input, double low, double high) {

		return input > low && input < high;
	}

	/**
	 * Creates a deadband.
	 *
	 * @param in
	 * @param width
	 * @return
	 */
	public static double deadband(double in, double width) {

		if (Math.abs(in) < width) {
			in = 0;
		}
		return in;
	}

	/**
	 * Truncates an input(value) to specified decimal places
	 *
	 * @param value
	 * @param places
	 * @return
	 */
	public static double truncate(double value, double places) {

		double multiplier = Math.pow(10, places);
		return Math.floor(multiplier * value) / multiplier;
	}

	public static double fMod(double value, double x) {

		// Negate if and only if base is negative.
		// (Java's modulo isn't mathematically pretty in this way.)
		double sign = (value < 0) ? -1 : 1;
		return sign * (Math.abs(x) % Math.abs(value));
	}

	public static double cap(double in, double low, double high) {

		if (in < low) {
			in = low;
		}
		if (in > high) {
			in = high;
		}
		return in;
	}

	/**
	 * Returns a curve similar to the square curve, but the negative range is
	 * inverted K is a scaling constant. default to 1 if no scaling needed
	 *
	 * @param in
	 * @param k
	 * @return
	 */
	public static double signedSquare(double in, double k) {

		return in * Math.abs(in) * k;
	}

	/**
	 * Drive algorithm for FRC5012 2015
	 * 
	 * @param l
	 *            left joystick input
	 * @param r
	 *            right joystick input
	 * @param turbo
	 *            flag to control turbo mode. default is off.
	 * @return
	 */
	public static double[] gryffinDrive(double l, double r, boolean turbo) {

		double answer[] = { 0.0, 0.0 };

		double scalar = 0.6; // Tune this between 0.0-1.0 to adjust throttle
								// sensitivity.
		double tSens = 1.0; // Tune this between 0.0-1.0 to adjust turning
							// sensitivity

		if (turbo) { // If turbo mode, no limit on speed
			scalar = 1.0;
		}

		// Convert to arcade(throttle + turning) commands so we may adjust
		// sensitivities of each orthogonal movement
		double throttle = ((l + r) / 2) * scalar;
		double turning = ((l - r) / 2) * tSens;

		// Turning movement is a signed square curve tuned according to driver
		// feel.
		// turning = signedSquare(turning, scalar * tSens);

		answer[0] = throttle + turning;
		answer[1] = throttle - turning;

		return answer;
	}

	/**
	 * Arcade drive variant of gryffindrive.
	 * 
	 * @param y
	 *            throttle input
	 * @param r
	 *            turning input
	 * @param turbo
	 *            turbo mode input
	 * @return an array containing tank drive outputs
	 */
	public static double[] gryffinDriveArcade(double y, double r, boolean turbo) {

		double answer[] = { 0.0, 0.0 };

		double scalar = 0.55; // Tune this between 0.0-1.0 to adjust overall
								// sensitivity
		double tSens = 0.9; // Tune this between 0.0-1.0 to adjust turning
							// sensitivity

		if (turbo) { // If turbo mode, no limit on speed
			scalar = 1.0;
		} else {
			if (r < -scalar) {
				r = -scalar;
			} else if (r > scalar) {
				r = scalar;
			}
		}

		// sensitivities of each orthogonal movement
		double throttle = y * scalar;
		double turning = -r;

		// Turning movement is a signed square curve tuned according to driver
		// feel.
		// turning = signedSquare(turning, scalar * tSens);
		turning = turning * tSens * scalar;

		answer[0] = throttle + turning;
		answer[1] = throttle - turning;

		return answer;
	}
	
	
	/**
	 * Converts a drivetrain angle to a turret setpoint
	 * @param in
	 * @return
	 */
	public static double toTurretAngle(double in) {
		return in; // todo - tune/scale properly
	}
	
	/**
	 * converts a turret setpoint to a drivetrain angle
	 * @param in
	 * @return
	 */
	public static double fromTurretAngle(double in) {
		return in; // todo - tune/scale properly
		
	}
	
	public static double visionXToAngle(double in) {
		return in; // todo - tune/scale properly
	}
	
	public static double thresholdOnOff(double in, double threshold, double mag) {
		if(in > threshold) return mag;
		if(in < -threshold) return -mag;
		return 0;
	}
	
	public static double thresholdOnOff(double in, double threshold) {
		if(in > threshold) return 1.0;
		if(in < -threshold) return -1.0;
		return 0;
	}
	
	/**
	 * returns -1 or 1 based on alliance color/field side
	 * @return -1 if red, 1 if blue or other
	 */
	public static double invertAllianceSide() {
		double out = 1;
		if(DriverStation.getInstance().getAlliance().equals(DriverStation.Alliance.Red)) {
			out = -1;
		} else {
			out = 1;
		}
		
		return out;
	}

}
