package org.usfirst.frc.team4795.commands;

import org.usfirst.frc.team4795.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TurnToTarget extends Command {
    
	private final double angle;

	public TurnToTarget(double angle) {
		this.angle = angle;
	}

	protected void initialize() {
		Robot.drivetrain.rotateDegrees(angle);
	}

	protected void execute() {
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		
	}

	protected void interrupted() {
		end();
	}

}
