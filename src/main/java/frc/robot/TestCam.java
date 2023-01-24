// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class TestCam extends SubsystemBase {
  //cannot use without a camera connected, will not create SerialPort
  private final SerialPort serialPort = new SerialPort(115200, SerialPort.Port.kUSB1);
  /** Creates a new TestCam. */
  public TestCam() {

  }
//reads out what the epic camera saw
  @Override
  public void periodic() {
    if (serialPort.getBytesReceived() > 0){
    String s = serialPort.readString();
    DriverStation.reportWarning("Data:" + s, false);
    System.out.println(s);
    String[] tokens = s.split(";");
    String[] ids = tokens[0].split(": ");
    if (ids[0] == "TAG_FOUND") {    
    
    String apriltag = ids[1];
    DriverStation.reportWarning("April Tag ID:" + apriltag, false);   
    
    String Group1 = tokens[2];
    DriverStation.reportWarning("First Group:" + Group1, false);


  
    }

    // This method will be called once per scheduler run
  }
}
}
//TAG_FOUND: 1;0.516811,0.116328,-0.848159,-0.149319,0.987787,0.044494,0.842977,0.103652,0.527869;-0.487307,0.097861,1.649744;0.000010



