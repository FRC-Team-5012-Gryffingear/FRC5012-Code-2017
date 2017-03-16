package com.gryffingear.y2017.auton.commands;

import com.gryffingear.y2017.systems.SuperSystem;
import edu.wpi.first.wpilibj.command.Command;

public class runClimberCommand extends Command{
	
	private double speed = 0.0;
	private double timeout = 0.0;
	public runClimberCommand(double speed, double timeout) {
	
		this.speed = speed;
		this.setTimeout(timeout);
		
	}

	protected void initialize() {
		SuperSystem.getInstance().climb.Climb(speed);;
	}

	protected boolean isFinished() {
		return this.isTimedOut();
	}

	protected void execute() {

	}

	protected void end() {
		SuperSystem.getInstance().climb.Climb(0.0);;
	}

	protected void interrupted() {

		SuperSystem.getInstance().climb.Climb(0.0);;
	}

}
