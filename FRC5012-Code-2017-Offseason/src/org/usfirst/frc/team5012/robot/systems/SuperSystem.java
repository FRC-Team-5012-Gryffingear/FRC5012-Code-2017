package org.usfirst.frc.team5012.robot.systems;
import org.usfirst.frc.team5012.robot.Constants;
import org.usfirst.frc.team5012.robot.Ports;
import org.usfirst.frc.team5012.robot.util.PulseTriggerBoolean;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SuperSystem {

	private static SuperSystem instance = null;
	public Drivetrain drivetrain = null;

	public Intake utilityarm = null;
	// public GRIPVision vision = null;
	
	public Compressor compressor = null;
	public Climber climb = null;
	public AnalogInput pixycam = null;
	
	public Claw claw = null;
	// public GryffinPIDController visionPid = null;
	// public GryffinPIDOutput visionOut = null;



	private SuperSystem() {

		drivetrain = new Drivetrain(Ports.Drivetrain.DRIVE_LEFT_A_MOTOR, 
									Ports.Drivetrain.DRIVE_LEFT_B_MOTOR,
									
									Ports.Drivetrain.DRIVE_RIGHT_A_MOTOR,
									Ports.Drivetrain.DRIVE_RIGHT_B_MOTOR, 
									Ports.Drivetrain.DRIVE_GYRO_PORT);
		
		utilityarm = new Intake(Ports.UtilityArm.INTAKE_MOTOR, 
									Ports.UtilityArm.ARM_BUMP_SWITCH);

		claw = new Claw(Ports.Claw.CLAW_PORT);	

		climb = new Climber(Ports.Climber.CLIMBER_MOTOR_A, 
				            Ports.Climber.CLIMBER_MOTOR_B);


		 pixycam = new AnalogInput(Ports.PixyCam.PixyCam);
		
		compressor = new Compressor();
		compressor.start();

	}

	public void drive(double leftIn, double rightIn, boolean climberIn, boolean autoAim) {

		
		
		double throttle = (leftIn + rightIn) / 2.0 ;
		double turning = (leftIn - rightIn) / 2.0 ;
	
		
		
		if(!autoAim){
			turning += 0;
		} else {
			throttle = 0.0;
			double kP = Constants.SuperSystem.AUTO_AIM_KP;
			turning = kP * ((Constants.SuperSystem.AUTO_AIM_TARGET) - pixycam.getVoltage());
		}
		/*
		 * double throttle = (leftin + rightin) / 2.0; double turning = (leftin
		 * - rightin) / 2.0;
		 */
		double cOut = 0;

		if (climberIn) {
			cOut = 1;
		} else {
			cOut = 0;
		}

		climb.set(-cOut);
		drivetrain.tankDrive(throttle + turning, throttle - turning);
		
		
		System.out.println("Pixycam Voltage: " + pixycam.getVoltage());

		// drivetrain.cheesyDrive(turning, throttle, quickturn);
	}

	double uaP = Constants.UtilityArm.UTILITY_ARM_SCORING_POSITION;

	PulseTriggerBoolean gearsense = new PulseTriggerBoolean();
	public void operate(double intakeInput, // left joy up/down
			double utilityArmInput,  // Left Trigger
			boolean shooterInput, // Right Trigger
			double uArmPositionInput, double nudgeInput, boolean ClawInput) { // D-pad

		double iOut = 0;
		double nOut = 0;

		double sOut = 0;
		double psOut = 0;

		double fOut = 0;
		double aOut = 0;
		
		claw.set(ClawInput);

		// iOut = GryffinMath.thresholdOnOff(intakeInput, 0.20);

		//
		// if (utilityArmInput > .20) {
		// uaOut = -1;
		// } else if (utilityArmInput < -.20) {
		// uaOut = 1;
		// } else {
		// uaOut = 0;
		// }

		gearsense.set(utilityarm.getBumpSwitch());
		if (uArmPositionInput == 0) {
			uaP = Constants.UtilityArm.UTILITY_ARM_STOW_POSITION;
		} else if (uArmPositionInput == 90 || gearsense.get()) {
			uaP = Constants.UtilityArm.UTILITY_ARM_SCORING_POSITION;
		} else if (uArmPositionInput == 180) {
			uaP = Constants.UtilityArm.UTILITY_ARM_GROUND_POSITION;
		} else if (uArmPositionInput == 270) {
			uaP = Constants.UtilityArm.UTILITY_ARM_SHUTTLING_POSITION;
		}

		//
		// if (feedInput) {
		// aOut = -.75;
		// fOut = -1;
		// } else if (feedOutput) {
		// aOut = -.75;
		// fOut = 1;
		// } else {
		// aOut = 0;
		// fOut = 0;
		// }

		// if (shooterInput) {
		// sOut = 1;
		// psOut = -.8;
		// } else {
		// sOut = 0;
		// psOut = 0;
		// }
		//
		// if (zeroArm) {
		// //SuperSystem.getInstance().utilityarm.zeroArm();
		// } else {
		//
		// }

		if (intakeInput < -.20) {
			iOut = -1.0;
		} else if (intakeInput > .20) {
			iOut = .8;
		} else {
			iOut = 0;

		}
		
		System.out.println(utilityarm.getBumpSwitch());

		


		utilityarm.set(iOut);

		SmartDashboard.putBoolean("hasGear", utilityarm.getBumpSwitch());

	}

	public static SuperSystem getInstance() {

		if (instance == null) {
			instance = new SuperSystem();
		}
		return instance;
	}
	

}