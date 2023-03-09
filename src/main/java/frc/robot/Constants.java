// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

//public class Constants extends TestConstants { 
public class Constants extends MainConstants {
  // constants for MainDriveSubsystem, speed + rotation factor (was for the PWMs
  // not the Neos...)
  public static final double SpeedFactor = 0.45;
  public static final double RotationFactor = 0.4;

  // create a private class for the motorType, brushless motors
  public static final MotorType motorType = MotorType.kBrushless;

  // constant (port #) for the ClawMotorChannel
  public static final int ClawMotorCANID = -1; //10; 10 is the correct CANID but it is no longer on the bot
  // constant for the PickUpSpeed (motor for extending / closing the claw)
  public static final double ClawMotorSpeed = 0.05; // 0.25;
  // constant for the ReleaseSpeed
  public static final double ReleaseSpeed = -0.05; // -0.5;
  // constant for the default TankDrive
  public static final double genTankSpeed = 0.3;

  // constant for ArmMotor Speed
  public static final double ArmMotorSpeed = 0.4;
  public static final double ArmRotationSpeed = 0.4;
  public static final double ArmEncoderRatio = 0.04 * 7./8. * 3.14;

  public static final double BalanceSpeed = 0.3;

  // PID Constants (kP)
  public static final double DriveKP = 0.5;
  public static final double ArmKP = 0.042;

  public static final double RotateKP = 0.008;
  public static final double RotateKI = 0.005;
  public static final double RotateKD = 0;
  public static final double MaxRotationSpeed = 0.5;
  public static final double MinRotationSpeed = 0.5;

  public static final double AutoBrakingSpeed = 0.4;

  //Constants for the potentiometer values in the arm (using map function)
  public static final double ArmBottomVoltage = 4.39;
  public static final double ArmTopVoltage = 2.28;
  public static final double ArmMinDeg = -83;
  public static final double ArmMaxDeg = 20;
}
// Pseudocode updated 1/23/2023 last, MainConstants