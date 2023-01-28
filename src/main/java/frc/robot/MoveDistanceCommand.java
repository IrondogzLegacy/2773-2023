// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveDistanceCommand extends CommandBase {
  /** Creates a new MoveDistanceCommand. */
  private TestEncoderSubsystem testEncoderSubsystem;
  private TestDriveSubsystem driveSubsystem;
  double StartDistance = testEncoderSubsystem.getDistance();
    double StopDistance;
  public MoveDistanceCommand() {
    addRequirements(testEncoderSubsystem, driveSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }
  
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (StartDistance > 0)
    {
      StopDistance = StartDistance + 3;
    }
    if (StartDistance <0)
    {
      StopDistance = StartDistance - 3; 
    }
    System.out.print("Start" + StartDistance);
    System.out.println("Stop" + StopDistance);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (testEncoderSubsystem.getDistance() >= StopDistance)
    {return true;}
    else return false;
   // m_leftMotor.stop();
    //m_rightMotor.stop();
  }
}
