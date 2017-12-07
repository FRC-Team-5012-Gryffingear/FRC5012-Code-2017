package com.gryffingear.y2017.auton.commands;

import com.gryffingear.y2017.systems.SuperSystem;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeCommand extends Command {
	
	private double position = 0.0;
	private double speed = 0.0;
	private double timeout = 0.0;
	
	public IntakeCommand(double speed, double timeout) {
		
		this.speed = speed;
		this.timeout = timeout;
		this.setTimeout(timeout);
	}

	protected void initialize() {
		
	}

	protected boolean isFinished() {
		return this.isTimedOut();
	}

	protected void execute() {
	

	}

	protected void end() {

	}

	protected void interrupted() {
		
		
	}
}
