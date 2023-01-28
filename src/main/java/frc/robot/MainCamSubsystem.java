package frc.robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class MainCamSubsystem extends SubsystemBase {
    private final SerialPort serialPort = new SerialPort(115200, SerialPort.Port.kUSB);
    /*
   * Needs to be tested
   * Parses April Tag Data
   * Driverstation warnings are only here to make sure that the code is working and are not necessary.
   * When testing a singular number the other System.out.println should be commented
   * out. They should be deleted when all testing is done.
   * Example April Tag data:
   * TAG_FOUND: 1;0.516811,0.116328,-0.848159,-0.149319,0.987787,0.044494,0.842977,0.103652,0.527869;-0.487307,0.097861,1.649744;0.000010
   */
  private static class TagData {
    String apriltag;
    double x;
    double y;
    double z;
  }

  private static TagData parseTagData(String s) {
    String[] tokens = s.split(";");
    String[] ids = tokens[0].split(": ");
    if (!ids[0].equals("TAG_FOUND") || tokens.length < 4) {
      return null;
    }

    String apriltag = ids[1];

    String Group1 = tokens[2];

    String[] Num = Group1.split(",");

    double XNum = Double.parseDouble(Num[0]);

    double YNum = Double.parseDouble(Num[1]);

    double ZNum = Double.parseDouble(Num[2]);

    TagData data = new TagData();
    data.x = XNum;
    data.y = YNum;
    data.z = ZNum;
    data.apriltag = apriltag;
    return data;
  }

  // reads out what the epic camera saw
  @Override
  public void periodic() {
    if (serialPort.getBytesReceived() > 0) {
      String s = serialPort.readString();
    //  System.out.println("Data:" + s);

      TagData tagData = parseTagData(s);
      if (tagData != null) {
        System.out.println("First: " + tagData.apriltag + " " + tagData.x  + " " + tagData.y + " " + tagData.z);
      }
    }

    // This method will be called once per scheduler run
  }

}
// TAG_FOUND:
// 1;0.516811,0.116328,-0.848159,-0.149319,0.987787,0.044494,0.842977,0.103652,0.527869;-0.487307,0.097861,1.649744;0.000010

