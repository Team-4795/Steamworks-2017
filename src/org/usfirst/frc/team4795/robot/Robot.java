
package org.usfirst.frc.team4795.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.nio.charset.Charset;

import org.zeromq.*;

public class Robot extends IterativeRobot {
    
	private ZMQ.Context context;
	private ZMQ.Socket subscriber;
	
	@Override
	public void robotInit() {
	    context = ZMQ.context(1);
	    subscriber = context.socket(ZMQ.SUB);
	    subscriber.bind("tcp://*:5808");
	    subscriber.subscribe("B".getBytes());
	    subscriber.setReceiveTimeOut(10);
	}
	
	@Override
    public void teleopInit() {}
	
	@Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
        // read and discard the topic
        subscriber.recvStr(Charset.defaultCharset());
        
        String angle = subscriber.recvStr(Charset.defaultCharset());
        if(!angle.equals("")) {
            SmartDashboard.putString("Angle", angle);
        }
    }
    
	@Override
    public void autonomousInit() {}
    
	@Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }
    
    @Override
	public void disabledInit() {}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
}
