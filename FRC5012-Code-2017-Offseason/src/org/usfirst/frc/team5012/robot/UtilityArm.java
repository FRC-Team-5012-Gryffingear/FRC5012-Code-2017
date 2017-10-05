package org.usfirst.frc.team5012.robot;

import com.ctre.CANTalon;

public class UtilityArm {

    private CANTalon intakeMotor = null;
    // private CANTalon utilityArmMotor = null;

    private boolean hasZeroed = false;

    private DigitalInput armBumpSwitch = null;
    private DigitalInput armLimitSwitch = null;
    

    public UtilityArm(int im,  int abs, int alm) {

        intakeMotor = configureTalon(new CANTalon(im), CANTalon.TalonControlMode.PercentVbus, false, 0);
     
        armBumpSwitch = new DigitalInput(abs);
        armLimitSwitch = new DigitalInput(alm);

    }

    public void runIntake(double intakeV) {
        intakeMotor.set(intakeV);
    }



    double setpoint = Constants.UtilityArm.UTILITY_ARM_STOW_POSITION;
    
    public void setPosition(double position) {
        this.setpoint = position;
    }
    
    public void run() {
        double kp = -1.5;
        double error = this.getPosition() - setpoint;

        System.out.println("setpoint: " + setpoint);
     //   utilityArmMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

        double out = kp * error;


      //  utilityArmMotor.set(out);
    }

    public void zeroArm() {
        hasZeroed = true;
      //  utilityArmMotor.setEncPosition(0);
    }

    public boolean getZeroed() {
        return hasZeroed;
    }

    public void printPosition() {
        System.out.println("Arm pos: " + this.getPosition());
    }

    public double getPosition() {
      //  return utilityArmMotor.getPosition();
    	return 0.0;
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