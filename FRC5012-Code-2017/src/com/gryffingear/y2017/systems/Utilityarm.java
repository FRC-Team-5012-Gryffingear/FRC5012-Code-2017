package com.gryffingear.y2017.systems;

import com.ctre.CANTalon;
import com.gryffingear.y2017.config.Constants;
import com.gryffingear.y2017.config.Ports;

import edu.wpi.first.wpilibj.DigitalInput;

public class UtilityArm {

    private CANTalon intakeMotor = null;
    private CANTalon utilityArmMotor = null;

    private boolean hasZeroed = false;

    private DigitalInput armBumpSwitch = null;
    private DigitalInput armLimitSwitch = null;
    

    public UtilityArm(int im, int uam, int uae, int abs, int alm) {

        intakeMotor = configureTalon(new CANTalon(im), CANTalon.TalonControlMode.PercentVbus, false, 0);
        utilityArmMotor = configureTalon(new CANTalon(uam), CANTalon.TalonControlMode.PercentVbus, true, 0);
        
        armBumpSwitch = new DigitalInput(abs);
        armLimitSwitch = new DigitalInput(alm);

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
        intakeMotor.set(intakeV);
    }

    public void setPercentV(double v) {
        utilityArmMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        utilityArmMotor.set(v);
    }

    double setpoint = Constants.UtilityArm.UTILITY_ARM_STOW_POSITION;
    
    public void setPosition(double position) {
        this.setpoint = position;
    }
    
    public void run() {
        double kp = -1.5;
        double error = this.getPosition() - setpoint;

        System.out.println("setpoint: " + setpoint);
        utilityArmMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

        double out = kp * error;


        utilityArmMotor.set(out);
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

    public boolean getLimitSwitch() {
        return armLimitSwitch.get();
    }
    
    public boolean getBumpSwitch() {
        return !armBumpSwitch.get();
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