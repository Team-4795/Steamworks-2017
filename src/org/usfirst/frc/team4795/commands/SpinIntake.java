package org.usfirst.frc.team4795.commands;

import org.usfirst.frc.team4795.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SpinIntake extends Command {

	private final double speed;

	public SpinIntake(double speed) {
		requires(Robot.intake);
		this.speed = speed;
	}

	@Override
	protected void initialize() {
		Robot.intake.spin(speed);
	}

	@Override
	protected void execute() {}

	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.intake.spin(0.0);
	}

	@Override
	protected void interrupted() {
		end();
	}

}
