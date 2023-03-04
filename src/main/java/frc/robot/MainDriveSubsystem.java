
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


//import the library for the SparkMax motors
import com.revrobotics.CANSparkMax;

public class MainDriveSubsystem extends SubsystemBase {
  // private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);
//Above is our old DifferentialDrive, now we are using MotorControllerGroups
  // specific drive motors
  private final CANSparkMax leftForMotor = new CANSparkMax(Constants.leftForWheelsCANID, Constants.motorType);
  private final CANSparkMax rightForMotor = new CANSparkMax(Constants.rightForWheelsCANID, Constants.motorType);
  private final CANSparkMax leftBackMotor = new CANSparkMax(Constants.leftBackWheelsCANID, Constants.motorType);
  private final CANSparkMax rightBackMotor = new CANSparkMax(Constants.rightBackWheelsCANID, Constants.motorType);
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

  // Creates a SlewRateLimiter that limits the rate of change of the signal to 0.5 units per second
  SlewRateLimiter filter = new SlewRateLimiter(0.5);
  LinearFilter linfilter = LinearFilter.singlePoleIIR(0.1, 0.02);

  public void arcadeDrive(double speed, double rotation) {
    // Drive with arcade drive.
    // That means that the Y axis drives forward and backward, and the X turns left and right.
   
    mainDrive.arcadeDrive(// Calculates the next value of the output
    linfilter.calculate(speed * Constants.SpeedFactor), rotation * Constants.RotationFactor);

  }
  public void rotation(double speed) {
    mainDrive.tankDrive(speed, -speed);

  }
  // Drive with tank drive
  public void driveLine (double speed)
  {
    mainDrive.tankDrive(speed, speed);
  }
  public void stopAllDrive()
  {
    mainDrive.tankDrive(0,0);
  }
}
//end