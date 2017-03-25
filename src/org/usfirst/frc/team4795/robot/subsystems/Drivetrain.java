package org.usfirst.frc.team4795.robot.subsystems;

import org.usfirst.frc.team4795.commands.TankDrive;
import org.usfirst.frc.team4795.robot.Robot;
import org.usfirst.frc.team4795.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Drivetrain extends Subsystem implements PIDOutput {
    
    public static final double P_POS = 0.8;
    public static final double I_POS = 0.001;
    public static final double D_POS = 1000.0;
    public static final int I_ZONE_POS = 2000;
    
    public static final double P_GYRO_POS = -0.047;
    public static final double I_GYRO_POS = 0.0;
    public static final double D_GYRO_POS = -0.56;

	public static final double WHEEL_DIAMETER_IN = 4.0;
	public static final int ENCODER_TICKS_PER_REV = 2048;
	public static final double ENCODER_TICKS_PER_FT =
	        (ENCODER_TICKS_PER_REV * 48) / (Math.PI * WHEEL_DIAMETER_IN);

	private final CANTalon leftMotor1;
	private final CANTalon leftMotor2;
	private final CANTalon rightMotor1;
	private final CANTalon rightMotor2;

//	public final ADXRS450_Gyro gyroscope;
	public PIDController gyroControl;

	private boolean closedLoopMode = false;
	private boolean gyroControlMode = false;
	
	private boolean brakeMode = false;
	public boolean reverseControls = true;

	public Drivetrain() {
//		gyroscope = new ADXRS450_Gyro() {
//			@Override
//			public double getAngle() {
//				return super.getAngle() % 360.0;
//			}
//		};
//		gyroscope.setPIDSourceType(PIDSourceType.kDisplacement);

		leftMotor1 = new CANTalon(RobotMap.LEFT_MOTOR_1.value);
		leftMotor2 = new CANTalon(RobotMap.LEFT_MOTOR_2.value);
		rightMotor1 = new CANTalon(RobotMap.RIGHT_MOTOR_1.value);
		rightMotor2 = new CANTalon(RobotMap.RIGHT_MOTOR_2.value);
		
		Robot.initTalon(leftMotor1, ENCODER_TICKS_PER_REV);
		Robot.initTalon(leftMotor2, ENCODER_TICKS_PER_REV);
		leftMotor1.reverseSensor(false);
		Robot.initTalon(rightMotor1, ENCODER_TICKS_PER_REV);
		Robot.initTalon(rightMotor2, ENCODER_TICKS_PER_REV);
		rightMotor1.reverseSensor(true);
		
		LiveWindow.addSensor("Drivetrain", "Left Motor 1", leftMotor1);
		LiveWindow.addSensor("Drivetrain", "Left Motor 2", leftMotor2);
		LiveWindow.addSensor("Drivetrain", "Right Motor 1", rightMotor1);
		LiveWindow.addSensor("Drivetrain", "Right Motor 2", rightMotor2);
	}

	public void init() {
	    IMU.getInstance().setPIDSourceType(PIDSourceType.kDisplacement);
	    
//	    gyroControl = new PIDController(0.0, 0.0, 0.0, 0.0, gyroscope, this);
		gyroControl = new PIDController(0.0, 0.0, 0.0, 0.0, IMU.getInstance(), this);
		gyroControl.setPercentTolerance(1);
		gyroControl.setOutputRange(-1.0, 1.0);
		gyroControl.setInputRange(0.0, 360.0);
		gyroControl.setContinuous(true);
		
//		LiveWindow.addSensor("Drivetrain", "Gyroscope", gyroControl);
	    LiveWindow.addSensor("Drivetrain", "IMU", IMU.getInstance());
	}

	public void disableControl() {
		leftMotor1.disableControl();
		leftMotor2.disableControl();
		rightMotor1.disableControl();
		rightMotor2.disableControl();
	}

	public void enableControl() {
		leftMotor1.enableControl();
		leftMotor2.enableControl();
		rightMotor1.enableControl();
		rightMotor2.enableControl();
	}

	public void changeControlMode(TalonControlMode mode) {
		if (gyroControlMode) {
			gyroControl.reset();
			gyroControlMode = false;
		}
		disableControl();
		leftMotor1.changeControlMode(mode);
		rightMotor1.changeControlMode(mode);
		if (mode != TalonControlMode.Position && mode != TalonControlMode.Speed) {
			leftMotor2.changeControlMode(mode);
			rightMotor2.changeControlMode(mode);
			closedLoopMode = false;
		} else {
			leftMotor2.changeControlMode(TalonControlMode.Follower);
			leftMotor2.set(RobotMap.LEFT_MOTOR_1.value);
			rightMotor2.changeControlMode(TalonControlMode.Follower);
			rightMotor2.set(RobotMap.RIGHT_MOTOR_1.value);
			closedLoopMode = true;
		}
		enableControl();
	}

	public void setRaw(double left, double right) {
		leftMotor1.set(left);
		rightMotor1.set(right);
		if (!closedLoopMode) {
			leftMotor2.set(left);
			rightMotor2.set(right);
		}
	}

	@Override
	public void pidWrite(double output) {
		// XXX using negatives since this is only called by the rotation controller
		leftMotor1.pidWrite(-output);
		leftMotor2.pidWrite(-output);
		rightMotor1.pidWrite(output);
		rightMotor2.pidWrite(output);
	}

	public void setPIDF(double P, double I, double D, double F) {
		leftMotor1.setPID(P, I, D);
		leftMotor1.setF(F);
		rightMotor1.setPID(P, I, D);
		rightMotor1.setF(F);
	}
	
	public void setIZone(int izone) {
		leftMotor1.setIZone(izone);
		rightMotor1.setIZone(izone);
	}

	public void driveBasic(double left, double right) {
		changeControlMode(TalonControlMode.PercentVbus);
		setRaw(left, right);
	}
	
	public void driveFeet(double distance) {
		changeControlMode(TalonControlMode.Position);
		setPIDF(P_POS, I_POS, D_POS, 0.0);
		setIZone(I_ZONE_POS);
		double distanceTicks = distance * ENCODER_TICKS_PER_FT;
		setRaw(leftMotor1.getPosition() + distanceTicks,
				rightMotor1.getPosition() + distanceTicks);
	}
	
	public void driveMeters(double distance) {
        driveFeet(distance / Robot.METERS_PER_FT);
    }
	
	public void rotateRadians(double angle) {
		rotateDegrees(Math.toDegrees(angle));
	}

	public void rotateDegrees(double angle) {
		changeControlMode(TalonControlMode.PercentVbus);
		gyroControl.setPID(P_GYRO_POS, I_GYRO_POS, D_GYRO_POS, 0.0);
		gyroControl.setSetpoint(IMU.getInstance().pidGet() + angle);
		gyroControl.enable();
		gyroControlMode = true;
	}

	public void setBrakeMode(boolean value) {
	    brakeMode = value;
		leftMotor1.enableBrakeMode(value);
		leftMotor2.enableBrakeMode(value);
		rightMotor1.enableBrakeMode(value);
		rightMotor2.enableBrakeMode(value);
	}
	
	public boolean getBrakeMode() {
	    return brakeMode;
	}

	public void setRampRate(double rampRate) {
		leftMotor1.setVoltageRampRate(rampRate);
		leftMotor2.setVoltageRampRate(rampRate);
		rightMotor1.setVoltageRampRate(rampRate);
		rightMotor2.setVoltageRampRate(rampRate);
	}

	public double getLeftCurrent() {
		return leftMotor1.getOutputCurrent();
	}
	
	public double getRightCurrent() {
	    return rightMotor1.getOutputCurrent();
	}

	public double getLeftError() {
		return leftMotor1.getError();
	}

	public double getRightError() {
		return rightMotor1.getError();
	}

	public double getLeftEncoderPos() {
		return leftMotor1.getEncPosition();
	}

	public double getRightEncoderPos() {
		return rightMotor1.getEncPosition();
	}

	public double getLeftEncoderVel() {
		return leftMotor1.getEncVelocity();
	}

	public double getRightEncoderVel() {
		return rightMotor1.getEncVelocity();
	}

	public double getLeftSpeed() {
		return leftMotor1.getSpeed();
	}

	public double getRightSpeed() {
		return rightMotor1.getSpeed();
	}
	
	public boolean isGyroOnTarget() {
	    return gyroControlMode && gyroControl.onTarget();
	}

//	public void calibrateGyroscope() {
//		gyroscope.calibrate();
//	}

	@Override
	protected void initDefaultCommand() {
	    setDefaultCommand(new TankDrive());
	}

}

