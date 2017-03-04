package org.usfirst.frc.team4795.commands;

import org.usfirst.frc.team4795.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class AutoShooter extends Command {
    
    TurnToTarget turnCmd;
    DriveDistance driveCmd;

    public AutoShooter() {
        requires(Robot.drivetrain);
        requires(Robot.shooter);
    }

    protected void initialize() {
        turnCmd = new TurnToTarget(Robot.angle);
        Scheduler.getInstance().add(turnCmd);
    }

    protected void execute() {
        if(Robot.drivetrain.isGyroOnTarget()) {
            // TODO
        } else {
            if(!turnCmd.isRunning()) {
                Scheduler.getInstance().add(turnCmd);
            }
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
       turnCmd.cancel();
    }

    protected void interrupted() {
        end();
    }
    
}
