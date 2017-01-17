package com.gryffingear.y2017.systems;

import com.gryffingear.y2017.config.Constants;

import com.ctre.CANTalon;

public class Drivetrain {

	private CANTalon lefta = null;
	private CANTalon leftb = null;
	private CANTalon leftc = null;

	private CANTalon righta = null;
	private CANTalon rightb = null;
	private CANTalon rightc = null;

	public Drivetrain(int la, int lb, int lc, int ra, int rb, int rc) {

		lefta = configureTalon(new CANTalon(la));
		leftb = configureTalon(new CANTalon(lb));
		leftc = configureTalon(new CANTalon(lc));

		righta = configureTalon(new CANTalon(ra));
		rightb = configureTalon(new CANTalon(rb));
		rightc = configureTalon(new CANTalon(rc));
	}

	private CANTalon configureTalon(CANTalon in) {

		in.clearStickyFaults();
		in.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		in.setVoltageRampRate(Constants.Drivetrain.RAMP_RATE);
		in.enableControl();
		System.out.println("[CANTalon]" + in.getDescription() + " Initialized at device ID: " + in.getDeviceID());
		return in;
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
}
