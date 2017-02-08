package org.usfirst.frc.team4795.robot.subsystems;

import org.usfirst.frc.team4795.commands.ClimbDistance;
import org.usfirst.frc.team4795.robot.Robot;
import org.usfirst.frc.team4795.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Climber extends Subsystem {
    
    private static final double P_POS = 0.0;
    private static final double I_POS = 0.0;
    private static final double D_POS = 0.0;
    
    private static final int ENCODER_TICKS_PER_REV = 2048;
    private static final double SPOOL_DIAMETER_IN = 1.0;
    private static final double TICKS_PER_FT_CLIMB =
            (12 * ENCODER_TICKS_PER_REV) / (SPOOL_DIAMETER_IN * Math.PI);
    
    private final CANTalon motor;
    
    public Climber() {
        motor = new CANTalon(RobotMap.CLIMBER_MOTOR.value);
        Robot.initTalon(motor, ENCODER_TICKS_PER_REV);
        
        LiveWindow.addActuator("Climber", "Soldier: 76", motor);
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
    
    private void setPID(double P, double I, double D) {
        motor.setPID(P, I, D);
    }
    
    /**
     * Set the climber motor output to the given value.
     * @param value The output value to set from -1.0 to 1.0
     */
    public void climbBasic(double value) {
        changeControlMode(TalonControlMode.PercentVbus);
        setRaw(value);
    }
    
    /**
     * Climb the given distance.
     * @param distance The distance to climb in feet
     */
    public void climbFeet(double distance) {
        changeControlMode(TalonControlMode.Position);
        setPID(P_POS, I_POS, D_POS);
        // convert to units of encoder ticks
        double distanceTicks = distance * TICKS_PER_FT_CLIMB;
        setRaw(motor.getPosition() + distanceTicks);
    }
    
    /**
     * Climb the given distance.
     * @param distance The distance to climb in meters
     */
    public void climbMeters(double distance) {
        climbFeet(distance / Robot.METERS_PER_FT);
    }
    
    /**
     * @return The current going through the Talon, in amps.
     */
    public double getCurrent() {
        return motor.getOutputCurrent();
    }
    
    /**
     * @return The current position of the spool, in circumferential distance and units of feet.
     */
    public double getPositionFeet() {
        // convert from units of encoder ticks
        return motor.getPosition() / TICKS_PER_FT_CLIMB;
    }
    
    /**
     * @return The current position of the spool, in circumferential distance and units of meters.
     */
    public double getPositionMeters() {
        return getPositionFeet() * Robot.METERS_PER_FT;
    }
    
    /**
     * @return The deviation from the target position, in feet.
     */
    public double getPositionErrorFeet() {
        // convert from units of encoder ticks
        return motor.getError() / TICKS_PER_FT_CLIMB;
    }
    
    /**
     * @return The deviation from the target position, in meters.
     */
    public double getPositionErrorMeters() {
        // convert from units of encoder ticks
        return getPositionErrorFeet() * Robot.METERS_PER_FT;
    }
    
    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ClimbDistance(0.0));
    }

}
