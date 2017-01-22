package com.gryffingear.y2017.auton.commands;

import com.gryffingear.y2017.systems.SuperSystem;

import edu.wpi.first.wpilibj.command.Command;

public class ArcadeDriveCommand extends Command {

	private double speed = 0.0;
	private double turn = 0.0;
	private double timeout = 0.0;

	public ArcadeDriveCommand(double speed, double turn, double timeout) {

		this.speed = speed;
		this.turn = turn;
		this.timeout = timeout;
		this.setTimeout(timeout);
	}

	protected void initialize() {
		SuperSystem.getInstance().drive.tankDrive(speed + turn, speed - turn);
	}

	protected boolean isFinished() {
		return this.isTimedOut();
	}

	protected void execute() {

	}

	protected void end() {
		SuperSystem.getInstance().drive.tankDrive(0.0, 0.0);
	}

	protected void interrupted() {
		SuperSystem.getInstance().drive.tankDrive(0.0, 0.0);
	}

}
