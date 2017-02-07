package org.usfirst.frc.team4795.robot;


public enum RobotMap 
{
    LEFT_MOTOR_1(1),
    LEFT_MOTOR_2(2),
    RIGHT_MOTOR_1(3),
    RIGHT_MOTOR_2(4),
    // Joystick mappings
    LEFT_JOY(0),
    RIGHT_JOY(1),
    MANIPULATOR(2);
    // Left joystick mappings
    
    // Right joystick mappings
    
    // Manipulator mappings
    
    
    public final int value;

    RobotMap(int value) {
        this.value = value;
    }
}
