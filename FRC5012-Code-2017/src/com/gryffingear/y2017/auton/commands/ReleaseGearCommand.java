package com.gryffingear.y2017.auton.commands;

import com.gryffingear.y2017.config.Constants;
import com.gryffingear.y2017.systems.SuperSystem;

import edu.wpi.first.wpilibj.command.Command;
public class ReleaseGearCommand extends Command{
	
	private double armPosition = Constants.UtilityArm.UTILITY_ARM_GROUND_POSITION;
	private double intakeSpeed = 0.0;
	private double timeout = 0.0;
	public ReleaseGearCommand(double armPosition, double intakeSpeed, double timeout) {
	
		this.armPosition = armPosition;
		this.intakeSpeed = intakeSpeed;
		this.timeout = timeout;
		this.setTimeout(timeout);
		
		
	}

	protected void initialize() {
		
		}

	protected boolean isFinished() {
		return this.isTimedOut();
	}

	protected void execute() {
		
		SuperSystem.getInstance().utilityarm.setPosition(armPosition);
		SuperSystem.getInstance().utilityarm.runIntake(intakeSpeed);

	}

	protected void end() {
		SuperSystem.getInstance().utilityarm.setPercentV(0);
	}

	protected void interrupted() {
		SuperSystem.getInstance().utilityarm.setPercentV(0);
	}

}
