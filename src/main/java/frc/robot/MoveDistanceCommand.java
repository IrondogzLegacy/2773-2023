// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveDistanceCommand extends CommandBase {
  /** Creates a new MoveDistanceCommand. */
  private final TestEncoderSubsystem testEncoderSubsystem;
  private final TestDriveSubsystem driveSubsystem;
  double startDistance;
  double stopDistance;

  public MoveDistanceCommand(TestEncoderSubsystem testEncoderSubsystem, TestDriveSubsystem driveSubsystem) {
    this.testEncoderSubsystem = testEncoderSubsystem;
    this.driveSubsystem = driveSubsystem;
    addRequirements(driveSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startDistance = testEncoderSubsystem.getDistance();

    if (startDistance >= 0) {
      stopDistance = startDistance + 3;
    }
    if (startDistance < 0) {
      stopDistance = startDistance - 3;
    }
    System.out.print("Start" + startDistance);
    System.out.println("Stop" + stopDistance);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (testEncoderSubsystem.getDistance() >= stopDistance) {
      return true;
    } else
      return false;
    // m_leftMotor.stop();
    // m_rightMotor.stop();
  }
}
