package org.usfirst.frc.team5012.robot;



import org.usfirst.frc.team5012.robot.auton.CenterGearAuton;
import org.usfirst.frc.team5012.robot.auton.DriveStraightAuton;
import org.usfirst.frc.team5012.robot.systems.SuperSystem;
import org.usfirst.frc.team5012.robot.util.Pixy;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	SuperSystem bot = SuperSystem.getInstance();

	Joystick driver = new Joystick(Ports.Controls.DRIVER_PORT);
	Joystick operator = new Joystick(Ports.Controls.OPERATOR_PORT);
	
	Pixy pixy = new Pixy(2);


	AnalogInput pot = new AnalogInput(4);
	
	BuiltInAccelerometer accel = new BuiltInAccelerometer();

	private CommandGroup currAuton = null;


	@Override
	public void robotInit() {

		CameraServer.getInstance().startAutomaticCapture("AnalogInput", 0).setFPS(15);       // previously 10 FPS //cam0
		

		// chooser.addObject("My Auto", new MyAutoCommand());
		//SmartDashboard.putData("Auto mode", new TestAuton());

	}


	@Override
	public void disabledInit() {

		cancelAuton();

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().disable();

		System.out.println("Pixy: " + pixy.get());
		
		
		if(driver.getRawAxis(2) > .2) {
			System.out.println("Auton: DriveStraightAuton" );
			currAuton = new DriveStraightAuton();
		} else if (driver.getRawAxis(2) < -.2) {
			System.out.println("Auton: DriveStraightAuton" );
			currAuton = new DriveStraightAuton();
		} else if (driver.getRawAxis(2) < .2 && driver.getRawAxis(2) > -.2 ){
			System.out.println("Auton: CenterGearAuton" );
			currAuton = new CenterGearAuton();
		} else {
			currAuton = new DriveStraightAuton();
		}

	}

	@Override
	public void autonomousInit() {

		cancelAuton();
//		
//		
//		if(driver.getRawAxis(2) > .2) {
//			System.out.println("Auton: DriveStraightAuton" );
//			currAuton = new DriveStraightAuton();
//		} else if (driver.getRawAxis(2) < -.2) {
//			System.out.println("Auton: DriveStraightAuton" );
//			currAuton = new DriveStraightAuton();
//		} else if (driver.getRawAxis(2) < .2 && driver.getRawAxis(2) > -.2 ){
//			System.out.println("Auton: CenterGearAuton" );
//			currAuton = new CenterGearAuton();
//		} 
		currAuton = new CenterGearAuton();
		Scheduler.getInstance().add(currAuton);
		Scheduler.getInstance().enable();
	}


	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {

		cancelAuton();

	}
	
	long rumstart = 0;


	@Override
	public void teleopPeriodic() {
		
		if(bot.intake.getBumpSwitch()) {
			rumstart = System.currentTimeMillis();
		} 
		
		if(System.currentTimeMillis() - rumstart < 1000) {
			driver.setRumble(RumbleType.kRightRumble, 1.0);
			driver.setRumble(RumbleType.kLeftRumble, 1.0);
		} else {
			driver.setRumble(RumbleType.kRightRumble, .0);
			driver.setRumble(RumbleType.kLeftRumble, .0);
		}

		boolean turbo = driver.getRawButton(8);
		double throttle = driver.getRawAxis(3) - driver.getRawAxis(2);
		double turning = driver.getRawAxis(0);
		
		bot.teleop(-throttle * (turbo ? 1.0 : 1.5), 
				  turning * (turbo ? 1.0 : 1.5), 
				  driver.getRawButton(3),
				  operator.getRawAxis(3),
				  operator.getRawButton(3));

	}

	@Override
	public void testInit() {

		cancelAuton();

	}


	@Override
	public void testPeriodic() {
	}

	public void cancelAuton() {
		if (currAuton != null) {
			System.out.println("[STATUS] Auton was running at this time. Cancelling...");
			currAuton.cancel();
			currAuton = null;
		}
	}
}
