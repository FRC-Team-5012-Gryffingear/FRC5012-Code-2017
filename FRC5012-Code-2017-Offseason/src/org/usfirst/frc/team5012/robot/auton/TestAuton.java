package org.usfirst.frc.team5012.robot.auton;



import org.usfirst.frc.team5012.robot.auton.commands.GyroTurnCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestAuton extends CommandGroup {

	public TestAuton() {
		

	    this.addSequential(new GyroTurnCommand(0.0, 90, 5.0));

	    this.addSequential(new GyroTurnCommand(0.0, 90, 5.0));

	    this.addSequential(new GyroTurnCommand(0.0, 90, 5.0));

	    this.addSequential(new GyroTurnCommand(0.0, 90, 5.0));
	    
	    
	}
}