package org.usfirst.frc.team4795.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * The operator interface, connects to peripherals on the driver station.
 */
public class OI {
    
    public static final double JOY_DEADZONE = 0.05;
    public static final double LEVER_DEADZONE = 0.05;
    
    public final Joystick LEFT_JOY = new Joystick(RobotMap.LEFT_JOY.value);
    public final Joystick RIGHT_JOY = new Joystick(RobotMap.RIGHT_JOY.value);
    public final Joystick MANIPULATOR = new Joystick(RobotMap.MANIPULATOR.value);
    
    public OI() {}
    
    public void init() {}
    
    /**
     * Polls the state of the lever on the arm manipulator board.
     * 
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
