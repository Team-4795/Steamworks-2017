
package org.usfirst.frc.team4795.robot;

import java.nio.charset.Charset;

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
    public static Climber climber;
    
    private static ZMQ.Context context;
	private static ZMQ.Socket subscriber;
	
	@Override
	public void robotInit() {
	    drivetrain = new Drivetrain();
	    drivetrain.init();
	    intake = new Intake();
	    shooter = new Shooter();
	    climber = new Climber();
	    
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
