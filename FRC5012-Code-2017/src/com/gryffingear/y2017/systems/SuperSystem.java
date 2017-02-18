package com.gryffingear.y2017.systems;

import com.gryffingear.y2017.config.Constants;
import com.gryffingear.y2017.config.Ports;
import com.gryffingear.y2017.utilities.GryffinMath;
import com.gryffingear.y2017.utilities.NegativeInertiaAccumulator;

public class SuperSystem {

	private static SuperSystem instance = null;
	public Drivetrain drivetrain = null;
	public Shooter shoot = null;
	public Intake intake = null;
	public GRIPVision vision = null;
	public Feeder feed = null;
	
	
	private SuperSystem() {

		drivetrain = new Drivetrain(Ports.Drivetrain.DRIVE_LEFT_A_MOTOR, 
							   Ports.Drivetrain.DRIVE_LEFT_B_MOTOR,
							   Ports.Drivetrain.DRIVE_LEFT_C_MOTOR, 
							   Ports.Drivetrain.DRIVE_RIGHT_A_MOTOR,
							   Ports.Drivetrain.DRIVE_RIGHT_B_MOTOR, 
							   Ports.Drivetrain.DRIVE_RIGHT_C_MOTOR,
							   Ports.Drivetrain.DRIVE_GYRO_PORT);

		shoot = new Shooter(Ports.Shooter.SHOOTER_MOTOR, 
							Ports.Shooter.SHOOTER_ENC_PORT, 
							Ports.Shooter.PRESHOOTER_MOTOR,
							Ports.Shooter.TURRET_MOTOR, 
							Ports.Shooter.TURRET_ENCODER,
							Ports.Shooter.TURRET_BUMP_A_PORT,
							Ports.Shooter.TURRET_BUMP_B_PORT);

		intake = new Intake(Ports.Intake.INTAKE_MOTOR, 
							Ports.Intake.INTAKE_SOLENOID,
							Ports.Intake.HOPPER_SOLENOID);
		
		vision = GRIPVision.getInstance();
		
		feed = new Feeder(Ports.Feeder.AGITATOR_MOTOR,
						   Ports.Feeder.FEEDER_MOTOR);
		
		
		
	}

	public void drive(double leftin,
					  double rightin) {
		

		double throttle = (leftin + rightin) / 2.0;
		double turning = (leftin - rightin) / 2.0;

		drivetrain.tankDrive(throttle + turning, throttle - turning);

	}

	public void operate(double intakeInput, 
						boolean intakePos, 
						boolean hopperPos, 
						double turretInput, 
						boolean autoAim,
						boolean feederPositiveInput, 
						boolean feederNegativeInput, 
						boolean agitatorPositiveInput,
						boolean agitatorNegativeInput,
						boolean shooterInput) {
		
		vision.update();

		double iOut = 0;
		boolean ipOut = false;
		boolean hpOut = false;
		
		double turrOut = 0;
		double sOut = 0;
		double psOut = 0;
		
		double fOut = 0;
		double aOut = 0;
		
		iOut = GryffinMath.thresholdOnOff(intakeInput, 0.20);
		
		if (turretInput > .20) {
			turrOut = .25;
		} else if (turretInput < -.20) {
			turrOut = -.25;
		} else if(autoAim) {
			double kP = Constants.SuperSystem.AUTO_AIM_KP;
			turrOut = kP * vision.getX();
		}
		else {
			turrOut = 0;
		}
		
		if (feederPositiveInput) {
			fOut = 1;
		} else if (feederNegativeInput) {
			fOut = -1;
		} else {
			fOut = 0;
		}
		
		if (agitatorPositiveInput) { 
			aOut = 1;
		} else if (agitatorNegativeInput) {
			aOut = -1;
		} else {
			aOut = 0;
		}
		
		if (shooterInput) {
			sOut = 1;
			psOut = .8 * sOut;
		} else if (shooterInput) {
			sOut = -1;
			psOut = .8 * sOut;
		} else {
			sOut = 0;
			psOut = 0;
		}

		intake.runIntake(iOut);
		intake.setIntake(ipOut);
		intake.setHopper(hpOut);
		
		shoot.runTurret(turrOut);
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
