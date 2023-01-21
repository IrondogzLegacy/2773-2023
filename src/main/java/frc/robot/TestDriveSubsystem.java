// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TestDriveSubsystem extends SubsystemBase {
  private final PWMSparkMax m_leftMotor = new PWMSparkMax(1);
  private final PWMSparkMax m_rightMotor = new PWMSparkMax(0);

  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);

  /** Creates a new DriveSubsystem. */
  public TestDriveSubsystem() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightMotor.setInverted(true);
  }

  @Override
  public void periodic() {
  }

  public void arcadeDrive(double speed, double rotation) {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    //m_robotDrive.arcadeDrive(-m_stick.getY() * 0.7, -m_stick.getX() * 0.7);
    m_robotDrive.arcadeDrive(speed * TestConstants.SpeedFactor, rotation * TestConstants.RotationFactor);
  }
}
