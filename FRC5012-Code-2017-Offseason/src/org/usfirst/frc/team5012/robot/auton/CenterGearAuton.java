package org.usfirst.frc.team5012.robot.auton;



import org.usfirst.frc.team5012.robot.auton.commands.ClawCommand;
import org.usfirst.frc.team5012.robot.auton.commands.DriveStraightCommand;
import org.usfirst.frc.team5012.robot.auton.commands.WaitCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CenterGearAuton extends CommandGroup {

	public CenterGearAuton() {
//
//		this.addSequential(new ClawCommand(false));
//		this.addSequential(new DriveStraightCommand(.2, -.0095, 7.0));
//		this.addSequential(new ClawCommand(true));
//		this.addSequential(new WaitCommand(1.0));
//		this.addSequential(new DriveStraightCommand(-.2, 0, 1.5));
//		this.addSequential(new DriveStraightCommand(.0, 0, 0));	
		
		

		
		
		
		this.addSequential(new ClawCommand(false));
		this.addSequential(new DriveStraightCommand(.2, 0, 5));
		this.addSequential(new DriveStraightCommand(0, .25, 0.5));

		this.addSequential(new DriveStraightCommand(.2, 0, 4));
		this.addSequential(new ClawCommand(true));
		this.addSequential(new WaitCommand(1.0));
		this.addSequential(new DriveStraightCommand(-.2, 0, 1.5));
		this.addSequential(new DriveStraightCommand(.0, 0, 0));	
		this.addSequential(new DriveStraightCommand(.0, -.25, 0.5));	
		//this.addSequential(new DriveStraightCommand(.6, 0, 1.5));	
		this.addSequential(new DriveStraightCommand(.0, 0, 0));	
	
	}
}