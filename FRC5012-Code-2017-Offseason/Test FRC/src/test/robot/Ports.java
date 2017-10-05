package test.robot;

public class Ports {

	public static class Controls {

		public static int DRIVER_LEFT_PORT = 0;
		public static int DRIVER_RIGHT_PORT = 1;
		public static int OPERATOR_PORT = 2;
	}

	public static class Drivetrain {

		public static int DRIVE_LEFT_A_MOTOR = 0;
		public static int DRIVE_LEFT_B_MOTOR = 1;
		public static int DRIVE_LEFT_C_MOTOR = 2;
		public static int DRIVE_LEFT_ENC_PORT = 0;

		public static int DRIVE_RIGHT_A_MOTOR = 3;
		public static int DRIVE_RIGHT_B_MOTOR = 4;
		public static int DRIVE_RIGHT_C_MOTOR = 5;
		public static int DRIVE_RIGHT_ENC_PORT = 1;
		
		public static int DRIVE_GYRO_PORT = 0;
		
		
	}
	
	public static class UtilityArm { 
		
		public static int INTAKE_MOTOR = 8;
		public static int ARM_ENCODER = 4;
		public static int ARM_MOTOR = 6;
		
		public static int ARM_BUMP_SWITCH = 0;
		public static int ARM_LIMIT_SWITCH = 1;
		
		public static int ARM_LED_A = 0;
		public static int ARM_LED_B = 1;
		public static int ARM_LED_C = 2;
		
	}
     public static class Feeder {
		
		public static int AGITATOR_MOTOR_A = 17;
		public static int AGITATOR_MOTOR_B = 15;
		
		public static int FEEDER_MOTOR = 10;
		
	}
	
    public static class Climber {
		
		public static int CLIMBER_MOTOR_A = 11;
		public static int CLIMBER_MOTOR_B = 12;
		public static int CLIMBER_BUMP_PORT = 2;
	}
	public static class Pneumatics {
		
		public static int PCM_CAN_ID = 0;
	}
}