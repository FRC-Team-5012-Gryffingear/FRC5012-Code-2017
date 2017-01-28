package com.gryffingear.y2017.systems;

import com.gryffingear.y2017.config.Ports;

import com.ctre.CANTalon;

public class Stager {

	private CANTalon stagerMotor = null;

	public Stager(int sm) {

		stagerMotor = configureTalon(new CANTalon(sm), false, false, false, false, 0);
	}

	public void runStager(double stagerv) {

		stagerMotor.set(stagerv);
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

		in.enableBrakeMode(brakeState);
		in.setVoltageRampRate(rampRate);
		in.enableControl();
		in.clearStickyFaults();
		System.out.println("[CANTalon]" + in.getDescription() + " Initialized at device ID: " + in.getDeviceID());
		return in;
	}

}
