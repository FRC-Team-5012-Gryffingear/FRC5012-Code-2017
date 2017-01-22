package com.gryffingear.y2017.systems;

import com.ctre.CANTalon;
import com.gryffingear.y2017.config.Constants;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;

public class Shooter {

	private CANTalon shooterMotor = null;
	private AnalogInput shooterEnc = null;

	private CANTalon preshooterMotor = null;

	private CANTalon turretMotor = null;
	private AnalogInput turretEnc = null;
	private DigitalInput turretABump = null;
	private DigitalInput turretBBump = null;

	public Shooter(int sm, int se, int psm, int tm, int te, int tab, int tbb) {

		shooterMotor = configureTalon(new CANTalon(sm), false, false, false, false, Constants.Shooter.SHOOTER_RAMP_RATE);
		shooterEnc = new AnalogInput(se);

		preshooterMotor = configureTalon(new CANTalon(psm), false, false, false, false, Constants.Shooter.SHOOTER_RAMP_RATE);

		turretMotor = configureTalon(new CANTalon(tm), true, false, false, false, Constants.Shooter.TURRET_RAMP_RATE);
		turretEnc = new AnalogInput(te);
		turretABump = new DigitalInput(tab);
		turretBBump = new DigitalInput(tbb);

	}

	public void runShooter(double shooterv, double preshooterv) {

		shooterMotor.set(shooterv);
		preshooterMotor.set(preshooterv);
		

	}
	
	public void runTurret(double turretv) {
		
		turretMotor.set(turretv);
	}

	private CANTalon configureTalon(CANTalon in, boolean brakeState, boolean speedMode, boolean positionMode,
			boolean voltageMode, double rampRate) {

		if (speedMode = true) {
			in.changeControlMode(CANTalon.TalonControlMode.Speed);
		} else if (positionMode = true) {
			in.changeControlMode(CANTalon.TalonControlMode.Position);

		} else if (voltageMode = true) {
			in.changeControlMode(CANTalon.TalonControlMode.Voltage);
		} else {
			in.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		}

		in.enableBrakeMode(brakeState);
		in.setVoltageRampRate(rampRate);
		in.enableControl();
		in.clearStickyFaults();
		System.out.println("[CANTalon]" + in.getDescription() + " Initialized at device ID: " + in.getDeviceID());
		return in;
	}
}
