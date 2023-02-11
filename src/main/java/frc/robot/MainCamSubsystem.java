package frc.robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MainCamSubsystem extends SubsystemBase {
  private final SerialPort serialPort = new SerialPort(115200, SerialPort.Port.kUSB);
  /*
   * Parses April Tag Data
   * 
   * Example April Tag data:
   * Example simplified April Tag data:
   * First: 1 0.328023 -0.520075 1.085208
   * More data:
   * First: 1 0.328262 -0.520233 1.085545
   */

  private double x;

  public double x() {
    return this.x;
  }

  private double z;

  public double z() { 
    return this.z;
  }

  private String apriltag;

  public String apriltag() { 
    return this.apriltag;
  }
  //Used for the data used in TurnToTagCommand.java

  private static class TagData {
    String apriltag;
    double x; // How far right or left (I think)
    double y; // How high or low the april tag is (mostly irrelavant for our purposes)
    double z; // How far away (I think)
  }

  /*
   * S = All data
   * Tokens splits data based on ";",
   * ids splits tokens based on ":"
   * ids[0] = "TAG_FOUND", when april tag is found.
   * ids[1] = the april tag ID, example: 1, 5, 16.
   * xyz is tokens[2], it is the three floats at the end of String s
   * When an april tag is found, a string will print with the id and xyz.
   */
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
      // System.out.println("Data:" + s);

      TagData tagData = parseTagData(s);
      if (tagData != null) {
        System.out.println("First: " + tagData.apriltag + " " + tagData.x + " " + tagData.y + " " + tagData.z);
      //If there is data, then the data will be printed.
        if (tagData.x < 0) {
          System.out.println("The robot is facing the tag from the left");

          if (tagData.z < 1) {
            System.out.println("Robot is close to april tag");
          } else {
            System.out.println("The Robot is far away from the april tag");
          }
          // The println will be replaced in the future with code that moves the robot
          // accordingly. The conditions for the if state
        
          this.x = tagData.x;
          this.z = tagData.z;
          this.apriltag = tagData.apriltag;
          //Used for the data used in TurnToTagCommand.java

        }
      }
    }

    // This method will be called once per scheduler run
}
}