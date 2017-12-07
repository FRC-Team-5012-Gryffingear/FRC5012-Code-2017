package org.usfirst.frc.team5012.robot.auton;



import org.usfirst.frc.team5012.robot.auton.commands.ClawCommand;
import org.usfirst.frc.team5012.robot.auton.commands.GyroTurnCommand;
import org.usfirst.frc.team5012.robot.auton.commands.VisionDriveCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightPegAuton extends CommandGroup {

	public RightPegAuton() {


		this.addSequential(new ClawCommand(false));
		this.addSequential(new GyroTurnCommand(.4, 0, .9));
		this.addSequential(new GyroTurnCommand(0, 70, 1));
	
	    this.addSequential(new VisionDriveCommand(0, 3));
	    

		this.addSequential(new GyroTurnCommand(.25, 0,.75));

		this.addSequential(new ClawCommand(true));
		this.addSequential(new GyroTurnCommand(0, 0, 1));
		
		this.addSequential(new GyroTurnCommand(-.3, 0, 1.1));
		this.addSequential(new GyroTurnCommand(0, -70, 2));
		this.addSequential(new ClawCommand(false));

		

	
	
	}
}