package org.usfirst.frc.team5012.robot.auton.commands;

import org.usfirst.frc.team5012.robot.Constants;
import org.usfirst.frc.team5012.robot.systems.SuperSystem;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class VisionDriveCommand extends Command {

	private double throttle = 0.0;
	private double timeout = 0.0;
	
	public VisionDriveCommand(double throttle, double timeout) {
		
		this.throttle = -throttle;
		this.timeout = timeout;
		this.setTimeout(timeout);
		
	}
	
	protected void initialize() {
	}
	
	protected boolean isFinished() {
		return this.isTimedOut();
	}
	
	protected void execute() {
		double p = -0.0010;
//		double error = SuperSystem.getInstance().pixycam.get() - 1.65;
//		
//		if(error > 0.05 || error < 3.25) {
//			error = 0;
//			// Zero out error if it's unacceptibly high. 
//			// Assume the previous step will get the robot to kinda-sorta the right place
//		}
		
		double error = SuperSystem.getInstance().vision.getX();
		
		double turning = p * error;
		SuperSystem.getInstance().drivetrain.tankDrive(throttle + turning, throttle - turning);
		
	}
	
	protected void end() {
		
	}
	
	protected void interrupted() {
		SuperSystem.getInstance().drivetrain.tankDrive(0.0, 0.0);
	}
	
	
}
