// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class TestCam extends SubsystemBase {
  private final SerialPort serialPort = new SerialPort(115200, SerialPort.Port.kUSB);
  /** Creates a new TestCam. */
  public TestCam() {

  }

  @Override
  public void periodic() {
    String s = serialPort.readString();
    System.out.println(s);
    // This method will be called once per scheduler run
  }
}
