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
  private final double distance;

  public MoveDistanceCommand(TestEncoderSubsystem testEncoderSubsystem, TestDriveSubsystem driveSubsystem, double distance) {
    this.testEncoderSubsystem = testEncoderSubsystem;
    this.driveSubsystem = driveSubsystem;
    this.distance = distance;
    addRequirements(driveSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startDistance = testEncoderSubsystem.getDistance();

      stopDistance = startDistance + distance;

    System.out.print("Start" + startDistance);
    System.out.println("Stop" + stopDistance);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveSubsystem.genTankDrive();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveSubsystem.stopAll();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (distance >= 0 && testEncoderSubsystem.getDistance() >= stopDistance) {
      return true;
    } 
    if (distance <= 0 && testEncoderSubsystem.getDistance() <= stopDistance) {
      return true;
    } 
    return false;

  }
}
