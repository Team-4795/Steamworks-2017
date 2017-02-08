package org.usfirst.frc.team4795.robot.subsystems;

import org.usfirst.frc.team4795.commands.SpinIntake;
import org.usfirst.frc.team4795.robot.RobotMap;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Intake extends Subsystem {
    
    private final VictorSP motor;
    
    public Intake() {
        motor = new VictorSP(RobotMap.INTAKE_MOTOR.value);
        
        LiveWindow.addActuator("Intake", "Motor", motor);
    }
    
    /**
     * @param speed The speed to spin at from -1.0 to 1.0
     */
    public void spin(double speed) {
        motor.set(speed);
    }
    
    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new SpinIntake(0.0));
    }

}
