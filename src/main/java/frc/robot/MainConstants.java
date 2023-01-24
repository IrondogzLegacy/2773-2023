// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */

//import the motortype CANSparkMaxLowLevel
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class MainConstants {
  //constants for MainDriveSubsystem, speed + rotation factor (was for the PWMs not the Neos...)
  public static final double SpeedFactor = 0.7;
  public static final double RotationFactor = 0.7;
//constants for the CANIDs, self-explanatory
    public static final int leftForWheelsCANID = 11;
    public static final int rightForWheelsCANID = 23;
    public static final int leftBackWheelsCANID = 19;
    public static final int rightBackWheelsCANID = 22;
    public static final MotorType motorType = MotorType.kBrushless;
}

//Pseudocode updated 1/23/2023 last, MainConstants