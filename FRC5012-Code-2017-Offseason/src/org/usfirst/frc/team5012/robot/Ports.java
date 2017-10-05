package org.usfirst.frc.team5012.robot;

public class Ports {

	public static class Controls {

		
		public static int DRIVER_PORT = 2;
		public static int OPERATOR_PORT = 3;
	}

	public static class Drivetrain {

		public static int DRIVE_LEFT_A_MOTOR = 1;
		public static int DRIVE_LEFT_B_MOTOR = 2;
	
	

		public static int DRIVE_RIGHT_A_MOTOR = 3;
		public static int DRIVE_RIGHT_B_MOTOR = 4;
	
		
		public static int DRIVE_GYRO_PORT = 0;
		
		
	}
	
	public static class UtilityArm { 
		
		public static int INTAKE_MOTOR = 7;
	//	public static int ARM_ENCODER = 4;
	//	public static int ARM_MOTOR = 6;
		
		public static int ARM_BUMP_SWITCH = 0;
		public static int ARM_LIMIT_SWITCH = 1;
		
	//	public static int ARM_LED_A = 0;
	//	public static int ARM_LED_B = 1;
	//	public static int ARM_LED_C = 2;
		
	}
 
    public static class Climber {
		
		public static int CLIMBER_MOTOR_A = 5;
		public static int CLIMBER_MOTOR_B = 6;
		
	}
	public static class Pneumatics {
		
		public static int PCM_CAN_ID = 0;
	}
	public static class PixyCam {
		public static int PixyCam = 0;
	}
	public static class Claw {
		public static int CLAW_PORT = 0;
	}
}