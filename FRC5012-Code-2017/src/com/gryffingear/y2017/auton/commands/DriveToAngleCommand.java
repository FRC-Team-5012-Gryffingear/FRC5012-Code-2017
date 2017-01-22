package com.gryffingear.y2017.auton.commands;

import edu.wpi.first.wpilibj.command.Command;

import com.gryffingear.y2017.systems.SuperSystem;

public class DriveToAngleCommand extends Command {

	private double speed = 0.0;
	private double angle = 0.0;
	private double timeout = 0.0;

	public DriveToAngleCommand(double speed, double angle, double timeout) {

		this.speed = speed;
		this.angle = 0.0;
		this.timeout = timeout;
		this.setTimeout(timeout);

	}

	protected void initialize() {
		SuperSystem.getInstance().drive.resetGyro();
	}

	protected boolean isFinished() {
		return this.isTimedOut();
	}

	protected void execute() {
		double p = 0.02;
		double error = SuperSystem.getInstance().drive.getYaw() - this.angle;
		SuperSystem.getInstance().drive.tankDrive(p * error * speed, -p * error * speed);
	}

	protected void end() {
		SuperSystem.getInstance().drive.tankDrive(0.0, 0.0);
	}

	protected void interrupted() {
		SuperSystem.getInstance().drive.tankDrive(0.0, 0.0);
	}

}
