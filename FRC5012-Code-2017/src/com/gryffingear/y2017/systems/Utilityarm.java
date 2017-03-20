package com.gryffingear.y2017.systems;

import com.ctre.CANTalon;
import com.gryffingear.y2017.utilities.GryffinMath;

import edu.wpi.first.wpilibj.AnalogInput;

public class Utilityarm {
	
	private CANTalon intakeMotor = null;
	private CANTalon utilityArmMotor = null;
	private AnalogInput intakeEnc = null;

	public Utilityarm(int im, int uam, int uae) {
		
		intakeMotor = configureTalon(new CANTalon(im), CANTalon.TalonControlMode.PercentVbus, false, 0);
		utilityArmMotor = configureTalon(new CANTalon(uam), CANTalon.TalonControlMode.PercentVbus, false, 0);
		
		int absolutePosition = utilityArmMotor.getPulseWidthPosition();
		utilityArmMotor.setEncPosition(absolutePosition);
		utilityArmMotor.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
		utilityArmMotor.reverseSensor(true);
		utilityArmMotor.configNominalOutputVoltage(0.0, 5.0);
		utilityArmMotor.configPeakOutputVoltage(5.0, -5.0);
		utilityArmMotor.setAllowableClosedLoopErr(0);
		utilityArmMotor.setProfile(0);
		utilityArmMotor.setF(0.0);
		utilityArmMotor.setP(0.1);
		utilityArmMotor.setI(0.0);
		utilityArmMotor.setD(0.0);
	}
	
	public void runIntake(double intakeV) {
		intakeMotor.set(-intakeV);
	}
	
	double pos = 0;
	public void setIntake(double utilityArmV) {
		pos = utilityArmMotor.getEncPosition();
		utilityArmMotor.set(utilityArmV);
	}
	
	public void setPosition(double position) {
		System.out.println("setpoint: " + position);
		utilityArmMotor.changeControlMode(CANTalon.TalonControlMode.Position);
		utilityArmMotor.set(position);
	}
	
	public void zeroArm() {
		
		utilityArmMotor.setEncPosition(0);
	}
	
	public void printPosition() {
		System.out.println("Arm pos: " + utilityArmMotor.getEncPosition());
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

