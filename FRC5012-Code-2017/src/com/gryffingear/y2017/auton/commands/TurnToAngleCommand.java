package com.gryffingear.y2017.auton.commands;

import com.gryffingear.y2017.systems.SuperSystem;

import edu.wpi.first.wpilibj.command.Command;

public class TurnToAngleCommand extends Command {

	private double speed = 0.0;
	private double angle = 0.0;
	private double timeout = 0.0;

	public TurnToAngleCommand(double speed, double angle, double timeout) {

		this.speed = speed;
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
		double p = -0.038;
		double error = SuperSystem.getInstance().drivetrain.getYaw() - this.angle;
		SuperSystem.getInstance().drivetrain.tankDrive(p * error * speed, -p * error * speed);
		
		System.out.println("Is running" );
	}

	protected void end() {
		SuperSystem.getInstance().drivetrain.tankDrive(0.0, 0.0);
	}

	protected void interrupted() {
		SuperSystem.getInstance().drivetrain.tankDrive(0.0, 0.0);
	}
	
	public String toString() {
		return "TestAuton";
	}

}
