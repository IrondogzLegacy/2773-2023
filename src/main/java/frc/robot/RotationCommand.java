// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class RotationCommand extends CommandBase {
  private final MainDriveSubsystem driveSubsystem;
  private final MainNavigationSubsystem navigationSubsystem;
  private final double turnAngle;

  public RotationCommand(MainDriveSubsystem driveSubsystem, MainNavigationSubsystem navigationSubsystem,
      double turnAngle) {
    this.driveSubsystem = driveSubsystem;
    this.navigationSubsystem = navigationSubsystem;
    this.turnAngle = turnAngle;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveSubsystem);
  }

  private double startAngle;
  private double stopAngle;

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startAngle = navigationSubsystem.getAngle();
    stopAngle = startAngle + turnAngle;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (turnAngle >= 0) {
      driveSubsystem.rotation(MainConstants.RotationFactor);
    } else {
      driveSubsystem.rotation(-MainConstants.RotationFactor);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveSubsystem.stopAllDrive();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (turnAngle >= 0 && stopAngle <= navigationSubsystem.getAngle()) {
      return true;
    } else if (turnAngle < 0 && stopAngle >= navigationSubsystem.getAngle()) {
      return true;
    } 
    return false;
  }
}
