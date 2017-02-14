package com.gryffingear.y2017.systems;

import com.gryffingear.y2017.config.Ports;

import com.ctre.CANTalon;

public class Feeder {

	private CANTalon agitatorMotor = null;
	private CANTalon feederMotor = null;

	public Feeder(int am, int fm) {

		agitatorMotor = configureTalon(new CANTalon(am), false, 0);
		feederMotor = configureTalon(new CANTalon(fm), false, 0);

	}

	public void runFeeder(double feederv) {

		feederMotor.set(feederv);
	}

	public void runAgitator(double agitatorv) {
		agitatorMotor.set(agitatorv);
	}

	private CANTalon configureTalon(CANTalon in, boolean brakeState, double rampRate) {

		in.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		in.enableBrakeMode(brakeState);
		in.setVoltageRampRate(rampRate);
		in.enableControl();
		in.clearStickyFaults();
		System.out.println("[CANTalon]" + in.getDescription() + " Initialized at device ID: " + in.getDeviceID());
		return in;
	}

}
