package org.usfirst.frc.team4795.robot;


public enum RobotMap
{
    // Motor mappings
    // PWM motor controllers
    INTAKE_MOTOR(0),
    AGITATOR_MOTOR(1),
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
    L_REVERSE_CONTROLS(3),
    L_INTAKE_IN(4),
    //L_INTAKE_OUT(6),
    L_CLIMB_UP(5),
    L_CLIMB_DOWN(6),
    // Right joystick button mappings
    //R_OVERRIDE(1), // override the manipulator controls
    //R_AUTO_SHOOT(1),
    R_AGITATOR_CW(5),
    R_AGITATOR_CCW(6),
    R_SPIN_SHOOTER(1),
    // Manipulator button mappings
    M_INTAKE_IN(1),
    M_INTAKE_OUT(10);

    public final int value;

    RobotMap(int value) {
        this.value = value;
    }
}
