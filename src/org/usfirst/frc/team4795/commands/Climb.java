package org.usfirst.frc.team4795.commands;

import org.usfirst.frc.team4795.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Climb extends Command {

    public Climb() {
        requires(Robot.climber);
    }
    
    protected void initialize() {}

    protected void execute() {
        double throttle = (1.0 - Robot.oi.RIGHT_JOY.getThrottle()) / 2.0;
        Robot.climber.climb(throttle);
    }

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
