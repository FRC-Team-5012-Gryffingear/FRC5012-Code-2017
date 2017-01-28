package com.gryffingear.autonomous;

import com.gryffingear.y2017.auton.commands.ArcadeDriveCommand;
import com.gryffingear.y2017.auton.commands.WaitCommand;
import com.gryffingear.y2017.auton.commands.DriveStraightCommand;
import com.gryffingear.y2017.auton.commands.TurnToAngleCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestAuton extends CommandGroup{
	
	public TestAuton() {
		this.addSequential(new TurnToAngleCommand(0.5, 90, 1));
		this.addSequential(new WaitCommand(1));
		this.addSequential(new TurnToAngleCommand(0.5, 90, 1));
		this.addSequential(new WaitCommand(1));
		this.addSequential(new TurnToAngleCommand(0.5, 90, 1));
		this.addSequential(new WaitCommand(1));
		this.addSequential(new TurnToAngleCommand(0.5, 90, 1));
		this.addSequential(new WaitCommand(1));
		this.addSequential(new TurnToAngleCommand(0.5, -90, 1));
		this.addSequential(new WaitCommand(1));
		this.addSequential(new TurnToAngleCommand(0.5, -90, 1));
		this.addSequential(new WaitCommand(1));
		this.addSequential(new TurnToAngleCommand(0.5, -90, 1));
		this.addSequential(new WaitCommand(1));
		this.addSequential(new TurnToAngleCommand(0.5, -90, 1));
		this.addSequential(new WaitCommand(1));
	}
}
