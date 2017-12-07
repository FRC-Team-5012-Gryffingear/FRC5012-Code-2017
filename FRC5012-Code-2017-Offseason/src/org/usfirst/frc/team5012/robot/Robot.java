package org.usfirst.frc.team5012.robot;



import org.usfirst.frc.team5012.robot.auton.CenterGearAuton;
import org.usfirst.frc.team5012.robot.auton.DriveStraightAuton;
import org.usfirst.frc.team5012.robot.auton.SidePegAuton;
import org.usfirst.frc.team5012.robot.auton.commands.WaitCommand;
import org.usfirst.frc.team5012.robot.systems.SuperSystem;

import edu.wpi.cscore.UsbCamera;
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
	


	AnalogInput modePot = new AnalogInput(0);
	AnalogInput timePot = new AnalogInput(1);
	
	
	BuiltInAccelerometer accel = new BuiltInAccelerometer();

	private CommandGroup currAuton = null;
	UsbCamera cam;

	@Override
	public void robotInit() {
		cam = CameraServer.getInstance().startAutomaticCapture("cam0", 0);
		
		cam.setFPS(15);       // previously 10 FPS //cam0
		cam.setExposureManual(15);


		// chooser.addObject("My Auto", new MyAutoCommand());
		//SmartDashboard.putData("Auto mode", new TestAuton());

	}


	@Override
	public void disabledInit() {

		cancelAuton();

		cam.setExposureManual(15);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().disable();

		SmartDashboard.putNumber("AutoMode", modePot.getAverageVoltage());
		SmartDashboard.putNumber("AutoDelay", timePot.getAverageVoltage());
	}

	@Override
	public void autonomousInit() {

		cam.setExposureManual(15);
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
		;
		
		
		if(modePot.getAverageVoltage() > 0 && modePot.getAverageVoltage() < 1.5) {
			
			currAuton = new SidePegAuton(1);
			
		} else if (modePot.getAverageVoltage() > 1.5 && modePot.getAverageVoltage() < 3.5) {
			
			currAuton = new CenterGearAuton();
			
		} else if (modePot.getAverageVoltage() > 3.5 && modePot.getAverageVoltage() < 4.5) {
			
			currAuton = new SidePegAuton(-1);
			
		} else if (modePot.getAverageVoltage() > 4.5 && modePot.getAverageVoltage() < 5.0) {
			
			currAuton = new DriveStraightAuton();
			
		}
		
		double delay = Math.max(0, (timePot.getAverageVoltage()-1.0));
		Scheduler.getInstance().add(new WaitCommand(delay));
		
		Scheduler.getInstance().add(currAuton);
		Scheduler.getInstance().enable();
	}


	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();

		SuperSystem.getInstance().pixycam.set(true);
		SuperSystem.getInstance().vision.update();
	}

	@Override
	public void teleopInit() {

		
		cancelAuton();
		cam.setExposureManual(40);
	}
	
	long rumstart = 0;


	@Override
	public void teleopPeriodic() {
		//SuperSystem.getInstance().pixycam.set(true);
		
		boolean lightOut = false;
		if(bot.intake.getBumpSwitch()) {
			rumstart = System.currentTimeMillis();
		} 
		
		if(System.currentTimeMillis() - rumstart < 1000) {
			driver.setRumble(RumbleType.kRightRumble, 1.0);
			driver.setRumble(RumbleType.kLeftRumble, 1.0);
			//SuperSystem.getInstance().pixycam.set(true);
			lightOut = true;
		} else {
			//SuperSystem.getInstance().pixycam.set(false);
			driver.setRumble(RumbleType.kRightRumble, .0);
			driver.setRumble(RumbleType.kLeftRumble, .0);
		}
		
		SuperSystem.getInstance().pixycam.set(lightOut || operator.getRawButton(2));

		boolean turbo = driver.getRawButton(6);
		double throttle = driver.getRawAxis(3) - driver.getRawAxis(2);
		double turning = driver.getRawAxis(0);
		
		bot.teleop(-throttle, 
				  turning * (turbo ? 0.7 : 1.0), 
				  driver.getRawButton(3),
				  operator.getRawAxis(3),
				  operator.getRawButton(3),
				  operator.getRawButton(9), !driver.getRawButton(4));

	}

	@Override
	public void testInit() {

		cancelAuton();

	}


	@Override
	public void testPeriodic() {
		SuperSystem.getInstance().pixycam.set(true);
	}

	public void cancelAuton() {
		if (currAuton != null) {
			System.out.println("[STATUS] Auton was running at this time. Cancelling...");
			currAuton.cancel();
			currAuton = null;
		}
	}
}
