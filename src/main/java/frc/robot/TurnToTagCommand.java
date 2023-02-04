// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurnToTagCommand extends CommandBase {
  private final MainDriveSubsystem driveSubsystem;
  private final MainCamSubsystem camSubsystem;
  /** Creates a new TurnToTag. */
  public TurnToTagCommand(MainDriveSubsystem driveSubsystem, MainCamSubsystem camSubsystem) {
    this.driveSubsystem = driveSubsystem;
    this.camSubsystem = camSubsystem;
    addRequirements(driveSubsystem);
  }

  double angle;
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    double x = camSubsystem.x();
    angle = x < 0 ? -30 : 30;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveSubsystem.rotation(angle < 0 ? -0.5 : 0.5);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveSubsystem.stopAll();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}