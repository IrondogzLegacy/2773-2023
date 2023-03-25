// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Constants;

/** Add your docs here. */
public class TestConstants {
  public static boolean IsTestRobot = true;

  // constants for the CANIDs
  public static final int leftForWheelsCANID = 11;
  public static final int rightForWheelsCANID = 23;
  public static final int leftBackWheelsCANID = 19;
  public static final int rightBackWheelsCANID = 22;

  // testbot, NOT the main bot
  public static final int ArmExtensionMotorCANID = -1; //was 17 but arm was removed
  public static final int ArmRotationMotorCANID = -1;
}
