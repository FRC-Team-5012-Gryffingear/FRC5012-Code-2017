package com.gryffingear.y2017.auton.commands;

import com.gryffingear.y2017.systems.SuperSystem;
import edu.wpi.first.wpilibj.command.Command;

public class setHopperCommand extends Command {
	
	private boolean state = false;
	
	public setHopperCommand(boolean state) {
		
		this.state = state;
	}
	protected void initialize() {
		SuperSystem.getInstance().intake.setHopper(state);
	}

	protected boolean isFinished() {
		return this.isTimedOut();
	}

	protected void execute() {

	}

	protected void end() {
	
	}

	protected void interrupted() {
		SuperSystem.getInstance().intake.setHopper(false);
	}

}
