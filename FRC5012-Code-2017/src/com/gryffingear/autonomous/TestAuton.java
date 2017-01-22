package com.gryffingear.autonomous;

import com.gryffingear.y2017.auton.commands.ArcadeDriveCommand;
import com.gryffingear.y2017.auton.commands.DriveStraightCommand;
import com.gryffingear.y2017.auton.commands.DriveToAngleCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestAuton extends CommandGroup{
	
	public TestAuton() {
		this.addSequential(new ArcadeDriveCommand(0.0, 0.0, 5));
		this.addSequential(new DriveStraightCommand(0.0, 90, 5));
		this.addSequential(new DriveToAngleCommand(0.0, 45, 5));
	}
}
