package com.gryffingear.y2017.auton.commands;

import com.gryffingear.y2017.systems.SuperSystem;
import edu.wpi.first.wpilibj.command.Command;

public class IntakeCommand extends Command {
	
	private boolean state = false;
	private double speed = 0.0;
	private double timeout = 0.0;
	
	public IntakeCommand(boolean state, double speed, double timeout ) {
		
		this.state = state;
		this.speed = speed;
		this.setTimeout(timeout);
	}

	protected void initialize() {
		SuperSystem.getInstance().intake.setIntake(state);
		SuperSystem.getInstance().intake.runIntake(speed);
	}

	protected boolean isFinished() {
		return this.isTimedOut();
	}

	protected void execute() {

	}

	protected void end() {
		SuperSystem.getInstance().intake.setIntake(false);
		SuperSystem.getInstance().intake.runIntake(0.0);
	}

	protected void interrupted() {
		SuperSystem.getInstance().intake.setIntake(false);
		SuperSystem.getInstance().intake.runIntake(0.0);
	}
}
