package com.gryffingear.y2017.systems;

import com.gryffingear.y2017.config.Ports;

public class SuperSystem {

	private static SuperSystem instance = null;
	public Drivetrain drive = null;
	public Shooter shoot = null;
	public Intake intake = null;

	private SuperSystem() {

		drive = new Drivetrain(Ports.Drivetrain.DRIVE_LEFT_A_MOTOR, 
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

	}

	public void drive(double leftin, double rightin) {

		double throttle = (leftin + rightin) / 2.0;
		double turning = (leftin - rightin) / 2.0;

		drive.tankDrive(throttle + turning, throttle - turning);

	}

	public void operate(double intakeInput, boolean intakePos, boolean hopperPos) {

		double iOut = 0;
		boolean ipOut = false;
		boolean hpOut = false;

		if (intakeInput > .20) {
			iOut = -1.0;
		} else if (intakeInput < -.20) {
			iOut = 1.0;
		} else {
			iOut = 0.0;
		}

		intake.runIntake(iOut);
		intake.setIntake(ipOut);
		intake.setHopper(hpOut);
	}

	public static SuperSystem getInstance() {

		if (instance == null) {
			instance = new SuperSystem();
		}
		return instance;
	}
}
