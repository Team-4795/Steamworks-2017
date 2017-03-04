package org.usfirst.frc.team4795.robot;

import org.usfirst.frc.team4795.commands.Climb;
import org.usfirst.frc.team4795.commands.ReverseControls;
import org.usfirst.frc.team4795.commands.SpinAgitator;
import org.usfirst.frc.team4795.commands.SpinIntake;
import org.usfirst.frc.team4795.commands.SpinShooter;
import org.usfirst.frc.team4795.commands.ToggleBrake;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

/**
 * The operator interface, connects to peripherals on the driver station.
 */
public class OI {
    
    public static final double JOY_DEADZONE = 0.05;
    public static final double LEVER_DEADZONE = 0.05;
    public static final double INTAKE_PWR = 1.0;
    public static final double CLIMB_PWR = 1.0;
    public static final double AGITATOR_PWR = 1.0;
    
    public final Joystick LEFT_JOY = new Joystick(RobotMap.LEFT_JOY.value);
    public final Joystick RIGHT_JOY = new Joystick(RobotMap.RIGHT_JOY.value);
    public final Joystick MANIPULATOR = new Joystick(RobotMap.MANIPULATOR.value);
    
    //private final JoystickButton OVERRIDE = 
    //        new JoystickButton(RIGHT_JOY, RobotMap.R_OVERRIDE.value);
    
    public OI() {
        Command cmdIntakeIn = new SpinIntake(INTAKE_PWR);
        Command cmdIntakeOut = new SpinIntake(-INTAKE_PWR);
        Command cmdToggleBrake = new ToggleBrake();
        //Command cmdAutoShooter = new AutoShooter();
        //Command cmdClimbUp = new Climb(CLIMB_PWR);
        //Command cmdClimbDown = new Climb(-CLIMB_PWR);
        //Command cmdClimb = new Climb();
        Command cmdAgitatorCW = new SpinAgitator(AGITATOR_PWR);
        Command cmdAgitatorCCW = new SpinAgitator(-AGITATOR_PWR);
        Command cmdSpinShooter = new SpinShooter(-5900.0);
        Command cmdReverseControls = new ReverseControls();
        
        // we want to be able to brake while a button is held,
        // but also run other commands in the meantime,
        // so just toggle the brake on both edges
        //JoystickButton butToggleBrake = new JoystickButton(LEFT_JOY, RobotMap.L_TOGGLE_BRAKE.value);
        //butToggleBrake.whenPressed(cmdToggleBrake);
        //butToggleBrake.whenReleased(cmdToggleBrake);
        
        new JoystickButton(LEFT_JOY, RobotMap.L_INTAKE_IN.value).whileHeld(cmdIntakeIn);
        //new JoystickButton(LEFT_JOY, RobotMap.L_INTAKE_OUT.value).whileHeld(cmdIntakeOut);
        
        //new JoystickButton(LEFT_JOY, RobotMap.L_CLIMB_UP.value).whileHeld(cmdClimb);
        //new JoystickButton(LEFT_JOY, RobotMap.L_CLIMB_DOWN.value).whileHeld(cmdClimbDown);
        
        new JoystickButton(RIGHT_JOY, RobotMap.R_AGITATOR_CW.value).whileHeld(cmdAgitatorCW);
        new JoystickButton(RIGHT_JOY, RobotMap.R_AGITATOR_CCW.value).whileHeld(cmdAgitatorCCW);
        
        new JoystickButton(RIGHT_JOY, RobotMap.R_SPIN_SHOOTER.value).whileHeld(cmdSpinShooter);
        
        new JoystickButton(LEFT_JOY, RobotMap.L_REVERSE_CONTROLS.value).whenPressed(cmdReverseControls);
        //new JoystickButton(RIGHT_JOY, RobotMap.R_AUTO_SHOOT.value).whileHeld(cmdAutoShooter);
    }
    
    public void init() {}
    
    public boolean isManipulatorDriver() {
    //    return !OVERRIDE.get();
        return false;
    }
    
    /**
     * Polls the state of the lever on the arm manipulator board.
     * @return The lever pos from -1.0 (fully back) to 0.0 (fully forward)
     */
    public double getManipulatorLever() {
        return MANIPULATOR.getRawAxis(0);
    }
    
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
    
    /**
     * Shares a common function between two buttons.
     * Acts upon primary unless the override is active, in which case acts upon secondary.
     */
    private class SharedButton extends Trigger {
        
        private final Trigger primary;
        private final Trigger secondary;
        private final Trigger override;
        
        public SharedButton(Trigger primary, Trigger secondary, Trigger override) {
            this.primary = primary;
            this.secondary = secondary;
            this.override = override;
        }
        
        @Override
        public boolean get() {return override.get() ? secondary.get() : primary.get();}
        
    }
    
}
