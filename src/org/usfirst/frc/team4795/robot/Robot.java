
package org.usfirst.frc.team4795.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.nio.charset.Charset;

import org.usfirst.frc.team4795.robot.subsystems.Drivetrain;
import org.zeromq.*;

public class Robot extends IterativeRobot {
    
    private OI oi;
    private Drivetrain drivetrain;
    
    private ZMQ.Context context;
	private ZMQ.Socket subscriber;
	
	@Override
	public void robotInit() {
	    drivetrain = new Drivetrain();
	    drivetrain.init();
	    
	    oi = new OI();
	    oi.init();
	    
	    context = ZMQ.context(1);
	    subscriber = context.socket(ZMQ.SUB);
	    subscriber.subscribe("".getBytes());
	    subscriber.connect("tcp://raspberrypi.local:5808");
	    subscriber.setReceiveTimeOut(10);
	}
	
	@Override
    public void teleopInit() {}
	
	@Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
        String angle = subscriber.recvStr(Charset.defaultCharset());
        if(angle != null && !angle.equals("")) {
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
