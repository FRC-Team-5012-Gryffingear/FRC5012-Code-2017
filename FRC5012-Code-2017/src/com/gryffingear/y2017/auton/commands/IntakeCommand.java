package com.gryffingear.y2017.auton.commands;

import com.gryffingear.y2017.systems.SuperSystem;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeCommand extends Command {
	
	private double position = 0.0;
	private double speed = 0.0;
	private double timeout = 0.0;
	
	public IntakeCommand(double position, double speed) {
		
		this.position = position;
		this.speed = speed;
	}

	protected void initialize() {
		SuperSystem.getInstance().utilityarm.setPosition(position);
		SuperSystem.getInstance().utilityarm.runIntake(speed);
	}

	protected boolean isFinished() {
		return true;
	}

	protected void execute() {

	}

	protected void end() {
	}

	protected void interrupted() {
		
	}
}
