package com.gryffingear.y2017.systems;

import com.gryffingear.y2017.config.Constants;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.AnalogGyro;

public class Drivetrain {

	private CANTalon lefta = null;
	private CANTalon leftb = null;
	private CANTalon leftc = null;

	private CANTalon righta = null;
	private CANTalon rightb = null;
	private CANTalon rightc = null;

	private AnalogGyro gyro = null;

	public Drivetrain(int la, int lb, int lc, int ra, int rb, int rc, int gp) {

		lefta = configureTalon(new CANTalon(la), false, false, false, false, Constants.Drivetrain.DRIVETRAIN_RAMP_RATE);
		leftb = configureTalon(new CANTalon(lb), false, false, false, false,  Constants.Drivetrain.DRIVETRAIN_RAMP_RATE);
		leftc = configureTalon(new CANTalon(lc), false, false, false, false, Constants.Drivetrain.DRIVETRAIN_RAMP_RATE);

		righta = configureTalon(new CANTalon(ra), false, false, false, false, Constants.Drivetrain.DRIVETRAIN_RAMP_RATE);
		rightb = configureTalon(new CANTalon(rb), false, false, false, false, Constants.Drivetrain.DRIVETRAIN_RAMP_RATE);
		rightc = configureTalon(new CANTalon(rc), false, false, false, false, Constants.Drivetrain.DRIVETRAIN_RAMP_RATE);

		//gyro = new AnalogGyro(gp);
		//gyro.initGyro();
		//gyro.calibrate();

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

	public double getYaw() {
		return gyro.getAngle();
	}

	public void resetGyro() {
		gyro.reset();
	}

	private CANTalon configureTalon(CANTalon in, boolean brakeState, boolean speedMode, boolean positionMode,
			boolean voltageMode, double rampRate) {

		if (speedMode = true) {
			in.changeControlMode(CANTalon.TalonControlMode.Speed);
		} else if (positionMode = true) {
			in.changeControlMode(CANTalon.TalonControlMode.Position);

		} else if (voltageMode = true) {
			in.changeControlMode(CANTalon.TalonControlMode.Voltage);
		} else {
			in.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		}
		
		in.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

		in.enableBrakeMode(brakeState);
		in.setVoltageRampRate(rampRate);
		in.enableControl();
		in.clearStickyFaults();
		System.out.println("[CANTalon]" + in.getDescription() + " Initialized at device ID: " + in.getDeviceID());
		return in;
	}
}
