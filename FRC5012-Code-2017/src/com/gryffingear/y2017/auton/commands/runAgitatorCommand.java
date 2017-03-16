package com.gryffingear.y2017.auton.commands;

import com.gryffingear.y2017.systems.SuperSystem;
import edu.wpi.first.wpilibj.command.Command;
public class runAgitatorCommand extends Command{

	private double speed = 0.0;
	private double timeout = 0.0;
	
	public runAgitatorCommand(double speed) {
		
		this.speed = speed;
		
		
	}
	protected void initialize() {
		SuperSystem.getInstance().feed.runAgitator(-speed);
		SuperSystem.getInstance().feed.runFeeder(-speed);
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
