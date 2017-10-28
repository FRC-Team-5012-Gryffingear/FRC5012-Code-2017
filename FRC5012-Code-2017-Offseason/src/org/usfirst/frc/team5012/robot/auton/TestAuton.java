package org.usfirst.frc.team5012.robot.auton;



import org.usfirst.frc.team5012.robot.auton.commands.ClawCommand;
import org.usfirst.frc.team5012.robot.auton.commands.GyroTurnCommand;
import org.usfirst.frc.team5012.robot.auton.commands.VisionDriveCommand;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestAuton extends CommandGroup {

	public TestAuton() {
		

		this.addSequential(new ClawCommand(false));
		this.addSequential(new GyroTurnCommand(.25, 0, 1));

		this.addSequential(new GyroTurnCommand(0, 60, 1));
	    this.addSequential(new VisionDriveCommand(0, 6));

		this.addSequential(new GyroTurnCommand(.25, 0,2));

		this.addSequential(new ClawCommand(true));
		
		this.addSequential(new GyroTurnCommand(-.3, 0, 1));

		this.addSequential(new GyroTurnCommand(0, -60, 1));
	}
}