package com.gryffingear.autonomous;
import com.gryffingear.y2017.auton.commands.DriveStraightCommand;
import com.gryffingear.y2017.auton.commands.DriveStraightUntilGyroChangeCommand;
import com.gryffingear.y2017.auton.commands.IntakeCommand;
import com.gryffingear.y2017.auton.commands.WaitCommand;
import com.gryffingear.y2017.auton.commands.setArmPositionCommand;
import com.gryffingear.y2017.config.Constants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterGearAuton extends CommandGroup {

	public CenterGearAuton() {
		
		
		this.addSequential(new setArmPositionCommand(Constants.UtilityArm.UTILITY_ARM_STOW_POSITION, 1));
		this.addSequential(new DriveStraightCommand(.4, 0, .4));
		this.addSequential(new DriveStraightUntilGyroChangeCommand(.3, 0));
		this.addSequential(new DriveStraightCommand(-.25, 0, 1.25));
		this.addSequential(new DriveStraightCommand(0, 0, .5));
		this.addSequential(new setArmPositionCommand(Constants.UtilityArm.UTILITY_ARM_SCORING_POSITION, 1));
		this.addSequential(new DriveStraightCommand(.3, 0, .75));
		this.addSequential(new DriveStraightCommand(0, 0, .5));
		this.addSequential(new setArmPositionCommand(Constants.UtilityArm.UTILITY_ARM_GROUND_POSITION, 0));
		this.addSequential(new WaitCommand(.1));
		this.addSequential(new IntakeCommand(-.5, 1));
		this.addSequential(new DriveStraightCommand(-.3, 0, .75));
		this.addSequential(new DriveStraightCommand(0, 0, 0));
	
	}
}