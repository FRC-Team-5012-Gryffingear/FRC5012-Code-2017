package com.gryffingear.y2017.auton.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.gryffingear.y2017.systems.SuperSystem;

public class ZeroArmIfNotZeroedCommand extends Command {

	private double speed = 0.0;
	private double angle = 0.0;
	private double timeout = 0.0;
	
	public ZeroArmIfNotZeroedCommand() {
		
		this.timeout = 1.0;
		this.setTimeout(timeout);
		
	}
	
	protected void initialize() {
		if(SuperSystem.getInstance().utilityarm.getZeroed()) {
			SuperSystem.getInstance().utilityarm.setPercentV(0);
		} else {
			SuperSystem.getInstance().utilityarm.setPercentV(0.25);
		}
	}
	
	protected boolean isFinished() {
		return this.isTimedOut() || SuperSystem.getInstance().utilityarm.getZeroed();
	}
	
	protected void execute() {
		
	}
	
	protected void end() {
		SuperSystem.getInstance().utilityarm.setPercentV(0);
		if(!SuperSystem.getInstance().utilityarm.getZeroed()) {
			SuperSystem.getInstance().utilityarm.zeroArm();;
		}
	}
	
	protected void interrupted() {
		SuperSystem.getInstance().utilityarm.setPercentV(0);
	}
	
	
}
