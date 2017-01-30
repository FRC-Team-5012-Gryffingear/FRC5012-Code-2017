package com.gryffingear.y2017.utilities;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

/**
 * PIDController. Takes a PIDSource, writes to a double value.
 * @author jgermita
 *
 */
public class GryffinPIDController {
	PIDController controller = null;
	
	public GryffinPIDController(double p, double i, double d, PIDSource source, PIDOutput out) {
		controller = new PIDController(p, i, d, source, out);
		controller.disable();
	}
	
	public void setTolerance(double tolerance) {
		controller.setPercentTolerance(tolerance);
	}
	
	public void setEnabled(boolean en) {
		if(en) {
			controller.enable();
		} else {
			controller.disable();
		}
	}
	
	public double get() {
		return controller.get();
	}
	
	public boolean onTarget() {
		return controller.onTarget();
	}
	
}
