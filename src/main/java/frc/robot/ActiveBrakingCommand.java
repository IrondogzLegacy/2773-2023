// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ActiveBrakingCommand extends CommandBase {
  private final MainDriveSubsystem driveSubsystem;
  private final MainNavigationSubsystem navigationSubsystem;

  public ActiveBrakingCommand(MainDriveSubsystem driveSubsystem, MainNavigationSubsystem navigationSubsystem) {
    this.driveSubsystem = driveSubsystem;
    this.navigationSubsystem = navigationSubsystem;
    addRequirements(driveSubsystem);
  }

  double startRightEncoder;
  double startLeftEncoder;
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startRightEncoder = navigationSubsystem.getRightEncoder();
    System.out.println("Start-right" + startRightEncoder);
    System.out.println("Start-left" + startLeftEncoder);
    startLeftEncoder = navigationSubsystem.getLeftEncoder();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    var currentRightEncoder = navigationSubsystem.getRightEncoder();
    var currentLeftEncoder = navigationSubsystem.getLeftEncoder();
    double deltaRightEncoder = currentRightEncoder - startRightEncoder;
    double deltaLeftEncoder = currentLeftEncoder - startLeftEncoder;
    //deadzone is 2 inches
    if (deltaRightEncoder > 1./6. && deltaLeftEncoder > 1./6.)
    {
      driveSubsystem.driveLine(-MainConstants.AutoBrakingSpeed);
      System.out.println("Delta-right" + deltaRightEncoder);
      System.out.println("Delta-left" + deltaRightEncoder);
    }
    //deadzone is 2 inches
    if (deltaRightEncoder < -1./6. && deltaLeftEncoder < -1./6.)
    {
      driveSubsystem.driveLine(MainConstants.AutoBrakingSpeed);
      System.out.println("Delta-right" + deltaRightEncoder);
      System.out.println("Delta-left" + deltaRightEncoder);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
