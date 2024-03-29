package org.usfirst.frc.team5012.robot.auton;



import org.usfirst.frc.team5012.robot.auton.commands.ClawCommand;
import org.usfirst.frc.team5012.robot.auton.commands.GyroTurnCommand;
import org.usfirst.frc.team5012.robot.auton.commands.VisionDriveCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SidePegAuton extends CommandGroup {

	public SidePegAuton(int dir) {

		double angle = 50.0;

		this.addSequential(new ClawCommand(false));
		this.addSequential(new GyroTurnCommand(.4, 0, 1.8));
		this.addSequential(new GyroTurnCommand(0, angle * dir, 1));
	
	    this.addSequential(new VisionDriveCommand(0, 3));
	    

		this.addSequential(new GyroTurnCommand(.25, 0,.75));

		this.addSequential(new ClawCommand(true));
		this.addSequential(new GyroTurnCommand(0, 0, 1));
		
		this.addSequential(new GyroTurnCommand(-.3, 0, 1.1));
		this.addSequential(new GyroTurnCommand(0, -angle * dir, 2));
		this.addSequential(new ClawCommand(false));

		

	
	
	}
}