package org.usfirst.frc.team4795.robot;


public enum RobotMap 
{
    // Motor mappings
    // PWM motor controllers
    INTAKE_MOTOR(0),
    // CAN motor controllers
    LEFT_MOTOR_1(1),
    LEFT_MOTOR_2(2),
    RIGHT_MOTOR_1(3),
    RIGHT_MOTOR_2(4),
    SHOOTER_MOTOR(5),
    CLIMBER_MOTOR(6),
    // Joystick mappings
    LEFT_JOY(0),
    RIGHT_JOY(1),
    MANIPULATOR(2),
    // Left joystick button mappings
    L_TOGGLE_BRAKE(1),
    L_INTAKE_IN(4),
    L_INTAKE_OUT(6),
    // Right joystick button mappings
    R_OVERRIDE(1), // override the manipulator controls
    // Manipulator button mappings
    M_INTAKE_IN(9),
    M_INTAKE_OUT(10);
    
    public final int value;

    RobotMap(int value) {
        this.value = value;
    }
}
