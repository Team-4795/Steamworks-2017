package org.usfirst.frc.team4795.robot;

import org.usfirst.frc.team4795.commands.Climb;
import org.usfirst.frc.team4795.commands.CloseIndexer;
import org.usfirst.frc.team4795.commands.OpenIndexer;
import org.usfirst.frc.team4795.commands.ReverseControls;
import org.usfirst.frc.team4795.commands.SpinAgitator;
import org.usfirst.frc.team4795.commands.SpinIntake;
import org.usfirst.frc.team4795.commands.SpinShooter;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * The operator interface, connects to peripherals on the driver station.
 */
public class OI {
    
    public static final double JOY_DEADZONE = 0.1;
    //public static final double LEVER_DEADZONE = 0.05;
    public static final double INTAKE_PWR = 1.0;
    public static final double CLIMB_PWR = 1.0;
    public static final double AGITATOR_PWR = 1.0;
    
    public final Joystick LEFT_JOY = new Joystick(RobotMap.LEFT_JOY.value);
    public final Joystick RIGHT_JOY = new Joystick(RobotMap.RIGHT_JOY.value);
    //public final Joystick MANIPULATOR = new Joystick(RobotMap.MANIPULATOR.value);
    
    //private final JoystickButton OVERRIDE = 
    //        new JoystickButton(RIGHT_JOY, RobotMap.R_OVERRIDE.value);
    
    public OI() {
        Command cmdIntakeIn = new SpinIntake(INTAKE_PWR);
        Command cmdClimb = new Climb();
        Command cmdAgitatorCW = new SpinAgitator(AGITATOR_PWR);
        Command cmdAgitatorCCW = new SpinAgitator(-AGITATOR_PWR);
        Command cmdSpinShooter = new SpinShooter(-5000.0);
        Command cmdReverseControls = new ReverseControls();
        Command cmdOpenIndexer = new OpenIndexer();
        Command cmdCloseIndexer = new CloseIndexer();
        
        new JoystickButton(LEFT_JOY, RobotMap.L_INTAKE_IN.value).whileHeld(cmdIntakeIn);        
        new JoystickButton(LEFT_JOY, RobotMap.L_CLIMB_UP.value).whileHeld(cmdClimb);
        new JoystickButton(RIGHT_JOY, RobotMap.R_AGITATOR_CW.value).whileHeld(cmdAgitatorCW);
        new JoystickButton(RIGHT_JOY, RobotMap.R_AGITATOR_CCW.value).whileHeld(cmdAgitatorCCW);
        new JoystickButton(RIGHT_JOY, RobotMap.R_SPIN_SHOOTER.value).whileHeld(cmdSpinShooter);
        new JoystickButton(LEFT_JOY, RobotMap.L_REVERSE_CONTROLS.value).whenPressed(cmdReverseControls);
        new JoystickButton(RIGHT_JOY, RobotMap.R_OPEN_INDEXER.value).whenPressed(cmdOpenIndexer);
        new JoystickButton(RIGHT_JOY, RobotMap.R_CLOSE_INDEXER.value).whenPressed(cmdCloseIndexer);
    }
    
    public void init() {}
    
    public double getLeftJoyX() {
        double raw = LEFT_JOY.getX();
        return Math.abs(raw) < JOY_DEADZONE ? 0.0 : raw;
    }
    
    public double getLeftJoyY() {
        double raw = LEFT_JOY.getY();
        return Math.abs(raw) < JOY_DEADZONE ? 0.0 : raw;
    }
    
    public double getRightJoyX() {
        double raw = RIGHT_JOY.getX();
        return Math.abs(raw) < JOY_DEADZONE ? 0.0 : raw;
    }
    
    public double getRightJoyY() {
        double raw = RIGHT_JOY.getY();
        return Math.abs(raw) < JOY_DEADZONE ? 0.0 : raw;
    }
    
}
