package com.gryffingear.y2017.systems;

import com.gryffingear.y2017.config.Constants;
import com.gryffingear.y2017.config.Ports;
import com.gryffingear.y2017.utilities.GryffinMath;
import com.gryffingear.y2017.utilities.LedOutput;

import edu.wpi.first.wpilibj.Compressor;

public class SuperSystem {

	private static SuperSystem instance = null;
	public Drivetrain drivetrain = null;
	public Shooter shoot = null;
	public UtilityArm utilityarm = null;
//	public GRIPVision vision = null;
	public Feeder feed = null;
	public Compressor compressor = null;
	public Climber climb = null;
//	public GryffinPIDController visionPid = null;
//	public GryffinPIDOutput visionOut = null;
	
	LedOutput ARM_LED_A;
	LedOutput ARM_LED_B;
	
	
	private SuperSystem() {

		drivetrain = new Drivetrain(Ports.Drivetrain.DRIVE_LEFT_A_MOTOR, 
							   Ports.Drivetrain.DRIVE_LEFT_B_MOTOR,
							   Ports.Drivetrain.DRIVE_LEFT_C_MOTOR, 
							   Ports.Drivetrain.DRIVE_RIGHT_A_MOTOR,
							   Ports.Drivetrain.DRIVE_RIGHT_B_MOTOR, 
							   Ports.Drivetrain.DRIVE_RIGHT_C_MOTOR,
							   Ports.Drivetrain.DRIVE_GYRO_PORT);

		shoot = new Shooter(Ports.Shooter.SHOOTER_MOTOR, 
							Ports.Shooter.PRESHOOTER_MOTOR);

		utilityarm = new UtilityArm(Ports.UtilityArm.INTAKE_MOTOR,
									Ports.UtilityArm.ARM_MOTOR,
									Ports.UtilityArm.ARM_ENCODER,
									Ports.UtilityArm.ARM_BUMP_SWITCH,
									Ports.UtilityArm.ARM_LIMIT_SWITCH);
//		
//		vision = GRIPVision.getInstance();
//		visionOut = new GryffinPIDOutput();
//		visionPid = new GryffinPIDController(Constants.SuperSystem.AUTO_AIM_KP, 0, 0, 
//											vision, visionOut);
//		
		
		feed = new Feeder(Ports.Feeder.AGITATOR_MOTOR_A,
						  Ports.Feeder.AGITATOR_MOTOR_B,
						  Ports.Feeder.FEEDER_MOTOR);
		
		climb = new Climber(Ports.Climber.CLIMBER_MOTOR_A,
							Ports.Climber.CLIMBER_MOTOR_B,
							Ports.Climber.CLIMBER_BUMP_PORT);
		
		ARM_LED_A = new LedOutput(Ports.UtilityArm.ARM_LED_A,
								  Ports.Pneumatics.PCM_CAN_ID);
		ARM_LED_B = new LedOutput(Ports.UtilityArm.ARM_LED_B,
								  Ports.Pneumatics.PCM_CAN_ID);
		
		compressor = new Compressor();
		compressor.start();
		
		
		
	}

	public void drive(double leftIn,
					  double rightIn,
					  boolean climberIn) {
		
/*
		double throttle = (leftin + rightin) / 2.0;
		double turning = (leftin - rightin) / 2.0;
*/
		double cOut = 0;
		
		if (climberIn) {
			cOut = 1;
		} else {
			cOut = 0;
		}
		
			
		climb.Climb(-cOut);
		drivetrain.tankDrive(leftIn, rightIn);

//		drivetrain.cheesyDrive(turning, throttle, quickturn);
	}

	double uaP = Constants.UtilityArm.UTILITY_ARM_SCORING_POSITION;

	public void operate(double intakeInput, //left joy up/down
						double utilityArmInput,
						boolean feedInput, // Left Trigger
						boolean feedOutput, // X
						boolean shooterInput,//Right Trigger
						double uArmPositionInput,
						boolean zeroArm,
						double nudgeInput) { //D-pad 

		double iOut = 0;
		double nOut = 0;
		
		double sOut = 0;
		double psOut = 0;
		
		double fOut = 0;
		double aOut = 0;
		
		//iOut = GryffinMath.thresholdOnOff(intakeInput, 0.20);
		
//	
//		if (utilityArmInput > .20) {
//			uaOut = -1;
//		} else if (utilityArmInput < -.20) {
//			uaOut = 1;
//		} else {
//			uaOut = 0;
//		}
		
		if (uArmPositionInput == 0) {
			uaP = Constants.UtilityArm.UTILITY_ARM_STOW_POSITION;
		} else if (uArmPositionInput == 90) {
			uaP = Constants.UtilityArm.UTILITY_ARM_SCORING_POSITION ;
		} else if (uArmPositionInput == 180) {
			uaP = Constants.UtilityArm.UTILITY_ARM_GROUND_POSITION;
		} else if (uArmPositionInput == 270) {
			uaP = Constants.UtilityArm.UTILITY_ARM_SHUTTLING_POSITION ;
		}
	
//		
//		if (feedInput) {
//			aOut = -.75;
//			fOut = -1;
//		} else if (feedOutput) {
//			aOut = -.75;
//			fOut = 1;
//		} else {
//			aOut = 0;
//			fOut = 0;
//		}
		
		
//		if (shooterInput) {
//			sOut = 1;
//			psOut = -.8;
//		} else {
//			sOut = 0;
//			psOut = 0;
//		}
//		
//		if (zeroArm) {
//			//SuperSystem.getInstance().utilityarm.zeroArm();
//		} else {
//			
//		}


		if(utilityarm.getBumpSwitch()){

			if(iOut > 0.0) {
				iOut = .1;
			}
			
		}else if (intakeInput > .20) {
			iOut = 1;
		} else if (intakeInput < -.20){
			iOut = -1;
		}else {

			ARM_LED_A.set(false);
			ARM_LED_B.set(false);
			
		}
		
		boolean ledOut = utilityarm.getBumpSwitch();
		
		ARM_LED_A.set(ledOut);
		ARM_LED_B.set(ledOut);

		
		utilityarm.runIntake(iOut);
		//utilityarm.setPercentV(uaOut);
		utilityarm.printPosition();
		
		utilityarm.setPosition(uaP + .365 * nudgeInput);
		
		shoot.runShooter(sOut, psOut);
		
		feed.runAgitator(aOut);
		feed.runFeeder(fOut);
		
		
		
	}

	public static SuperSystem getInstance() {

		if (instance == null) {
			instance = new SuperSystem();
		}
		return instance;
	}
}
