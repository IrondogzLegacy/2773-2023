// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TestNavigationSubsystem extends SubsystemBase {
  private final ADXRS450_Gyro gyro = new ADXRS450_Gyro(Port.kOnboardCS0);
  /** Creates a new TestNavigationSubsystem. */
  public TestNavigationSubsystem() {}

  @Override
  public void periodic() {
    System.out.println(gyro.getAngle());
  }
}
