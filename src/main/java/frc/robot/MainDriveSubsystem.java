// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
//
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import javax.management.loading.PrivateClassLoader;

//import the library for the SparkMax motors
import com.revrobotics.CANSparkMax;

public class MainDriveSubsystem extends SubsystemBase {
  /**
   *
   */
  


  // private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);

  // specific drive motors
  private final CANSparkMax leftForMotor = new CANSparkMax(MainConstants.leftForWheelsCANID, MainConstants.motorType);
  private final CANSparkMax rightForMotor = new CANSparkMax(MainConstants.rightForWheelsCANID, MainConstants.motorType);
  private final CANSparkMax leftBackMotor = new CANSparkMax(MainConstants.leftBackWheelsCANID, MainConstants.motorType);
  private final CANSparkMax rightBackMotor = new CANSparkMax(MainConstants.rightBackWheelsCANID, MainConstants.motorType);
  private final MotorControllerGroup leftGroup = new MotorControllerGroup(leftBackMotor, leftForMotor);
  private final MotorControllerGroup rightGroup = new MotorControllerGroup(rightBackMotor, rightForMotor);

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

  public void arcadeDrive(double speed, double rotation) {
    // Drive with arcade drive.
    // That means that the Y axis drives forward and backward, and the X turns left and right.
    //m_robotDrive.arcadeDrive(-m_stick.getY() * 0.7, -m_stick.getX() * 0.7);
    // m_robotDrive.arcadeDrive(speed * MainConstants.SpeedFactor, rotation * MainConstants.RotationFactor);
    leftGroup.set(speed*0.1);
  }
}
//end