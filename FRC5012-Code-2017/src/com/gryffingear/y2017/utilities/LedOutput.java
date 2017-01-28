package com.gryffingear.y2017.utilities;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * Class that represents an LED output on the PCM's solenoid outputs. 
 * @author jgermita
 *
 */
public class LedOutput {
	
	Solenoid led = null;
	
	/**
	 * Constructor.
	 * @param id id of the solenoid port the LED is connected to
	 * @param pcmId CAN ID of the pcm
	 */
	public LedOutput(int id, int pcmId) {
		led = new Solenoid(pcmId, id);
	}
	
	
	/**
	 * Sets the output of the led. true for on, false for off.
	 * @param state
	 */
	public void set(boolean state) {
		led.set(state);
	}
	
	/**
	 * blink the output. output will be on for 0.5*period, off for 0.5*period.
	 * @param period
	 */
	public void blink(int period) {
		this.set(((System.currentTimeMillis() % period)) < (period/2));
	}

}
