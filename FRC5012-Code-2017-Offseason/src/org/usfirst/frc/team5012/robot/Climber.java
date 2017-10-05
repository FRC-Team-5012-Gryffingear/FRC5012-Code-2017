package org.usfirst.frc.team5012.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
public class Climber {

	private CANTalon climberMotorA = null;
	private CANTalon climberMotorB = null;
	private DigitalInput climbSensor = null;

	public Climber(int cma, int cmb) {

		climberMotorA = configureTalon(new CANTalon(cma), CANTalon.TalonControlMode.PercentVbus, true, 0);
		climberMotorB = configureTalon(new CANTalon(cmb), CANTalon.TalonControlMode.PercentVbus, true, 0);
	//	climbSensor = new DigitalInput(cs);
	}

	public void Climb(double climberv) {
		climberMotorA.set(climberv);
		climberMotorB.set(climberv);
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




    