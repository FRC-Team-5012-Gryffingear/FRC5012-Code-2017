package org.usfirst.frc.team5012.robot.auton.commands;

import org.usfirst.frc.team5012.robot.systems.SuperSystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ArcadeDriveCommand extends Command {

	private double throttle = 0.0;
	private double turning = 0.0;
	private double timeout = 0.0;
	private boolean invert = false;
	
	public ArcadeDriveCommand(double throttle, double turning, double timeout) {
		
		this.throttle = -throttle;
		this.turning = turning;
		this.timeout = timeout;
		this.setTimeout(timeout);
		
	}
	
	public ArcadeDriveCommand(double throttle, double turning, double timeout, boolean allianceInvert) {
		
		this.throttle = -throttle;
		this.turning = turning;
		this.timeout = timeout;
		this.setTimeout(timeout);
		this.invert = allianceInvert;
	}
	
	protected void initialize() {
		SuperSystem.getInstance().drivetrain.resetGyro();
	}
	
	protected boolean isFinished() {
		return this.isTimedOut();
	}
	
	protected void execute() {
		if(invert) {
			turning *= -1;
		}
		
		SuperSystem.getInstance().drivetrain.tankDrive(throttle + turning, throttle - turning);
		
	}
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		SuperSystem.getInstance().drivetrain.tankDrive(0.0, 0.0);
	}
	
	
}
