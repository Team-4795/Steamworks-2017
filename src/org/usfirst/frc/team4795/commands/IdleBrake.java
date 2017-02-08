package org.usfirst.frc.team4795.commands;

import org.usfirst.frc.team4795.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IdleBrake extends Command {

	public IdleBrake() {
		requires(Robot.drivetrain);
	}

	protected void initialize() {
		Robot.drivetrain.setBrakeMode(true);
	}

	protected void execute() {}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	    Robot.drivetrain.setBrakeMode(false);
	}

	protected void interrupted() {
		end();
	}
	
}
