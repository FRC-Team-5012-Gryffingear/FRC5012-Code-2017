package com.gryffingear.y2017.auton.commands;

import com.gryffingear.y2017.systems.SuperSystem;
import edu.wpi.first.wpilibj.command.Command;

public class runShooterCommand extends Command{
	
	private double shooterspeed = 0.0;
	private double preshooterspeed = 0.0;
	private double timeout = 0.0;
	public runShooterCommand(double shooterspeed, double preshooterspeed, double timeout) {
	
		this.shooterspeed = shooterspeed;
		this.preshooterspeed = preshooterspeed;
		this.setTimeout(timeout);
		
		
	}

	protected void initialize() {
		SuperSystem.getInstance().shoot.runShooter(shooterspeed, preshooterspeed);
	}

	protected boolean isFinished() {
		return this.isTimedOut();
	}

	protected void execute() {

	}

	protected void end() {
		SuperSystem.getInstance().shoot.runShooter(0, 0);
	}

	protected void interrupted() {
		SuperSystem.getInstance().shoot.runShooter(0, 0);
	}

}
