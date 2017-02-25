package com.gryffingear.y2017.auton.commands;

import edu.wpi.first.wpilibj.command.Command;

import java.util.ArrayList;

import com.gryffingear.y2017.systems.SuperSystem;

public class TrapezoidalDriveStraightCommand extends Command {

	private double speed = 0.0;
	private double angle = 0.0;
	private double timeout = 0.0;
	
	public TrapezoidalDriveStraightCommand(double speed, double angle, double timeout) {
		
		this.speed = -speed;
		this.angle = 0.0;
		this.timeout = timeout;
		this.setTimeout(timeout);
		

		ArrayList<Double> trajectory = new ArrayList();
		double v = speed;
		//double speed = Math.abs(v);
		double dir = Math.signum(v);
		//double timeout = 10;
		double timeStep = .05;
		double maxA = 0.1;
		
		

	    double tempSpeed = (speed);

	    // Generate profile

	    double timeToV = tempSpeed / maxA;
	    double out = 0.0;
	    for (double t = 0; t < timeout; t += timeStep) {

	      if (timeout < 2.0 * timeToV) {
	        if (t <= timeout / 2) {
	          out += maxA * timeStep;
	        } else {
	          out += -maxA * timeStep;
	        }
	      } else {
	        if (t <= timeToV) {
	          out += maxA * timeStep;
	        } else if (t > timeToV && t < (timeout - timeToV)) {
	          out = tempSpeed;
	        } else {
	          out += -maxA * timeStep;
	        }

	      }
	      
	      trajectory.add(new Double(out) * dir);
	      
	    }
	    
		
	}
	
	protected void initialize() {
		SuperSystem.getInstance().drivetrain.resetGyro();
		
		
	}
	
	protected boolean isFinished() {
		return this.isTimedOut();
	}
	
	protected void execute() {
		
		double p = 0.02;
		double error = SuperSystem.getInstance().drivetrain.getYaw() - this.angle;
		SuperSystem.getInstance().drivetrain.tankDrive(speed + (p * error), speed - (p * error));
		
	}
	
	protected void end() {
		SuperSystem.getInstance().drivetrain.tankDrive(0.0, 0.0);
	}
	
	protected void interrupted() {
		SuperSystem.getInstance().drivetrain.tankDrive(0.0, 0.0);
	}
	
	
}
