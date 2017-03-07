package com.gryffingear.y2017.auton.commands;

import com.gryffingear.y2017.systems.SuperSystem;
import edu.wpi.first.wpilibj.command.Command;
public class runAgitatorCommand extends Command{

	private double speed = 0.0;
	private double timeout = 0.0;
	
	public runAgitatorCommand(double speed, double timeout) {
		
		this.speed = speed;
		this.setTimeout(timeout);
		
		
	}
	protected void initialize() {
		SuperSystem.getInstance().feed.runAgitator(speed);
		SuperSystem.getInstance().feed.runFeeder(speed);
	}

	protected boolean isFinished() {
		return this.isTimedOut();
	}

	protected void execute() {

	}

	protected void end() {
		SuperSystem.getInstance().feed.runAgitator(0);
		SuperSystem.getInstance().feed.runFeeder(0);
	}

	protected void interrupted() {
		SuperSystem.getInstance().feed.runAgitator(0);
		SuperSystem.getInstance().feed.runFeeder(0);
	}
}
