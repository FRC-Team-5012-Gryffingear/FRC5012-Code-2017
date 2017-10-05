package org.usfirst.frc.team5012.robot.auton.commands;

import org.usfirst.frc.team5012.robot.systems.SuperSystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveStraightCommand extends Command {

	private double speed = 0.0;
	private double angle = 0.0;
	private double timeout = 0.0;
	
	public DriveStraightCommand(double speed, double angle, double timeout) {
		
		this.speed = -speed;
		this.angle = angle;
		this.timeout = timeout;
		this.setTimeout(timeout);
		
	}
	
	protected void initialize() {
		SuperSystem.getInstance().drivetrain.resetGyro();
	}
	
	protected boolean isFinished() {
		return this.isTimedOut();
	}
	
	protected void execute() {
		
		double p = -0.025;
		double error = 0; //SuperSystem.getInstance().drivetrain.getYaw() - this.angle;
		SuperSystem.getInstance().drivetrain.tankDrive(speed + angle, speed - angle);
		
	}
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		SuperSystem.getInstance().drivetrain.tankDrive(0.0, 0.0);
	}
	
	
}
