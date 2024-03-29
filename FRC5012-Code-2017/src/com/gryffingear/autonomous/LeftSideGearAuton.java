package com.gryffingear.autonomous;

import com.gryffingear.y2017.auton.commands.DriveStraightCommand;
import com.gryffingear.y2017.auton.commands.DriveStraightUntilGyroChangeCommand;
import com.gryffingear.y2017.auton.commands.ReleaseGearCommand;
import com.gryffingear.y2017.auton.commands.TurnToAngleCommand;
import com.gryffingear.y2017.auton.commands.setArmPositionCommand;
import com.gryffingear.y2017.config.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftSideGearAuton extends CommandGroup{

	public LeftSideGearAuton() {
		
		this.addSequential(new DriveStraightCommand(.4, 0, 1));
		this.addSequential(new DriveStraightCommand(0, 0, .5));
		this.addSequential(new TurnToAngleCommand(.4, 45, 1));
		this.addSequential(new DriveStraightUntilGyroChangeCommand(.3, 0));
		this.addSequential(new DriveStraightCommand(-.25, 0, 1));
		this.addSequential(new DriveStraightCommand(0, 0, .5));
		this.addSequential(new setArmPositionCommand(Constants.UtilityArm.UTILITY_ARM_SCORING_POSITION, 1));
		this.addSequential(new DriveStraightCommand(.3, 0, .9));
		this.addSequential(new DriveStraightCommand(0, 0, .5));
		this.addSequential(new ReleaseGearCommand(Constants.UtilityArm.UTILITY_ARM_SCORING_POSITION, -.5, 2));
		this.addSequential(new DriveStraightCommand(.3, 0, 1));
		
	}
}
