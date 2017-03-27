package com.gryffingear.y2017.auton.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.gryffingear.y2017.systems.SuperSystem;

public class ZeroArmIfNotZeroedCommand extends Command {

	private double speed = 0.0;
	private double angle = 0.0;
	private double timeout = 1.5;
	
	public ZeroArmIfNotZeroedCommand() {
		
		this.timeout = 1.5;
		this.setTimeout(timeout);
		
	}
	
	protected void initialize() {
			SuperSystem.getInstance().utilityarm.setPercentV(-0.4);
	}
	
	protected boolean isFinished() {
		return this.isTimedOut();
	}
	
	protected void execute() {
		
	}
	
	protected void end() {
		SuperSystem.getInstance().utilityarm.setPercentV(0);
			SuperSystem.getInstance().utilityarm.zeroArm();;
	}
	
	protected void interrupted() {
		SuperSystem.getInstance().utilityarm.setPercentV(0);
	}
	
	
}
