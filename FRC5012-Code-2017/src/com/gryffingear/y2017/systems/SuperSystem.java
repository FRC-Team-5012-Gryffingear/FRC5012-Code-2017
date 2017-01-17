package com.gryffingear.y2017.systems;

import com.gryffingear.y2017.config.Ports;

public class SuperSystem {

	private static SuperSystem instance = null;
	public Drivetrain drive = null;
	
	private SuperSystem () {
		
		drive = new Drivetrain(Ports.Drivetrain.DRIVE_LEFT_A_MOTOR,
							   Ports.Drivetrain.DRIVE_LEFT_B_MOTOR,
							   Ports.Drivetrain.DRIVE_LEFT_C_MOTOR,
							   Ports.Drivetrain.DRIVE_RIGHT_A_MOTOR,
							   Ports.Drivetrain.DRIVE_RIGHT_B_MOTOR,
							   Ports.Drivetrain.DRIVE_RIGHT_C_MOTOR);
	}
	
	public static SuperSystem getInstance() {

		if (instance == null) {
			instance = new SuperSystem();
		}
		return instance;
	}
	
	public void drive (double leftin,
					   double rightin) {
		
		double throttle = (leftin + rightin) / 2.0;
		double turning = (leftin - rightin) / 2.0;
		
		drive.tankDrive(throttle + turning, throttle - turning);
		
	}
}
