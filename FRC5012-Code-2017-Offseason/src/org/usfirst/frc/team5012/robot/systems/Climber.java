package org.usfirst.frc.team5012.robot.systems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Class that represents the climber system
 * @author jgermita
 *
 */
public class Climber {

	private CANTalon climberMotorA = null;
	private CANTalon climberMotorB = null;

	/**
	 * Constructor
	 * @param cma climber A CAN ID
	 * @param cmb climber B CAN ID
	 */
	public Climber(int cma, int cmb) {

		climberMotorA = configureTalon(new CANTalon(cma), CANTalon.TalonControlMode.PercentVbus, true, 0);
		climberMotorB = configureTalon(new CANTalon(cmb), CANTalon.TalonControlMode.PercentVbus, true, 0);

	}

	/**
	 * sets the output voltage to the climber
	 * @param in
	 */
	public void set(double in) {
		climberMotorA.set(in);
		climberMotorB.set(in);
	}

	/**
	 * configures uninstantiated talons
	 * @param in
	 * @param mode
	 * @param brakeState
	 * @param rampRate
	 * @return
	 */
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




    