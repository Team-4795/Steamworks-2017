package org.usfirst.frc.team4795.robot;


public enum RobotMap 
{
    LEFT_MOTOR_1(1),
    LEFT_MOTOR_2(2),
    RIGHT_MOTOR_1(3),
    RIGHT_MOTOR_2(4),
    ARM_MOTOR(5),
    // Joystick mappings
    LEFT_JOY(0),
    RIGHT_JOY(1),
    MANIPULATOR(2),
    // Left joystick mappings
    L_INTAKE_IN(4),
    L_INTAKE_OUT(6),
    L_TOGGLE_CAM(5),
    // Right joystick mappings
    R_ARM_UP(3),
    R_ARM_DOWN(5),
    R_OVERRIDE(1), // override the manipulator
    // Manipulator mappings
    M_ARM_DOWN(1),
    M_ARM_UP(2),
    M_INTAKE_IN(9),
    M_INTAKE_OUT(10),
    M_CALIBRATE(3);
    
    public final int value;

    RobotMap(int value) {
        this.value = value;
    }
}
