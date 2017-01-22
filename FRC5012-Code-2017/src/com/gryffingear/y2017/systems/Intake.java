package com.gryffingear.y2017.systems;

import com.ctre.CANTalon;


import edu.wpi.first.wpilibj.Solenoid;

public class Intake {

	private CANTalon intakeMotor = null;
	private Solenoid intakeSolenoid = null;
	private Solenoid hopperSolenoid = null;

	public Intake(int im, int is, int hs) {

		intakeMotor = configureTalon(new CANTalon(im), false, false, false, false, 0);
		intakeSolenoid = new Solenoid(is);

		hopperSolenoid = new Solenoid(hs);
	}

	public void runIntake(double intakev) {

		intakeMotor.set(-intakev);
	}

	public void setIntake(boolean state) {

		intakeSolenoid.set(state);
	}

	public void setHopper(boolean state) {

		hopperSolenoid.set(state);
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
