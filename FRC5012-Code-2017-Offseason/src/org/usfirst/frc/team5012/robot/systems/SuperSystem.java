package org.usfirst.frc.team5012.robot.systems;

import org.usfirst.frc.team5012.robot.Ports;
import org.usfirst.frc.team5012.robot.util.Pixy;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class that represents the robot as a complete supersystem.
 * Includes a method to receive and interpret teleop inputs
 * @author jgermita
 *
 */
public class SuperSystem {

	// Null instances of all major subsystems
	private static SuperSystem instance = null;
	public Drivetrain drivetrain = null;

	public Intake intake = null;

	public Compressor compressor = null;
	public Climber climb = null;
	public Pixy pixycam = null;

	public Claw claw = null;

	/**
	 * Constructor.
	 */
	private SuperSystem() {

		drivetrain = new Drivetrain(Ports.Drivetrain.DRIVE_LEFT_A_MOTOR, Ports.Drivetrain.DRIVE_LEFT_B_MOTOR,
				Ports.Drivetrain.DRIVE_RIGHT_A_MOTOR, Ports.Drivetrain.DRIVE_RIGHT_B_MOTOR,
				Ports.Drivetrain.DRIVE_GYRO_PORT);

		intake = new Intake(Ports.UtilityArm.INTAKE_MOTOR, Ports.UtilityArm.ARM_BUMP_SWITCH);

		claw = new Claw(Ports.Claw.CLAW_PORT);

		climb = new Climber(Ports.Climber.CLIMBER_MOTOR_A, Ports.Climber.CLIMBER_MOTOR_B);

		pixycam = new Pixy(Ports.PixyCam.PIXY_PORT);

		compressor = new Compressor();
		compressor.start();

	}

	public void teleop(double thr, double tur, 
						boolean climberIn, 
						double intakeInput,
						boolean ClawInput) {
		
		// DRIVER:	/////////////////////////////
		double throttle = thr;
		double turning = tur;
		if(Math.abs(turning) < .20) {
			//	turning = 0;
		}
		
		// cube the turning input to make it feel better + eliminate deadband
		turning = turning * turning * turning;
		// Scale turning because turns are too fast
		turning *= 0.75;
		
		// "SmartTurn" logic - if driver is not commanding a turn, resist turning
		boolean smartTurn = false;
		
		if(smartTurn) {
			double kSt = .10;	// unit conversion for yaw-rate to turning units
			double kP = 1.0;	// tuning constant, higher is more sensitive.
			double input = drivetrain.getRawRate() * kSt;
			
			turning = kP * (turning - input);
			
		}
		

		drivetrain.tankDrive(throttle - turning, throttle + turning);

		double cOut = 0;

		if (climberIn) {
			cOut = 1;
		} else {
			cOut = 0;
		}

		climb.set(-cOut);
		
		//System.out.println("Pixycam Voltage: " + pixycam.getVoltage());

		
		// OPERATOR:	/////////////////////////////

		double iOut = 0;

		claw.set(ClawInput);

		if (intakeInput < -.20) {
			iOut = -1.0;
		} else if (intakeInput > .20) {
			iOut = .8;
		} else {
			iOut = 0;
		}

		intake.set(iOut);

		SmartDashboard.putBoolean("hasGear", intake.getBumpSwitch());
	}

	public static SuperSystem getInstance() {

		if (instance == null) {
			instance = new SuperSystem();
		}
		return instance;
	}

	
}