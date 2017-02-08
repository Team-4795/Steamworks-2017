package org.usfirst.frc.team4795.commands;

import org.usfirst.frc.team4795.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbDistance extends Command {
    
    private final double distance;
    
    /**
     * @param distance The distance to climb, in feet.
     */
    public ClimbDistance(double distance) {
        requires(Robot.climber);
        this.distance = distance;
    }
    
    protected void initialize() {
        Robot.climber.climbFeet(distance);
    }

    protected void execute() {}
    
    @Override
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.climber.climbBasic(0.0);
    }

    protected void interrupted() {
        end();
    }

}
