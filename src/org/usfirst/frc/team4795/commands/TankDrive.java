package org.usfirst.frc.team4795.commands;

import org.usfirst.frc.team4795.robot.Robot;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TankDrive extends Command {

    public TankDrive() {
        requires(Robot.drivetrain);
    }

    protected void initialize() {
        Robot.drivetrain.changeControlMode(TalonControlMode.PercentVbus);
    }

    protected void execute() {
        double throttle = (1.0 - Robot.oi.LEFT_JOY.getThrottle()) / -2.0;
        double left = Robot.drivetrain.reverseControls ? Robot.oi.getRightJoyY()
                : Robot.oi.getLeftJoyY();
        double right = Robot.drivetrain.reverseControls ? Robot.oi.getLeftJoyY()
                : Robot.oi.getRightJoyY();
        throttle *= Robot.drivetrain.reverseControls ? 1.0 : -1.0;
        Robot.drivetrain.setRaw(left * throttle, right * throttle);
        
        SmartDashboard.putNumber("Left Enc", Robot.drivetrain.getLeftEncoderPos());
        SmartDashboard.putNumber("Right Enc", Robot.drivetrain.getRightEncoderPos());
    }

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
