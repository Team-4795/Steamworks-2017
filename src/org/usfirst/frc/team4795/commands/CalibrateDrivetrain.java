package org.usfirst.frc.team4795.commands;

import org.usfirst.frc.team4795.robot.BNO055.CalStatus;
import org.usfirst.frc.team4795.robot.Robot;
import org.usfirst.frc.team4795.robot.subsystems.IMU;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CalibrateDrivetrain extends Command {
    
    public CalibrateDrivetrain() {
        requires(Robot.drivetrain);
    }
    
    protected void initialize() {
        Robot.drivetrain.changeControlMode(TalonControlMode.PercentVbus);
        Robot.drivetrain.gyroControl.enable();
        
        if(!SmartDashboard.containsKey("P")) {
            SmartDashboard.putNumber("P", 0.0);
        }
        if(!SmartDashboard.containsKey("I")) {
            SmartDashboard.putNumber("I", 0.0);
        }
        if(!SmartDashboard.containsKey("D")) {
            SmartDashboard.putNumber("D", 0.0);
        }
        if(!SmartDashboard.containsKey("Setpoint (Degrees)")) {
            SmartDashboard.putNumber("Setpoint (Degrees)", 0.0);
        }
        if(!SmartDashboard.containsKey("Ramp Rate")) {
            SmartDashboard.putNumber("Ramp Rate", 12.0);
        }
    }

    protected void execute() {
        double P = SmartDashboard.getNumber("P", 0.0);
        double I = SmartDashboard.getNumber("I", 0.0);
        double D = SmartDashboard.getNumber("D", 0.0);
        Robot.drivetrain.gyroControl.setPID(P, I, D);
        
        double rampRate = SmartDashboard.getNumber("Ramp Rate", 12.0);
        Robot.drivetrain.setRampRate(rampRate);
        
        double setpoint = SmartDashboard.getNumber("Setpoint (Degrees)", 0.0);
        Robot.drivetrain.gyroControl.setSetpoint(setpoint);
        
        double position = IMU.getInstance().pidGet();
        SmartDashboard.putNumber("Position (Degrees)", position);
        
        double error = Robot.drivetrain.gyroControl.getError();
        SmartDashboard.putNumber("Error (Degrees)", error);
        
        CalStatus calStatus = IMU.getInstance().getCalibrationStatus();
        SmartDashboard.putNumber("Sys Cal", calStatus.sys);
        SmartDashboard.putNumber("Gyr Cal", calStatus.gyro);
        SmartDashboard.putNumber("Acc Cal", calStatus.accel);
        SmartDashboard.putNumber("Mag Cal", calStatus.mag);
    }
    
    @Override
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.drivetrain.gyroControl.reset();
    }

    protected void interrupted() {
        end();
    }

}
