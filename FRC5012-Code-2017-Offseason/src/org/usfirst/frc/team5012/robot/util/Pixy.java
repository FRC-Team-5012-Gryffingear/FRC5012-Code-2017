package org.usfirst.frc.team5012.robot.util;
import edu.wpi.first.wpilibj.AnalogInput;

public class Pixy {
	
	private static AnalogInput in = null;
	
	public Pixy(int port) {
		in = new AnalogInput(port);
	}
	
	public double get() {
		double answer = ((0) - in.getVoltage());
		
		if(answer >= 3.25) answer = 3.3/2.0;
		
		return answer;
		
	}
	

}
