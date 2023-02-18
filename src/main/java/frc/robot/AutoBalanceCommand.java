// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoBalanceCommand extends CommandBase {
  private final MainDriveSubsystem driveSubsystem;
  private final MainNavigationSubsystem navigationSubsystem;

  public AutoBalanceCommand(MainDriveSubsystem driveSubsystem, MainNavigationSubsystem navigationSubsystem) {
    this.driveSubsystem = driveSubsystem;
    this.navigationSubsystem = navigationSubsystem;
    addRequirements(driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.

  @Override
  public void execute() {
      double pitch = navigationSubsystem.getPitch();
      if (pitch > 1.5)
      {
        driveSubsystem.driveLine(MainConstants.BalanceSpeed);
      }
      else if (pitch < -1.5)
      {
        driveSubsystem.driveLine(-MainConstants.BalanceSpeed);
      }
      else{driveSubsystem.stopAllDrive();}
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveSubsystem.stopAllDrive();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
