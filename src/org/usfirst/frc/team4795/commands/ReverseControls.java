package org.usfirst.frc.team4795.commands;

import org.usfirst.frc.team4795.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ReverseControls extends Command {
    
    /**
     * @param speed The speed to spin at, in rpm.
     */
    public ReverseControls() {}
    
    protected void initialize() {
        Robot.drivetrain.reverseControls = !Robot.drivetrain.reverseControls;
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
