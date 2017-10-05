package org.usfirst.frc.team5012.robot.auton.commands;




import org.usfirst.frc.team5012.robot.systems.SuperSystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

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
		SuperSystem.getInstance().utilityarm.set(speed);

	}

	protected void end() {
		SuperSystem.getInstance().utilityarm.set(0);
	}

	protected void interrupted() {
		SuperSystem.getInstance().utilityarm.set(0);
		
	}
}
