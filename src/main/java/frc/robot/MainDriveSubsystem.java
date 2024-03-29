
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.math.filter.SlewRateLimiter;
//
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Constants;

//import the library for the SparkMax motors
import com.revrobotics.CANSparkMax;
public class MainDriveSubsystem extends SubsystemBase {
  // private final DifferentialDrive m_robotDrive = new
  // DifferentialDrive(m_leftMotor, m_rightMotor);
  // Above is our old DifferentialDrive, now we are using MotorControllerGroups
  // specific drive motors
  private final CANSparkMax leftForMotor = new CANSparkMax(Constants.leftForWheelsCANID, Constants.DriveMotorType);
  private final CANSparkMax rightForMotor = new CANSparkMax(Constants.rightForWheelsCANID, Constants.DriveMotorType);
  private final CANSparkMax leftBackMotor = new CANSparkMax(Constants.leftBackWheelsCANID, Constants.DriveMotorType);
  private final CANSparkMax rightBackMotor = new CANSparkMax(Constants.rightBackWheelsCANID, Constants.DriveMotorType);
  private final MotorControllerGroup leftGroup = new MotorControllerGroup(leftBackMotor, leftForMotor);
  private final MotorControllerGroup rightGroup = new MotorControllerGroup(rightBackMotor, rightForMotor);
  private final DifferentialDrive mainDrive = new DifferentialDrive(leftGroup, rightGroup);

  /** Creates a new DriveSubsystem. */
  public MainDriveSubsystem() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    rightGroup.setInverted(true);
  }

  @Override
  public void periodic() {
  }

  public boolean lowSpeed;
  public boolean isMoving;

  public void changeSpeedMode() {
    if (lowSpeed = false) {
      lowSpeed = true;
    }
    if (lowSpeed = true) {
      lowSpeed = false;
    }
  }

  public void arcadeDrive(double speed, double rotation) {
    mainDrive.arcadeDrive(speed, rotation);
  }

  public void rotation(double speed) {
    mainDrive.tankDrive(speed, -speed);
  }

  // Drive with tank drive
  public void driveLine(double speed) {
    mainDrive.tankDrive(speed, speed);
  }

  public void driveSlow()
  {mainDrive.tankDrive(0.2,0.2);}

  public void stopAllDrive() {
    mainDrive.tankDrive(0, 0);
  }

  public void driveBack() {
    mainDrive.tankDrive(-0.3, -0.3);
  }
}
// end