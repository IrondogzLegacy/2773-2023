// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
//importing differential drive commands
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//importing motorcontrol commands for PMWSparkMax's
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
//importing the subsystem base commands
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//the class TestDriveSubsystem is an extension of SubsystemBase
public class TestDriveSubsystem extends SubsystemBase {
  //within this class, the Spark motors for the left / right motors of testbot are defined below, as well as what channel they are imported into
  private final PWMSparkMax m_leftMotor = new PWMSparkMax(1);
  private final PWMSparkMax m_rightMotor = new PWMSparkMax(0);
  //creating a differential drive with the two above motors (again private final meaning it cannot be changed and stays within this class)
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);

  //within this testsubsystem, a command that runs every time the system boots up ("forever" commands in Scratch :P )
  public TestDriveSubsystem() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightMotor.setInverted(true);
  }
//makes an error message popup if this function fails (void => function)
  @Override
  public void periodic() {
  }

  //Creates a function for arcadeDrive, what we're using for testbot currently
  public void arcadeDrive(double speed, double rotation) {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    //m_robotDrive.arcadeDrive(-m_stick.getY() * 0.7, -m_stick.getX() * 0.7); //this without the speedfactor / rotationfactor, magic numbers (0.7) should not be in code but instead in Constants file
    m_robotDrive.arcadeDrive(speed * TestConstants.SpeedFactor, rotation * TestConstants.RotationFactor);
  }
  public void rotation(double speed) {
    m_robotDrive.tankDrive(speed, -speed);

  }
  public void genTankDrive ()
  {
    m_robotDrive.tankDrive(TestConstants.genTankLSpeed, TestConstants.genTankRSpeed);
  }
  public void stopAll()
  {
    m_robotDrive.stopMotor();
  }
}

//Pseudocode last updated 1/23/2023, TestDriveSubsystem