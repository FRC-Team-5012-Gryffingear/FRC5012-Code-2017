package com.gryffingear.y2017.systems;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class GRIPVision {
	
	private static GRIPVision instance = null;

	NetworkTable table;

	double x = 0;
	double y = 0;
	double area = 0;

	double[] defaultValue = new double[0];

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

		if (table.getNumberArray("centerX", defaultValue) != null
				&& table.getNumberArray("centerX", defaultValue).length > 0) {

			x = (table.getNumberArray("centerX", defaultValue)[0] - 320);
			y = (table.getNumberArray("centerY", defaultValue)[0] - 240);
			area = table.getNumberArray("area", defaultValue)[0];
		}
	}
	
	
	/**
	 * Converts x position to a yaw angle 
	 * @return
	 */
	public double getYaw() {
		return getX();
	}

}
