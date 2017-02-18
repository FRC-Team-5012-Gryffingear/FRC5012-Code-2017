package com.gryffingear.y2017.systems;

import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.CANTalon;

public class Climber {

	private CANTalon climberMotor = null;
	private DigitalInput climbSensor = null;

	public Climber(int cm, int cs) {

		climberMotor = configureTalon(new CANTalon(cm), CANTalon.TalonControlMode.PercentVbus, true, 0);
		climbSensor = new DigitalInput(cs);
	}

	public void Climb(double climberv) {
		climberMotor.set(climberv);
	}

	public boolean getBump() {
		return !climbSensor.get();
	}

	private boolean getScaled = false;

	public boolean getScaled() {
		return getBump();
	}

	public void update() {
		// System.out.println(getBump());
		getScaled = (getBump());

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
