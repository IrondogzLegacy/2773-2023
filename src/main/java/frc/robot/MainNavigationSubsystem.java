// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.kauailabs.navx.IMUProtocol.GyroUpdate;
import com.kauailabs.navx.frc.AHRS;

//Imports :
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//Navigation Subsystem --

public class MainNavigationSubsystem extends SubsystemBase {
  /** Creates a new MainNavigationSubsystem. */
  // Encoders :
  Encoder leftEncoder = new Encoder(0, 1);
  Encoder rightEncoder = new Encoder(2, 3, true);

  public MainNavigationSubsystem() {
    leftEncoder.setDistancePerPulse(0.5 * 3.14 / 2048);
    rightEncoder.setDistancePerPulse(0.5 * 3.14 / 2048);
  }

  // Gyro :

  // private final ADXRS450_Gyro gyro = new ADXRS450_Gyro(Port.kOnboardCS0);
  private AHRS ahrs = new AHRS(SPI.Port.kMXP); /* Alternatives:  SPI.Port.kMXP, I2C.Port.kMXP or SerialPort.Port.kUSB */
   
  private double prevAngle = 0;
  private double angleCorrection = 0;

  @Override
  public void periodic() {
    double currentAngle = ahrs.getYaw();
    if (prevAngle < -90 && currentAngle > 90) {
      angleCorrection -= 360;
    }
    if (prevAngle > 90 && currentAngle < -90) {
      angleCorrection += 360;
    }
    prevAngle = currentAngle;
    //System.out.println("left\t" + ahrs.getYaw() + "\t");
    // System.out.print("right\t" + rightEncoder.getDistance() + "\t");
    // System.out.println("a\t" + ahrs.getRawGyroZ());
  }

  // Variables :
  public double getAngle() {
    return ahrs.getYaw() + angleCorrection;
  }

  public double getPitch()
  {
    return ahrs.getPitch();
  }

  public double getDistance() {
    return rightEncoder.getDistance();
  }
}
