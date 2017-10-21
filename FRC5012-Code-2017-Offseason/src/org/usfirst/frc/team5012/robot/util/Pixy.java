package org.usfirst.frc.team5012.robot.util;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;

public class Pixy {
	
	private static AnalogInput in = null;
	private static Solenoid LED = null;
	public Pixy(int port , int LEDport) {
		in = new AnalogInput(port);
		LED = new Solenoid(LEDport);
	}
	
	public double get() {
		double answer = ((0) - in.getVoltage());
		
		if(answer >= 3.25) answer = 3.3/2.0;
		
		return answer;
		
	}
	
	
	public void set(boolean val) {
		LED.set(val);
	}

}
