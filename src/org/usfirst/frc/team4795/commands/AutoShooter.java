package org.usfirst.frc.team4795.commands;

import java.nio.charset.Charset;

import org.usfirst.frc.team4795.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class AutoShooter extends Command {
    
    TurnToTarget drivetrainCmd;

    public AutoShooter() {
        requires(Robot.drivetrain);
        requires(Robot.shooter);
    }

    protected void initialize() {
        drivetrainCmd = new TurnToTarget(Robot.angle);
        Scheduler.getInstance().add(drivetrainCmd);
    }

    protected void execute() {}

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
       drivetrainCmd.cancel();
    }

    protected void interrupted() {
        end();
    }
    
}
