package com.gryffingear.y2017.utilities;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * Class that represents a solenoid with filtered inputs to better manage air usage
 * @author jgermita
 *
 */
public class GryffinSolenoid {
	private Solenoid sol = null;
	int id = -1, pcm = 0;
	int mode = 0;
	long timeout = 0;
	
	/**
	 * Constructor.
	 * @param id
	 * @param pcm
	 */
	public GryffinSolenoid(int id, int pcm) {
		this.id = id;
		this.pcm = pcm;
		sol = new Solenoid(pcm, id);
	}
	
	/**
	 * Constructor.
	 * @param id
	 */
	public GryffinSolenoid(int id) {
		this.id = id;
		sol = new Solenoid(id);
	}
	
	public static class Mode {
		/**
		 * Raw mode, neither rising nor falling edges are filtered.
		 */
		public static final int RAW = 0;
		/**
		 * Rising mode, rising edge will be filtered, falling will not.
		 */
		public static final int RISING = 1;
		/**
		 * Falling mode, falling edge will be filtered, rising will not.
		 */
		public static final int FALLING = 2;
		/**
		 * All mode, all edges will be filtered
		 */
		public static final int ALL = 3;
		/**
		 * Toggle mode, output will change state only on rising edge of input. 
		 */
		public static final int TOGGLE = 4;
	}
	
	/**
	 * 
	 * @param mode
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	/**
	 * Set timeout in milliseconds for rising, falling, and all edge filtering modes.
	 * 0ms will make rising, falling, and all modes behave like raw mode. 
	 * @param millis
	 */
	public void setTimeout(long millis) {
		this.timeout = millis;
	}
	
	
	private boolean prevInput = false;
	boolean output = false;
	long start = 0;
	
	/**
	 * Sets the output the solenoid. output behavior depends on the selected mode. 
	 * Call setMode() to specify an operating mode.
	 * 
	 * @param input
	 */
	public void set(boolean input) {
		switch(this.mode) {
		case Mode.RAW:
			output = input;
			break;
			
		case Mode.RISING:
			if((prevInput != input) && input) {
				start = System.currentTimeMillis();
			}
			
			output = (System.currentTimeMillis() - start > timeout) && input;
			break;
			
		case Mode.FALLING:
			if((prevInput != input) && !input) {
				start = System.currentTimeMillis();
			}
			
			output = (System.currentTimeMillis() - start > timeout) && !input;
			break;
			
		case Mode.ALL:
			if((prevInput != input)) {
				start = System.currentTimeMillis();
			}
			
			if(System.currentTimeMillis() - start > timeout) {
				output = input;
			}
			break;
			
		case Mode.TOGGLE:
			if((prevInput != input) && input) {
				output = !output;
			}
			break;
			
		default:
			System.out.println("MODE NOT SET OR NOT DEFINED ON SOLENOID " + id + " / PCM " + pcm + "!");
			break;
		}
		
		this.sol.set(output);
		
		prevInput = input;
	}
	
	/**
	 * Gets the current output state. 
	 * @return
	 */
	public boolean get() {
		return output;
	}
	
}
