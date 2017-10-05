package org.usfirst.frc.team5012.robot.systems;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * Class that represents the claw subsystem
 * @author jgermita
 *
 */
public class Claw {
	
	private Solenoid claw = null;
	
	/**
	 * Constructor
	 * @param cp claw solenoid port
	 */
	public Claw(int cp) {
		
		claw = new Solenoid(cp);
		
	}
	
	/**
	 * Sets the output state of the solenoid
	 * @param state
	 */
	public void set(boolean state) {
		this.claw.set(state);
	}

}
