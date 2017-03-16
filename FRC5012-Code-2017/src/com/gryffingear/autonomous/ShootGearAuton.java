package com.gryffingear.autonomous;

import com.gryffingear.y2017.auton.commands.DriveStraightCommand;
import com.gryffingear.y2017.auton.commands.WaitCommand;
import com.gryffingear.y2017.auton.commands.runAgitatorCommand;
import com.gryffingear.y2017.auton.commands.runShooterCommand;
import com.gryffingear.y2017.auton.commands.setHopperCommand;
import com.gryffingear.y2017.auton.commands.IntakeCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShootGearAuton extends CommandGroup{
	
	public ShootGearAuton() {

//		this.addSequential(new setHopperCommand(false));
//		this.addSequential(new DriveStraightCommand(0.2, 0.0, 6.0));

		
		this.addSequential(new runShooterCommand(0.8, 0.75));
		this.addSequential(new setHopperCommand(true));
		
		this.addSequential(new WaitCommand(2.0));
		this.addSequential(new runAgitatorCommand(0.8));
		this.addSequential(new IntakeCommand(true,.75));

		this.addSequential(new WaitCommand(5.0));
		this.addSequential(new runAgitatorCommand(0.0));
		this.addSequential(new runShooterCommand(0, 0));

		this.addSequential(new IntakeCommand(true,0.0));
		
		this.addSequential(new DriveStraightCommand(0.4, 0.0, 1.5));

	}
}
