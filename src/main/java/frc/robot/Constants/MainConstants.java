// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Constants;

/** Add your docs here. */
public class MainConstants {
    public static boolean IsTestRobot = false;

    // constants for the CANIDs
    public static final int leftForWheelsCANID = 15;
    public static final int rightForWheelsCANID = 13;
    public static final int leftBackWheelsCANID = 14;
    public static final int rightBackWheelsCANID = 20;

    public static final int ArmExtensionMotorCANID = 12;
    public static final int ArmRotationMotorCANID = 16;

    public static final int PnuematicsPort1 = 7;
    public static final int PnuematicsPort2 = 6;

    public static final double ArmMaxRotationSpeed = 0.2;
    public static final double armMaxExtensionSpeed = 0.5;

    public static final double armMaxAngle = 105;
    public static final double armMaxPosition = 35;
    public static final double armMaxPositionOverride = -20;
    public static final double armMaxRotationOverride = -20;
}
