package org.usfirst.frc.team4795.robot.subsystems;

import org.usfirst.frc.team4795.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem implements PIDOutput {

	public static final double WHEEL_DIAMETER_IN = 4.0;
	public static final int ENCODER_TICKS_PER_REV = 2048;
	public static final double ENCODER_TICKS_PER_FT =
	        (ENCODER_TICKS_PER_REV * 48) / (Math.PI * WHEEL_DIAMETER_IN);
	public static final double ENCODER_TICKS_PER_METER = (ENCODER_TICKS_PER_FT * 2.54) / 1200;

	private final CANTalon leftMotor1;
	private final CANTalon leftMotor2;
	private final CANTalon rightMotor1;
	private final CANTalon rightMotor2;

	public final ADXRS450_Gyro gyroscope;
	private PIDController gyroControl;

	private boolean closedLoopMode = false;
	private boolean gyroControlMode = false;

	private boolean brakeMode = false;

	public Drivetrain() {
		gyroscope = new ADXRS450_Gyro() {
			@Override
			public double getAngle() {
				return super.getAngle() % 360.0;
			}
		};
		gyroscope.setPIDSourceType(PIDSourceType.kDisplacement);

		leftMotor1 = new CANTalon(RobotMap.LEFT_MOTOR_1.value);
		leftMotor2 = new CANTalon(RobotMap.LEFT_MOTOR_2.value);
		rightMotor1 = new CANTalon(RobotMap.RIGHT_MOTOR_1.value);
		rightMotor2 = new CANTalon(RobotMap.RIGHT_MOTOR_2.value);

		disableControl();

		leftMotor1.configEncoderCodesPerRev(ENCODER_TICKS_PER_REV);
		rightMotor1.configEncoderCodesPerRev(ENCODER_TICKS_PER_REV);

		leftMotor1.reverseSensor(true);
		rightMotor1.reverseSensor(false);

		leftMotor1.ConfigFwdLimitSwitchNormallyOpen(true);
		leftMotor1.ConfigRevLimitSwitchNormallyOpen(true);
		leftMotor2.ConfigFwdLimitSwitchNormallyOpen(true);
		leftMotor2.ConfigRevLimitSwitchNormallyOpen(true);
		rightMotor1.ConfigFwdLimitSwitchNormallyOpen(true);
		rightMotor1.ConfigRevLimitSwitchNormallyOpen(true);
		rightMotor2.ConfigFwdLimitSwitchNormallyOpen(true);
		rightMotor2.ConfigRevLimitSwitchNormallyOpen(true);

		leftMotor1.configMaxOutputVoltage(12);
		leftMotor2.configMaxOutputVoltage(12);
		rightMotor1.configMaxOutputVoltage(12);
		rightMotor2.configMaxOutputVoltage(12);

		leftMotor1.enableBrakeMode(false);
		leftMotor2.enableBrakeMode(false);
		rightMotor1.enableBrakeMode(false);
		rightMotor2.enableBrakeMode(false);
	}

	public void init() {
		gyroControl = new PIDController(0.0, 0.0, 0.0, 0.0, gyroscope, this);
		gyroControl.setPercentTolerance(1);
		gyroControl.setOutputRange(-1.0, 1.0);
		gyroControl.setInputRange(0.0, 360.0);
		gyroControl.setContinuous(true);
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

	public void drive(double left, double right) {
		changeControlMode(TalonControlMode.PercentVbus);
		setRaw(left, right);
	}
	
	public void driveFeet(double distance, double P, double I, double D, double F) {
		changeControlMode(TalonControlMode.Position);
		setPIDF(P, I, D, F);
		double distanceTicks = distance * ENCODER_TICKS_PER_FT;
		setRaw(leftMotor1.getPosition() + distanceTicks,
				rightMotor1.getPosition() + distanceTicks);
	}
	
	public void driveMeters(double distance, double P, double I, double D, double F) {
        changeControlMode(TalonControlMode.Position);
        setPIDF(P, I, D, F);
        double distanceTicks = distance * ENCODER_TICKS_PER_METER;
        setRaw(leftMotor1.getPosition() + distanceTicks,
                rightMotor1.getPosition() + distanceTicks);
    }
	
	public void rotateRadians(double angle, double P, double I, double D, double F) {
		rotateDegrees(Math.toDegrees(angle), F, P, I, D);
	}

	public void rotateDegrees(double angle, double P, double I, double D, double F) {
		changeControlMode(TalonControlMode.PercentVbus);
		gyroControl.setPID(P, I, D, F);
		gyroControl.setSetpoint(gyroscope.getAngle() + angle);
		gyroControl.enable();
		gyroControlMode = true;
	}

	public void toggleBrakeMode() {
		brakeMode = !brakeMode;
		leftMotor1.enableBrakeMode(brakeMode);
		leftMotor2.enableBrakeMode(brakeMode);
		rightMotor1.enableBrakeMode(brakeMode);
		rightMotor2.enableBrakeMode(brakeMode);
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
		return rightMotor1.getPosition();
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

	public void calibrateGyroscope() {
		gyroscope.calibrate();
	}

	@Override
	protected void initDefaultCommand() {}

}

