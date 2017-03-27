package com.gryffingear.y2017.systems;

import com.ctre.CANTalon;
import com.gryffingear.y2017.utilities.GryffinMath;

import edu.wpi.first.wpilibj.AnalogInput;

public class UtilityArm {
	
	private CANTalon intakeMotor = null;
	private CANTalon utilityArmMotor = null;
	
	private boolean hasZeroed = false;
	

	public UtilityArm(int im, int uam, int uae) {
		
		intakeMotor = configureTalon(new CANTalon(im), CANTalon.TalonControlMode.PercentVbus, false, 0);
		utilityArmMotor = configureTalon(new CANTalon(uam), CANTalon.TalonControlMode.PercentVbus, false, 0);
		
		int absolutePosition = utilityArmMotor.getPulseWidthPosition();
		utilityArmMotor.setEncPosition(absolutePosition);
		utilityArmMotor.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
		utilityArmMotor.reverseSensor(false);
		utilityArmMotor.setAllowableClosedLoopErr(0);
		utilityArmMotor.setProfile(0);
		utilityArmMotor.setF(0.0);
		utilityArmMotor.setP(0.004);
		utilityArmMotor.setI(0.0);
		utilityArmMotor.setD(0.0);
	}
	
	public void runIntake(double intakeV) {
		intakeMotor.set(-intakeV);
	}
	
	public void setPercentV(double v) {
		utilityArmMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		utilityArmMotor.set(v);
	}
	
	
	
	public void setPosition(double position) {
		double kp = -1.5;
		double error = this.getPosition() - position;
		
		System.out.println("setpoint: " + position);
		utilityArmMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		utilityArmMotor.set(kp * error);
	}
	
	public void zeroArm() {
		hasZeroed = true;
		utilityArmMotor.setEncPosition(0);
	}
	
	public boolean getZeroed() {
		return hasZeroed;
	}
	
	public void printPosition() {
		System.out.println("Arm pos: " + this.getPosition());
	}
	
	
	public double getPosition() {
		return utilityArmMotor.getPosition();
	}
	
	public double getSetpoint() {
		return utilityArmMotor.getSetpoint();
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

