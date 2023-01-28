// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TestEncoderSubsystem extends SubsystemBase {
  /** Creates a new TestEncoderSubsystem. */
  public TestEncoderSubsystem() {
    leftEncoder.setDistancePerPulse(.5*3.14/1024);
    rightEncoder.setDistancePerPulse(.5*3.14/1024);
  }
  Encoder leftEncoder = new Encoder(0, 1);
  Encoder rightEncoder = new Encoder(2, 3, true);
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    System.out.print("left\t" + leftEncoder.getDistance()+"\t");
    System.out.println("right\t" + rightEncoder.getDistance());
  }
}
