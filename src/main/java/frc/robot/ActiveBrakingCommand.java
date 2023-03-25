// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Constants;

public class ActiveBrakingCommand extends CommandBase {
  private final MainDriveSubsystem driveSubsystem;
  private final MainNavigationSubsystem navigationSubsystem;

  public ActiveBrakingCommand(MainDriveSubsystem driveSubsystem, MainNavigationSubsystem navigationSubsystem) {
    this.driveSubsystem = driveSubsystem;
    this.navigationSubsystem = navigationSubsystem;
    addRequirements(driveSubsystem);
  }

  double startRightEncoder;
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startRightEncoder = navigationSubsystem.getRightEncoder();
    System.out.println("Start-right" + startRightEncoder);
  }
  private static final double ErrorDistance = 1./10;
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    var currentRightEncoder = navigationSubsystem.getRightEncoder();
    double deltaRightEncoder = currentRightEncoder - startRightEncoder;

    //If the change in distance is greater than ActionDistance, the robot will move back. 
    //
    //deadzone is 2 inches
    if (deltaRightEncoder > ErrorDistance)
    {
      driveSubsystem.driveLine(-Constants.AutoBrakingSpeed);
      System.out.println("Delta-right" + deltaRightEncoder);
    }
    //deadzone is 2 inches
    if (deltaRightEncoder < -ErrorDistance)
    {
      driveSubsystem.driveLine(Constants.AutoBrakingSpeed);
      System.out.println("Delta-right" + deltaRightEncoder);
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
