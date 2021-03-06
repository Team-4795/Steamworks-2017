
package org.usfirst.frc.team4795.robot;

import java.nio.charset.Charset;

import org.usfirst.frc.team4795.commands.DriveTime;
import org.usfirst.frc.team4795.robot.subsystems.Agitator;
import org.usfirst.frc.team4795.robot.subsystems.Climber;
import org.usfirst.frc.team4795.robot.subsystems.Drivetrain;
import org.usfirst.frc.team4795.robot.subsystems.Intake;
import org.usfirst.frc.team4795.robot.subsystems.Shooter;
import org.zeromq.ZMQ;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
    
    public static OI oi;
    public static Drivetrain drivetrain;
    public static Intake intake;
    public static Shooter shooter;
    public static Agitator agitator;
    public static Climber climber;
    
    private static ZMQ.Context context;
    private static ZMQ.Socket subscriber;
	
	public static double angle = 0.0;
	//public static double distance = 0.0;
	
	protected void zmqUpdate() {
	    String angle = subscriber.recvStr(Charset.defaultCharset());
	    if(angle == null) {
	    	SmartDashboard.putString("Angle", "NULL ANGLE");
	    } else if(angle.equals("")) {
	    	SmartDashboard.putString("Angle", "NO ANGLE");
	    } else {
	        try {
	            Robot.angle = Double.parseDouble(angle);
	        } catch(Exception e) {
	            e.printStackTrace();
	        }
	        SmartDashboard.putString("Angle", angle);
	    }
	    /*
	    String distance = subscriber.recvStr(Charset.defaultCharset());
	    if(distance != null && !distance.equals("")) {
	        try {
	            Robot.distance = Double.parseDouble(distance);
	        } catch(Exception e) {
	            e.printStackTrace();
	        }
	    }
	    */
	}
	
	@Override
	public void robotInit() {
	    drivetrain = new Drivetrain();
	    drivetrain.init();
	    intake = new Intake();
	    shooter = new Shooter();
	    agitator = new Agitator();
	    climber = new Climber();
	    
	    oi = new OI();
        oi.init();
        
//      CameraServer.getInstance().startAutomaticCapture();
	    
	    context = ZMQ.context(1);
	    subscriber = context.socket(ZMQ.SUB);
	    subscriber.subscribe("".getBytes());
	    subscriber.connect("tcp://raspberrypi.local:5808");
	    subscriber.setReceiveTimeOut(10);
	}
	
	@Override
    public void teleopInit() {
	    //Scheduler.getInstance().add(new CalibrateShooter());
	}
	
	@Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
        zmqUpdate();
    }
    
	@Override
    public void autonomousInit() {
		Scheduler.getInstance().add(new DriveTime(3000));
	}
    
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
	
	// some universal utilities for subsystems
	
	public static final double METERS_PER_FT = (12.0 * 2.54) / 100.0;
	
	public static void initTalon(CANTalon motor, int encoderTicksPerRev) {
	    motor.disableControl();
	    motor.configEncoderCodesPerRev(encoderTicksPerRev);
	    motor.reverseSensor(false);
	    motor.ConfigFwdLimitSwitchNormallyOpen(true);
	    motor.ConfigRevLimitSwitchNormallyOpen(true);
	    motor.configMaxOutputVoltage(12.0);
	    motor.enableBrakeMode(false);
	    motor.setVoltageRampRate(24.0);
	    motor.enableControl();
	}
	
}
