
package org.usfirst.frc.team4795.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.zeromq.*;

public class Robot extends IterativeRobot {
    
	ZMQ.Context context;
	ZMQ.Socket subscriber;
	
	@Override
	public void robotInit() {
	    context = ZMQ.context(1);
	    subscriber = context.socket(ZMQ.SUB);
	    subscriber.bind("tcp://*:5808");
	    subscriber.subscribe(new byte[]{});
	    subscriber.setReceiveTimeOut(10);
	}
	
	@Override
	public void disabledInit() {}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void autonomousInit() {}
	
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {}
	
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		byte[] angle = subscriber.recv();
		if(angle != null)
		    SmartDashboard.putString("Angle", angle.toString());
	}
	
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
}
