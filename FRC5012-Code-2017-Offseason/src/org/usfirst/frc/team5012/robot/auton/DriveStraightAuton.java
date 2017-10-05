package org.usfirst.frc.team5012.robot.auton;


import org.usfirst.frc.team5012.robot.auton.commands.DriveStraightCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveStraightAuton extends CommandGroup{

	public DriveStraightAuton () {
		
	    this.addSequential(new DriveStraightCommand(.4, 0, 1.5));
		this.addSequential(new DriveStraightCommand(0, 0, 0.0));
	}
}
