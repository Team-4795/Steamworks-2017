package org.usfirst.frc.team4795.robot.subsystems;

import org.usfirst.frc.team4795.commands.Climb;
import org.usfirst.frc.team4795.robot.Robot;
import org.usfirst.frc.team4795.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Climber extends Subsystem {
    
    private final CANTalon motor;
    
    public Climber() {
        motor = new CANTalon(RobotMap.CLIMBER_MOTOR.value);
        Robot.initTalon(motor, 1);
        motor.changeControlMode(TalonControlMode.PercentVbus);
        motor.enableBrakeMode(true);
        
        LiveWindow.addActuator("Climber", "Soldier: 76", motor);
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
    
    /**
     * @return The current going through the Talon, in amps.
     */
    public double getCurrent() {
        return motor.getOutputCurrent();
    }
    
    @Override
    protected void initDefaultCommand() {
        
    }

}
