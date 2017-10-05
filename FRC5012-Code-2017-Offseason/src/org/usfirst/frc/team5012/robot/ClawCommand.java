package org.usfirst.frc.team5012.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;

public class ClawCommand extends Command {

  private double timeout = 0.0;
  private boolean state = false;

  public ClawCommand(boolean state) {
    this.timeout = timeout;
    this.state = state;
  }

  protected void initialize() {

  }

  protected void execute() {
	  SuperSystem.getInstance().claw.set(state);
  }

  protected void interrupted() {

  }

  protected void end() {

  }

  protected boolean isFinished() {

    return true;
  }
}