package com.gryffingear.y2017.auton.commands;

import edu.wpi.first.wpilibj.command.Command;

import java.util.ArrayList;

import com.gryffingear.y2017.systems.SuperSystem;

public class TrapezoidalDriveStraightCommand extends Command {

	private double speed = 0.0;
	private double angle = 0.0;
	private double timeout = 0.0;

	double timeStep = .05;
	double maxA = 0.2;

	ArrayList<Double> trajectory = new ArrayList();
	
	long startTime = 0;
	
	public TrapezoidalDriveStraightCommand(double speed, double angle, double timeout) {
		
		this.speed = -speed;
		this.angle = 0.0;
		this.timeout = timeout;
		this.setTimeout(timeout);
		

		double v = speed;
		//double speed = Math.abs(v);
		
		//double timeout = 10;
		
		

	    double tempSpeed = Math.abs(v);

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
	      
	      trajectory.add(new Double(out * Math.signum(this.speed)));
	      
	    }
	    
		
	}
	
	protected void initialize() {
		SuperSystem.getInstance().drivetrain.resetGyro();
		

	    startTime = System.currentTimeMillis();
	}
	
	protected boolean isFinished() {
		return this.isTimedOut();
	}
	
	protected void execute() {
		
		double elapsedTime = ((double) (System.currentTimeMillis() - startTime)) / 1000.0;

	    int frame = (int) (elapsedTime / timeStep);

	    // read the current frame's output data

	    double out = 0.0;
	    if (frame < trajectory.size()) {
	      out = trajectory.get(frame).doubleValue() * -Math.signum(this.speed);
	    } else {
	      out = 0.0;
	    }

		
		double p = -0.02;
		double error = SuperSystem.getInstance().drivetrain.getYaw() - this.angle;
		SuperSystem.getInstance().drivetrain.tankDrive(out + (p * error), out - (p * error));
		
	}
	
	protected void end() {
		SuperSystem.getInstance().drivetrain.tankDrive(0.0, 0.0);
	}
	
	protected void interrupted() {
		SuperSystem.getInstance().drivetrain.tankDrive(0.0, 0.0);
	}
	
	
}
