package org.usfirst.frc.team4795.robot.subsystems;

import org.usfirst.frc.team4795.commands.SpinAgitator;
import org.usfirst.frc.team4795.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Agitator extends Subsystem {
    
    private final Talon motor;
    
    public Agitator() {
        motor = new Talon(RobotMap.AGITATOR_MOTOR.value);
        
        LiveWindow.addActuator("Shooter", "Agitator", motor);
    }
    
    public void spin(double speed) {
        motor.set(speed);
    }
    
    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new SpinAgitator(0.0));
    }
    
}
