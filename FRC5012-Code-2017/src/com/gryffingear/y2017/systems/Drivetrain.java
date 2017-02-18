package com.gryffingear.y2017.systems;

import com.gryffingear.y2017.config.Constants;
import com.ctre.PigeonImu;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.AnalogGyro;

public class Drivetrain {

	private CANTalon lefta = null;
	private CANTalon leftb = null;
	private CANTalon leftc = null;

	private CANTalon righta = null;
	private CANTalon rightb = null;
	private CANTalon rightc = null;

	private PigeonImu gyro = null;

	public Drivetrain(int la, int lb, int lc, int ra, int rb, int rc, int gp) {

		lefta = configureTalon(new CANTalon(la), CANTalon.TalonControlMode.PercentVbus, false, Constants.Drivetrain.DRIVETRAIN_RAMP_RATE);
		leftb = configureTalon(new CANTalon(lb), CANTalon.TalonControlMode.PercentVbus, false, Constants.Drivetrain.DRIVETRAIN_RAMP_RATE);
		leftc = configureTalon(new CANTalon(lc), CANTalon.TalonControlMode.PercentVbus, false, Constants.Drivetrain.DRIVETRAIN_RAMP_RATE);

		righta = configureTalon(new CANTalon(ra), CANTalon.TalonControlMode.PercentVbus, false, Constants.Drivetrain.DRIVETRAIN_RAMP_RATE);
		rightb = configureTalon(new CANTalon(rb), CANTalon.TalonControlMode.PercentVbus, false, Constants.Drivetrain.DRIVETRAIN_RAMP_RATE);
		rightc = configureTalon(new CANTalon(rc), CANTalon.TalonControlMode.PercentVbus, false, Constants.Drivetrain.DRIVETRAIN_RAMP_RATE);

		gyro = new PigeonImu(gp);
	}

	public void tankDrive(double leftv, double rightv) {

		lefta.set(-leftv);
		leftb.set(-leftv);
		leftc.set(-leftv);

		righta.set(rightv);
		rightb.set(rightv);
		rightc.set(rightv);

	}

	public void tankDrive(double[] input) {

		tankDrive(input[0], input[1]);
	}

	public double getYaw() {
		double[] ypr = new double[3];
		return gyro.GetYawPitchRoll(ypr);
	}

	public void resetGyro() {
		gyro.SetYaw(0);
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
