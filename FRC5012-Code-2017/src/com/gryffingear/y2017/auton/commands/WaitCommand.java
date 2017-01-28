package com.gryffingear.y2017.auton.commands;


import edu.wpi.first.wpilibj.command.Command;


public class WaitCommand extends Command {

  private double timeout = 0.0;

  public WaitCommand(double timeout) {

    this.timeout = timeout;
  }

  protected void initialize() {

    this.setTimeout(timeout);
  }

  protected void execute() {

  }

  protected void interrupted() {

  }

  protected void end() {

  }

  protected boolean isFinished() {

    return this.isTimedOut();
  }
}