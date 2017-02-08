package org.usfirst.frc.team4795.robot.subsystems;

import org.usfirst.frc.team4795.commands.SpinShooter;
import org.usfirst.frc.team4795.robot.Robot;
import org.usfirst.frc.team4795.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Shooter extends Subsystem {
    
    private static final double P_SPEED = 0.0;
    private static final double I_SPEED = 0.0;
    private static final double D_SPEED = 0.0;
    private static final double F_SPEED = 0.0;
    
    private static final int ENCODER_TICKS_PER_REV = 2048;
    
    private final CANTalon motor;
    
    public Shooter() {
        motor = new CANTalon(RobotMap.SHOOTER_MOTOR.value);
        Robot.initTalon(motor, ENCODER_TICKS_PER_REV);
        
        LiveWindow.addActuator("Shooter", "Motor", motor);
    }
    
    public void disableControl() {
        motor.disableControl();
    }
    
    public void enableControl() {
        motor.enableControl();
    }
    
    public void changeControlMode(TalonControlMode mode) {
        disableControl();
        motor.changeControlMode(mode);
        enableControl();
    }
    
    public void setRaw(double value) {
        motor.set(value);
    }
    
    private void setPIDF(double P, double I, double D, double F) {
        motor.setPID(P, I, D);
        motor.setF(F);
    }
    
    /**
     * Set the intake motor output to the given value.
     * @param value The output value to set from -1.0 to 1.0
     */
    public void spinBasic(double value) {
        changeControlMode(TalonControlMode.PercentVbus);
        setRaw(value);
    }
    
    /**
     * Spin the intake at the given speed.
     * @param speed The speed to spin at in rpm
     */
    public void spinSpeed(double speed) {
        changeControlMode(TalonControlMode.Speed);
        setPIDF(P_SPEED, I_SPEED, D_SPEED, F_SPEED);
        // convert to units of encoder ticks per 10ms
        setRaw((speed * ENCODER_TICKS_PER_REV) / 6000.0);
    }
    
    /**
     * Ramp voltage output at the given rate.
     * @param rampRate The rate at which to ramp output voltage, in volts per second
     */
    public void setRampRate(double rampRate) {
        motor.setVoltageRampRate(rampRate);
    }
    
    /**
     * @return The current going through the Talon, in amps.
     */
    public double getCurrent() {
        return motor.getOutputCurrent();
    }
    
    /**
     * @return The current speed of the motor, in rpm.
     */
    public double getSpeed() {
        // convert from units of encoder ticks per 100ms
        return (motor.getSpeed() * 600.0) / ENCODER_TICKS_PER_REV;
    }
    
    /**
     * @return The deviation from the target speed, in rpm.
     */
    public double getSpeedError() {
        // convert from units of encoder ticks per 10ms
        return (motor.getError() * 6000.0) / ENCODER_TICKS_PER_REV;
    }
    
    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new SpinShooter(0.0));
    }

}
