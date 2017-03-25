package org.usfirst.frc.team4795.commands;

import org.usfirst.frc.team4795.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SpinAgitator extends Command {

    private final double speed;
    private boolean wasIndexerOpen = false;

    public SpinAgitator(double speed) {
        requires(Robot.agitator);
        this.speed = speed;
    }

    @Override
    protected void initialize() {
        Robot.agitator.spin(speed);
        wasIndexerOpen = Robot.shooter.isIndexerOpen();
        if(speed != 0.0) {
        	Robot.shooter.openIndexer();
        }
    }

    @Override
    protected void execute() {}

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.agitator.spin(0.0);
        if(speed != 0.0 && !wasIndexerOpen) {
        	Robot.shooter.closeIndexer();
        }
    }

    @Override
    protected void interrupted() {
        end();
    }

}
