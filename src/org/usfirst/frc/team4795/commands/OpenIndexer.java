package org.usfirst.frc.team4795.commands;

import org.usfirst.frc.team4795.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class OpenIndexer extends Command {
    
    public OpenIndexer() {}
    
    protected void initialize() {
        Robot.shooter.openIndexer();
    }

    protected void execute() {}
    
    @Override
    protected boolean isFinished() {
        return true;
    }

    protected void end() {}

    protected void interrupted() {
        end();
    }

}
