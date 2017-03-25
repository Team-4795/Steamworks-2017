package org.usfirst.frc.team4795.robot.subsystems;

import org.usfirst.frc.team4795.robot.Robot;
import org.usfirst.frc.team4795.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Climber extends Subsystem {
	
	private static final double P_CURRENT = 0.0;
	private static final double I_CURRENT = 0.0;
	private static final double D_CURRENT = 0.0;
	private static final double F_CURRENT = 0.0;
    
    private final CANTalon motor;
    
    public Climber() {
        motor = new CANTalon(RobotMap.CLIMBER_MOTOR.value);
        Robot.initTalon(motor, 1);
        motor.enableBrakeMode(true);
        
        LiveWindow.addActuator("Climber", "Soldier: 76", motor);
    }
    
    public void setPIDF(double P, double I, double D, double F) {
    	motor.setPID(P, I, D);
    	motor.setF(F);
    }
    
    public void changeControlMode(TalonControlMode mode) {
    	motor.changeControlMode(mode);
    }
    
    public void setRaw(double value) {
        motor.set(value);
    }
    
    /**
     * Set the climber motor output to the given value.
     * @param value The output value to set from -1.0 to 1.0
     */
    public void climb(double value) {
        setRaw(value);
    }
    
    public void climbCurrent(double current) {
    	changeControlMode(TalonControlMode.Current);
    	setPIDF(P_CURRENT, I_CURRENT, D_CURRENT, F_CURRENT);
    	setRaw(current);
    }
    
    /**
     * @return The current going through the Talon, in amps.
     */
    public double getCurrent() {
        return motor.getOutputCurrent();
    }
    
    public double getError() {
    	return motor.getClosedLoopError();
    }
    
    @Override
    protected void initDefaultCommand() {
        
    }

}
