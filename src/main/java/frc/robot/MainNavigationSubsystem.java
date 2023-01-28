// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//Imports :
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//Navigation Subsystem --

public class MainNavigationSubsystem extends SubsystemBase {
  /** Creates a new MainNavigationSubsystem. */
  //Encoders :
    public MainNavigationSubsystem() {
      leftEncoder.setDistancePerPulse(.5*3.14/1024);
      rightEncoder.setDistancePerPulse(.5*3.14/1024);
    }
    Encoder leftEncoder = new Encoder(0, 1);
    Encoder rightEncoder = new Encoder(2, 3, true);

  //Gyro :

    private final ADXRS450_Gyro gyro = new ADXRS450_Gyro(Port.kOnboardCS0);
  @Override
    public void periodic() {
    System.out.print("left\t" + leftEncoder.getDistance()+"\t");
    System.out.println("right\t" + rightEncoder.getDistance());
    System.out.println(gyro.getAngle());
    }

  //Ending :

  public double getAngle() {return gyro.getAngle();}
  public double getDistance() {return rightEncoder.getDistance();}
}
