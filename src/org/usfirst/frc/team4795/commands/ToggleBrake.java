package org.usfirst.frc.team4795.commands;

import org.usfirst.frc.team4795.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ToggleBrake extends Command {

	public ToggleBrake() {
		requires(Robot.drivetrain);
	}
	
	protected void initialize() {
		Robot.drivetrain.setBrakeMode(!Robot.drivetrain.getBrakeMode());
	}

	protected void execute() {}

	protected boolean isFinished() {
		return true;
	}

	protected void end() {}

	protected void interrupted() {
		end();
	}
}
