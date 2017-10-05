package org.usfirst.frc.team5012.robot;


import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveStraightAuton extends CommandGroup{

	public DriveStraightAuton () {
		
	    this.addSequential(new DriveStraightCommand(.4, 0, 1.5));
		this.addSequential(new DriveStraightCommand(0, 0, 0.0));
	}
}
