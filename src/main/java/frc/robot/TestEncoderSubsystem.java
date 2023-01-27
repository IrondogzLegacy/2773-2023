// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TestEncoderSubsystem extends SubsystemBase {
  /** Creates a new TestEncoderSubsystem. */
  public TestEncoderSubsystem() {}
  Encoder leftEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k2X);
  Encoder rightEncoder = new Encoder(2, 3, false, Encoder.EncodingType.k2X);
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    System.out.println(leftEncoder.getDistance());
  }
}
