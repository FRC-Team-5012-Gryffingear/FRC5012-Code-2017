package com.gryffingear.y2017.auton.commands;

import com.gryffingear.y2017.systems.SuperSystem;
import edu.wpi.first.wpilibj.command.Command;

public class runShooterCommand extends Command{
	
	private double shooterspeed = 0.0;
	private double preshooterspeed = 0.0;
	private double timeout = 0.0;
	public runShooterCommand(double shooterspeed, double preshooterspeed) {
	
		this.shooterspeed = shooterspeed;
		this.preshooterspeed = preshooterspeed;
		
		
	}

	protected void initialize() {
		SuperSystem.getInstance().shoot.runShooter(shooterspeed, -preshooterspeed);
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
