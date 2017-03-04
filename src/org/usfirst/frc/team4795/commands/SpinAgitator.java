package org.usfirst.frc.team4795.commands;

import org.usfirst.frc.team4795.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SpinAgitator extends Command {

    private final double speed;

    public SpinAgitator(double speed) {
        requires(Robot.agitator);
        this.speed = speed;
    }

    @Override
    protected void initialize() {
        Robot.agitator.spin(speed);
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
    }

    @Override
    protected void interrupted() {
        end();
    }

}
