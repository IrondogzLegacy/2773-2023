// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Constants;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

//public class Constants extends TestConstants { 
public class Constants extends MainConstants {
  // constants for MainDriveSubsystem, speed + rotation factor (was for the PWMs
  // not the Neos...)
  public static final double SpeedFactor = 0.6;
  public static final double SpeedFactorLow = 0.3;
  public static final double SpeedFactorHigh = 0.7;
  public static final double RotationFactor = 0.4;
  public static final double RotationFactorLow = 0.3;
  public static final double RotationFactorHigh = 0.5;

  // create a private class for the motorType, brushless motors
  public static final MotorType DriveMotorType = MotorType.kBrushless;

  // constant (port #) for the ClawMotorChannel
  public static final int ClawMotorCANID = 21; //10; // 10; 10 is the correct CANID but it is no longer on the bot
  // constant for the PickUpSpeed (motor for extending / closing the claw)
  public static final double ClawMotorSpeed = -0.5;
  // constant for the ReleaseSpeed
  public static final double ReleaseSpeed = 0.8; // -0.5;
  // constant for the default TankDrive
  public static final double GeneralTankSpeed = 0.3;

  // constant for ArmMotor Speed
  public static final double ArmMotorSpeed = 0.5;
  public static final double ArmRotationSpeed = 0.1;
  public static final double ArmEncoderRatio = 0.04 * 9. / 8. * 3.14;

  public static final double BalanceSpeed = 0.3;
  public static final double DpadSpeed = 0.3;
  public static final double SpeedIncrease = 0.9;

  // PID Constants (kP)
  public static final double DriveKP = 0.5;
  public static final double ArmKP = 0.042;

  public static final double RotateKP = 0.008;
  public static final double RotateKI = 0.005;
  public static final double RotateKD = 0;
  public static final double MaxRotationSpeed = 0.5;
  public static final double MinRotationSpeed = 0.5;

  public static final double AutoBrakingSpeed = 0.3;

  // Constants for the potentiometer values in the arm (using map function)
  public static final double ArmBottomVoltage = 4.16;
  public static final double ArmTopVoltage = 2.24;
  public static final double ArmMinDeg = -83;
  public static final double ArmMaxDeg = 20;

  //Constants for the fully retracted & rotated-back position, stowed away
  public static final double StowedPosition = 0;
  public static final double StowedAngle = 0;

  //Constants for the safe position, to make sure that the arm does not run into anything
  public static final double SafePosition = 0;
  public static final double SafeAngle = 50;

  //Constants for the position & angle we need for the first level
  public static final double FirstPosition = 10;
  public static final double FirstAngle = 50;

  //Constants for the position & angle we need for the second level
  public static final double SecondPosition = 25;
  public static final double SecondAngle = 75;

  //Constants for the position & angle we need for the third level
  public static final double ThirdPosition = 33;
  public static final double ThirdAngle = 97;

  public static final double ControllerDeadzone = 0.01;
}
// Pseudocode updated 1/23/2023 last, MainConstants