package com.gryffingear.y2017.utilities;
import edu.wpi.first.wpilibj.PIDOutput;

/**
 * Dummy PIDOutput object that writes to a double value instead of directly to a system.
 * @author jgermita
 *
 */
public class GryffinPIDOutput implements PIDOutput{
	private double out = 0.0;
	
	
	public GryffinPIDOutput() {
		
	}

	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		out = output;
	}
	
	public double get() {
		return out;
	}

}
