package org.usfirst.frc.team5012.robot.systems;

import org.usfirst.frc.team5012.robot.util.GryffinMath;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class GRIPVision implements PIDSource {

	private static GRIPVision instance = null;

	NetworkTable table;

	double x = 0;
	double y = 0;
	double area = 0;
	
	double min = 0;
	double max = 320;

	private GRIPVision() {

		table = NetworkTable.getTable("GRIP/myContoursReport");

	}

	public static GRIPVision getInstance() {
		if (instance == null) {
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

		double[] defaultValue = { 0 }; // initialize an array with size 1,
										// element is 0
		double[] cx = table.getNumberArray("centerX", defaultValue);

		if (cx != null) { // First check if null
			if (cx.length > 1) {
				
				// Average the two targets' x positions to find their centers.
				x = ((cx[0] + cx[1]) / 2) - (max / 2);
			} else {
				x = 0;
			}
		} else {
			x = 0;
		}
	}

	/**
	 * Converts x position to a yaw angle
	 * 
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
