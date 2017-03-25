package org.usfirst.frc.team4795.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoGear extends CommandGroup {
	
	private static final double distance1 = 0.0;
	private static final double time1 = 0.0;
	private static final double angle = 0.0;
	private static final double time2 = 0.0;
	private static final double distance2 = 0.0;
	private static final double time3 = 0.0;
	private static final double distance3 = 0.0;
	private static final double time4 = 0.0;

	public AutoGear() {
		addSequential(new DriveDistance(distance1), time1);
		addSequential(new TurnToTarget(angle), time2);
		addSequential(new DriveDistance(distance2), time3);
		addSequential(new DriveDistance(-distance2), time3);
		addSequential(new TurnToTarget(-angle), time2);
		addSequential(new DriveDistance(distance3), time4);
	}
	
}
