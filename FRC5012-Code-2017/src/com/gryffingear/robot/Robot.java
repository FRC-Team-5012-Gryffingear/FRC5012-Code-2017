
package com.gryffingear.robot;

import com.gryffingear.autonomous.TestAuton;
import com.gryffingear.y2017.config.Ports;
import com.gryffingear.y2017.systems.SuperSystem;
import com.gryffingear.y2017.systems.Drivetrain;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.AnalogInput;

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

	Joystick driverL = new Joystick(Ports.Controls.DRIVER_LEFT_PORT);
	Joystick driverR = new Joystick(Ports.Controls.DRIVER_RIGHT_PORT);
	Joystick gamepad = new Joystick(Ports.Controls.OPERATOR_PORT);

	AnalogInput pot = new AnalogInput(1);

	private CommandGroup currAuton = null;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {

		CameraServer.getInstance().startAutomaticCapture("cam0", 0);

		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", new TestAuton());

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

		if (currAuton != null) {
			System.out.println("[STATUS] Auton was running at this time. Cancelling...");
			currAuton.cancel();
			currAuton = null;
		}
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().disable();

		currAuton = new TestAuton();
		SmartDashboard.putString("Currently Selected Auton", currAuton.toString());

		System.out.println("Potentiometer Voltlage: " + pot.getVoltage());

		System.out.println("Gyro: " + bot.drivetrain.getYaw());

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		// autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		currAuton = new TestAuton();
		Scheduler.getInstance().add(currAuton);
		Scheduler.getInstance().enable();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * 
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		System.out.println("gyro: " + bot.drivetrain.getYaw());

		Scheduler.getInstance().run();

	//	bot.drive((driverL.getRawAxis(1)+driverR.getRawAxis(1))/2, 
	//	(driverL.getRawAxis(1)-driverR.getRawAxis(1))/2, false,
		bot.drive(driverL.getRawAxis(1), 
				driverR.getRawAxis(0), driverR.getRawButton(5),
				  driverL.getRawButton(2));

		bot.operate(gamepad.getRawAxis(1), 
					gamepad.getRawAxis(1) > .2,
					gamepad.getRawButton(2),
					gamepad.getRawAxis(3),
					gamepad.getRawButton(9),
					gamepad.getRawButton(7),
					gamepad.getRawButton(1),
					gamepad.getRawButton(8));
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
		
		bot.shoot.testTurret();
		bot.shoot.printPosition();
	}
}
