package com.gryffingear.y2017.systems;

import com.gryffingear.y2017.config.Ports;

import com.ctre.CANTalon;

public class Feeder {

	private CANTalon agitatorMotor = null;
	private CANTalon feederMotor = null;

	public Feeder(int am, int fm) {

		agitatorMotor = configureTalon(new CANTalon(am), CANTalon.TalonControlMode.PercentVbus, false, 0);
		feederMotor = configureTalon(new CANTalon(fm), CANTalon.TalonControlMode.PercentVbus, false, 0);

	}

	public void runFeeder(double feederv) {

		feederMotor.set(feederv);
	}

	public void runAgitator(double agitatorv) {
		agitatorMotor.set(agitatorv);
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
