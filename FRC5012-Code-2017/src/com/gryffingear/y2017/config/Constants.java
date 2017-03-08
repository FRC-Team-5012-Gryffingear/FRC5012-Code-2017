package com.gryffingear.y2017.config;

public class Constants {

	public static class Drivetrain {

	
		public static int DRIVETRAIN_RAMP_RATE = 48;
	}


    public static final double CD_SENS_HIGH         = 1;
    public static final double CD_SENS_LOW          = 1.111;
    public static final double CD_WHEEL_NONLIN_HIGH = 0.1;
    public static final double CD_WHEEL_NONLIN_LOW  = 0.8;
    public static final double CD_NEG_INERTIA       = 3.0;
    
	
	public static class Shooter {

		public static int SHOOTER_RAMP_RATE = 0;
		public static int TURRET_RAMP_RATE = 0;
	}
	
	public static class SuperSystem {
		
		public static final double AUTO_AIM_KP = -.0015625;
		public static final double AUTO_AIM_TARGET_OFFSET = 0;
		
	}
}
