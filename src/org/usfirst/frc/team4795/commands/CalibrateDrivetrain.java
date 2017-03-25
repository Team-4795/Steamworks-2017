package org.usfirst.frc.team4795.commands;

import org.usfirst.frc.team4795.robot.Robot;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CalibrateDrivetrain extends Command {
    
    public CalibrateDrivetrain() {
        requires(Robot.drivetrain);
    }
    
    protected void initialize() {
        Robot.drivetrain.changeControlMode(TalonControlMode.Position);
        
        if(!SmartDashboard.containsKey("P")) {
            SmartDashboard.putNumber("P", 0.0);
        }
        if(!SmartDashboard.containsKey("I")) {
            SmartDashboard.putNumber("I", 0.0);
        }
        if(!SmartDashboard.containsKey("D")) {
            SmartDashboard.putNumber("D", 0.0);
        }
        if(!SmartDashboard.containsKey("Setpoint")) {
            SmartDashboard.putNumber("Setpoint", 0.0);
        }
        if(!SmartDashboard.containsKey("Ramp Rate")) {
            SmartDashboard.putNumber("Ramp Rate", 12.0);
        }
        
        if(!SmartDashboard.containsKey("I Zone")) {
        	SmartDashboard.putNumber("I Zone", 0.0);
        }
    }

    protected void execute() {
    	int izone = (int) SmartDashboard.getNumber("I Zone", 0);
    	Robot.drivetrain.setIZone(izone);
    	
        double P = SmartDashboard.getNumber("P", 0.0);
        double I = SmartDashboard.getNumber("I", 0.0);
        double D = SmartDashboard.getNumber("D", 0.0);
        Robot.drivetrain.setPIDF(P, I, D, 0.0);
        
        double rampRate = SmartDashboard.getNumber("Ramp Rate", 12.0);
        Robot.drivetrain.setRampRate(rampRate);
        
        double setpoint = SmartDashboard.getNumber("Setpoint", 0.0);
        Robot.drivetrain.setRaw(setpoint, setpoint);
        
        double lposition = Robot.drivetrain.getLeftEncoderPos();
        double rposition = Robot.drivetrain.getRightEncoderPos();
        SmartDashboard.putNumber("Left Pos", lposition);
        SmartDashboard.putNumber("Right Pos", rposition);
        
        double lerror = Robot.drivetrain.getLeftError();
        double rerror = Robot.drivetrain.getRightError();
        SmartDashboard.putNumber("Left Error", lerror);
        SmartDashboard.putNumber("Right Error", rerror);
    }
    
    @Override
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.drivetrain.driveBasic(0.0, 0.0);
    }

    protected void interrupted() {
        end();
    }

}
