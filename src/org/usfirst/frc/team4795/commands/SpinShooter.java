package org.usfirst.frc.team4795.commands;

import org.usfirst.frc.team4795.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SpinShooter extends Command {
    
    private final double speed;
    
    /**
     * @param speed The speed to spin at, in rpm.
     */
    public SpinShooter(double speed) {
        requires(Robot.shooter);
        this.speed = speed;
    }
    
    protected void initialize() {
        Robot.shooter.spinSpeed(speed);
    }

    protected void execute() {}
    
    @Override
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.shooter.spinBasic(0.0);
    }

    protected void interrupted() {
        end();
    }

}
