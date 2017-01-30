package com.gryffingear.y2017.utilities;

import com.gryffingear.y2017.systems.GRIPVision;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class VisionPIDSource implements PIDSource {
	
	GRIPVision grip = GRIPVision.getInstance();
	
	public VisionPIDSource() {
		this.setPIDSourceType(PIDSourceType.kDisplacement);
		
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
		return grip.getYaw();
	}

}
