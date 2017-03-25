package org.usfirst.frc.team4795.commands;

import org.usfirst.frc.team4795.robot.Robot;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CalibrateClimber extends Command {
    
    public CalibrateClimber() {
        requires(Robot.climber);
    }
    
    protected void initialize() {
        Robot.climber.changeControlMode(TalonControlMode.Current);
        
        if(!SmartDashboard.containsKey("P")) {
            SmartDashboard.putNumber("P", 0.0);
        }
        if(!SmartDashboard.containsKey("I")) {
            SmartDashboard.putNumber("I", 0.0);
        }
        if(!SmartDashboard.containsKey("D")) {
            SmartDashboard.putNumber("D", 0.0);
        }
        if(!SmartDashboard.containsKey("F")) {
            SmartDashboard.putNumber("F", 0.0);
        }
        if(!SmartDashboard.containsKey("Setpoint (Amps)")) {
            SmartDashboard.putNumber("Setpoint (Amps)", 0.0);
        }
    }

    protected void execute() {
        double P = SmartDashboard.getNumber("P", 0.0);
        double I = SmartDashboard.getNumber("I", 0.0);
        double D = SmartDashboard.getNumber("D", 0.0);
        double F = SmartDashboard.getNumber("F", 0.0);
        Robot.climber.setPIDF(P, I, D, F);
        
        double setpoint = SmartDashboard.getNumber("Setpoint (Amps)", 0.0);
        Robot.climber.setRaw(setpoint);
        
        double current = Robot.climber.getCurrent();
        SmartDashboard.putNumber("Current (Amps)", current);
        
        double error = Robot.climber.getError();
        SmartDashboard.putNumber("Error (Amps)", error);
    }
    
    @Override
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.climber.climb(0.0);
    }

    protected void interrupted() {
        end();
    }

}
