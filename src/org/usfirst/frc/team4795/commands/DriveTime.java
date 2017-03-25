package org.usfirst.frc.team4795.commands;

import org.usfirst.frc.team4795.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveTime extends Command {

	private final double time;
	private double goal;
	private double initial;

	public DriveTime(double time) {
		requires(Robot.drivetrain);
		this.time = time;
	}

	protected void initialize() {
		double power = Robot.drivetrain.reverseControls
				? -1.0 : 1.0;
		Robot.drivetrain.setRaw(power, power);
		initial = System.currentTimeMillis();
		goal = initial + time;
	}

	protected void execute() {}

	@Override
	protected boolean isFinished() {
		return System.currentTimeMillis() > goal;
	}

	protected void end() {
		Robot.drivetrain.driveBasic(0.0, 0.0);
	}

	protected void interrupted() {
		end();
	}

}
