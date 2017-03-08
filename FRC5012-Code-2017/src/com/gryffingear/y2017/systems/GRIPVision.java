package com.gryffingear.y2017.systems;

import com.gryffingear.y2017.utilities.GryffinMath;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class GRIPVision implements PIDSource{
	
	private static GRIPVision instance = null;

	NetworkTable table;

	double x = 0;
	double y = 0;
	double area = 0;

	private GRIPVision() {

		table = NetworkTable.getTable("GRIP/myContoursReport");

	}
	
	public static GRIPVision getInstance()  {
		if(instance == null) {
			instance = new GRIPVision();
		}
		
		return instance;
	}

	public double getX() {

		return x;

	}

	public double getY() {

		return y;
	}

	public double getArea() {

		return area;

	}

	public void update() {

//		
//		System.out.println("X: " + (table.getNumberArray("centerX", defaultValue)[0] - 320));
//		System.out.println("Y: " + (table.getNumberArray("centerY", defaultValue)[0] - 240));
//		System.out.println("Area: " + table.getNumberArray("area", defaultValue)[0]);
//		System.out.println();

		double[] defaultValue = {0};	// initialize an array with size 1, element is 0
		double[] cx = table.getNumberArray("centerX", defaultValue);
		double[] cy = table.getNumberArray("centerY", defaultValue);
		double[] ar = table.getNumberArray("area", defaultValue);

		if(cx != null) {	//First check if null
			if (cx.length > 0) { // then check if length is not zero
	
				x = (cx[0] - 320);
				y = (cy[0] - 240);
				area = ar[0];
			}
		}
	}
	
	
	/**
	 * Converts x position to a yaw angle 
	 * @return
	 */
	public double getYaw() {
		return GryffinMath.visionXToAngle(getX());
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		return this.getX();
	}

}
