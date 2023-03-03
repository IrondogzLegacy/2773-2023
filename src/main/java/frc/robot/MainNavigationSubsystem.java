// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// import com.kauailabs.navx.IMUProtocol.GyroUpdate;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
//Imports :
//import edu.wpi.first.wpilibj.ADXRS450_Gyro;
//import edu.wpi.first.wpilibj.SPI.Port;
//import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//Navigation Subsystem --

public class MainNavigationSubsystem extends SubsystemBase {
  /** Creates a new MainNavigationSubsystem. */
  // Encoders :
  Encoder leftEncoder = new Encoder(0, 1);
  Encoder rightEncoder = new Encoder(2, 3, true);

  NetworkTable table = NetworkTableInstance.getDefault().getTable("Navigation");
  NetworkTableEntry leftEncoderEntry = table.getEntry("left");
  NetworkTableEntry rightEncoderEntry = table.getEntry("right");
  NetworkTableEntry directionEntry = table.getEntry("direction");
  NetworkTableEntry pitchEntry = table.getEntry("pitch");

  public MainNavigationSubsystem() {
    leftEncoder.setDistancePerPulse(0.5 * 3.14 / 2048);
    rightEncoder.setDistancePerPulse(0.5 * 3.14 / 2048);
  }

  // Gyro :
  private AHRS ahrs = new AHRS(SPI.Port.kMXP); /* Alternatives:  SPI.Port.kMXP, I2C.Port.kMXP or SerialPort.Port.kUSB */
  
  //The left/right angles
  private double prevAngleZ = 0;
  private double angleCorrectionZ = 0;
  private double currentAngleZ = 0;

  //The up/down angles
  private double angleCorrectionX = 0;
  private double currentAngleX = 0;
  private Timer calibrationTimer = null;

  @Override
  public void periodic() {
    prevAngleZ = currentAngleZ;
    currentAngleZ = ahrs.getRoll() - 90;
    if (prevAngleZ < -90 && currentAngleZ > 90) {
      angleCorrectionZ -= 360;
    }
    if (prevAngleZ > 90 && currentAngleZ < -90) {
      angleCorrectionZ += 360;
    }
    currentAngleX = 90 + ahrs.getYaw();
    // check if calibration period is in progress.
    if (calibrationTimer != null && calibrationTimer.hasElapsed(2)) {
      // set current angle as correction.
      angleCorrectionX -= currentAngleX;
      calibrationTimer.stop();
      calibrationTimer = null;
    }

    leftEncoderEntry.setDouble(leftEncoder.getDistance());
    rightEncoderEntry.setDouble(rightEncoder.getDistance());
    directionEntry.setDouble(getAngle());
    pitchEntry.setDouble(getPitch());
  }

  public double getRightEncoder()
  {return rightEncoder.getDistance();}

  public double getLeftEncoder()
  {return leftEncoder.getDistance();}

  //Reset Gyro method
public void resetGyro()
{
  ahrs.calibrate();
  // start calibration period
  calibrationTimer = new Timer();
  calibrationTimer.start();
}

  // Variables :
  public double getAngle() {
    return currentAngleZ + angleCorrectionZ;
  }

  public double getPitch()
  {
    return currentAngleX + angleCorrectionX;
  }

  public double getDistance() {return rightEncoder.getDistance();}

  public void printGyroValues() {
  System.out.println(ahrs.getRoll() + " roll");
  System.out.println(ahrs.getYaw() + " yaw");
  System.out.println(ahrs.getPitch() + " pitch");
}
}
