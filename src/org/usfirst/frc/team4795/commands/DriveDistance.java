package org.usfirst.frc.team4795.commands;

import org.usfirst.frc.team4795.robot.Robot;
import org.usfirst.frc.team4795.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveDistance extends Command {
    
    private final double distance;

    public DriveDistance(double distance) {
        this.distance = distance;
    }

    protected void initialize() {
        Robot.drivetrain.driveFeet(distance);
    }

    protected void execute() {
    }

    @Override
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
