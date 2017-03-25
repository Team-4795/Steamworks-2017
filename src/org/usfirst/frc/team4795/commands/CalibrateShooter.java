package org.usfirst.frc.team4795.commands;

import org.usfirst.frc.team4795.robot.Robot;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CalibrateShooter extends Command {
    
    public CalibrateShooter() {
        requires(Robot.shooter);
    }
    
    protected void initialize() {
        Robot.shooter.changeControlMode(TalonControlMode.Speed);
        
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
        if(!SmartDashboard.containsKey("Setpoint (RPM)")) {
            SmartDashboard.putNumber("Setpoint (RPM)", 0.0);
        }
        if(!SmartDashboard.containsKey("Ramp Rate")) {
            SmartDashboard.putNumber("Ramp Rate", 12.0);
        }
        if(!SmartDashboard.containsKey("I Zone")) {
        	SmartDashboard.putNumber("I Zone", 0.0);
        }
    }

    protected void execute() {
        double P = SmartDashboard.getNumber("P", 0.0);
        double I = SmartDashboard.getNumber("I", 0.0);
        double D = SmartDashboard.getNumber("D", 0.0);
        double F = SmartDashboard.getNumber("F", 0.0);
        Robot.shooter.setPIDF(P, I, D, F);
        
        int izone = (int) SmartDashboard.getNumber("I Zone", 0.0);
        Robot.shooter.setIZone(izone);
        
        double rampRate = SmartDashboard.getNumber("Ramp Rate", 12.0);
        Robot.shooter.setRampRate(rampRate);
        
        double setpoint = SmartDashboard.getNumber("Setpoint (RPM)", 0.0);
        Robot.shooter.setRaw(/*Shooter.rpmToTicksPer10Ms(setpoint)*/setpoint);
        
        double speed = Robot.shooter.getSpeed();
        SmartDashboard.putNumber("Speed (RPM)", speed);
        
        double error = Robot.shooter.getSpeedError();
        SmartDashboard.putNumber("Error (RPM)", error);
    }
    
    @Override
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.shooter.spinBasic(0.0);
    }

    protected void interrupted() {
        end();
    }

}
