package org.usfirst.frc.team4795.robot.subsystems;

import org.usfirst.frc.team4795.robot.BNO055;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

public class IMU extends Subsystem implements PIDSource {
    
    private static IMU instance = null; 
	private static BNO055 imu;
    
    private IMU() {	
    	imu = BNO055.getInstance(BNO055.opmode_t.OPERATION_MODE_NDOF,
    	        BNO055.vector_type_t.VECTOR_EULER);
    }
    
	public static IMU getInstance() {
		if(instance == null) {
			instance = new IMU();
		}
		
		return instance;
	}
	
	@Override
    public void initDefaultCommand() {}
   
    /**
	 * The heading of the sensor (x axis) in continuous format. Eg rotating the
	 *   sensor clockwise two full rotations will return a value of 720 degrees.
	 *
	 * @return heading in degrees
     */
    public double getHeading() {
    	return imu.getHeading();
    }
    
    /**
     * Gets a vector representing the sensors position (heading, roll, pitch).
	 * heading:    0 to 360 degrees
	 * roll:     -90 to +90 degrees
	 * pitch:   -180 to +180 degrees
	 *
	 * For continuous rotation heading (doesn't roll over between 360/0) see
	 *   the getHeading() method.
	 *
	 * @return a vector [heading, roll, pitch]
	 */
    public double[] getVector() {
    	return imu.getVector();
    }
    
    /**
     * @return the rotation rate vector from the gyroscope in degrees per second
     */
    public double[] getDubya() {
    	return imu.getDubya();
    }
    
	public boolean isSensorPresent() {
		return imu.isSensorPresent();
	}
	
	public boolean isInitialized() {
		return imu.isInitialized();
	}
	
	/**
	 * Gets current IMU calibration state.
	 * @return each value will be set to 0 if not calibrated, 3 if fully calibrated.
	 */
	public BNO055.CalStatus getCalibrationStatus() {
		return imu.getCalibrationStatus();
	}
	
	/**
	 * Returns true if all required sensors (accelerometer, magnetometer,
	 *   gyroscope) in the IMU have completed their respective calibration
	 *   sequence.
	 * @return true if calibration is complete for all sensors required for the
	 *   mode the sensor is currently operating in. 
	 */
	public boolean isCalibrated() {
		return imu.isCalibrated();
	}
	
	private PIDSourceType sourceType = PIDSourceType.kRate;

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		sourceType = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return sourceType;
	}

	@Override
	/**
	 * Return rotation rate in Z in units selected on IMU
	 */
	public double pidGet() {
		return imu.getDubya()[2];
	}
}
