package com.gryffingear.y2017.auton.commands;

import com.gryffingear.y2017.systems.SuperSystem;
import com.gryffingear.y2017.config.Constants;

import edu.wpi.first.wpilibj.command.Command;

public class setArmPositionCommand extends Command{
	
	private double armPosition = Constants.UtilityArm.UTILITY_ARM_GROUND_POSITION;
	private double timeout = 0.0;
	public setArmPositionCommand(double armPosition, double timeout) {
	
		this.armPosition = armPosition;
		this.timeout = timeout;
		this.setTimeout(timeout);
		
		
	}

	protected void initialize() {
		
	}

	protected boolean isFinished() {
		return this.isTimedOut();
	}

	protected void execute() {}
		

	

	protected void end() {

		
	}

	protected void interrupted() {
		
	}


}
