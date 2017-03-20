package com.gryffingear.y2017.systems;

import com.ctre.CANTalon;
import com.gryffingear.y2017.config.Constants;
import com.gryffingear.y2017.utilities.GryffinMath;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;

import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.StatusFrameRate;

public class Shooter {

	private CANTalon shooterMotor = null;

	private CANTalon preshooterMotor = null;


	public Shooter(int sm, int psm) {

		shooterMotor = configureTalon(new CANTalon(sm), CANTalon.TalonControlMode.PercentVbus, false,
				Constants.Shooter.SHOOTER_RAMP_RATE);

		preshooterMotor = configureTalon(new CANTalon(psm), CANTalon.TalonControlMode.PercentVbus, false,
				Constants.Shooter.SHOOTER_RAMP_RATE);

	}

	public void runShooter(double shooterv, double preshooterv) {

		shooterMotor.set(shooterv);
		preshooterMotor.set(preshooterv);

	}

	private CANTalon configureTalon(CANTalon in, CANTalon.TalonControlMode mode, boolean brakeState, double rampRate) {
		in.changeControlMode(mode);
		in.setVoltageRampRate(rampRate);
		in.enableBrakeMode(brakeState);
		in.enableControl();
		in.clearStickyFaults();

		System.out.println("[CANTalon]" + in.getDescription() + " Initialized at device ID: " + in.getDeviceID());
		return in;
	}
	
	
}
