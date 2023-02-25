// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class TurnToTagCommand extends CommandBase {
  private final MainDriveSubsystem driveSubsystem;
  private final MainCamSubsystem camSubsystem;
  private MainNavigationSubsystem navigationSubsystem;
  /** Creates a new TurnToTag. */
  public TurnToTagCommand(MainDriveSubsystem driveSubsystem, MainCamSubsystem camSubsystem, MainNavigationSubsystem navigationSubsystem, RotationCommand rotate90) {
    this.driveSubsystem = driveSubsystem;
    this.camSubsystem = camSubsystem;
    this.navigationSubsystem = navigationSubsystem;
  }
  double turnAngle;
  double angleToTag;
  double dis;
  double z;
  double x;
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    x = camSubsystem.x();
    z = camSubsystem.z();
    String apriltag = camSubsystem.apriltag();
    //angle = x < 0 ? -30 : 30;
    // turnAngle = x;
    RotationCommand rotationCommand = new RotationCommand(driveSubsystem, navigationSubsystem, angleToTag);
    MoveDistanceCommand moveDistanceCommand = new MoveDistanceCommand(driveSubsystem, navigationSubsystem, distanceToTag()-2);
    rotationCommand.andThen(moveDistanceCommand).schedule();
  }
  public double distanceToTag()
  {
     var distanceToTag = (Math.sqrt(x * x + z * z));
    //For movement, the robot will turn angleToTag, and then move distanceToTag
    return distanceToTag;
  }

  public double angletoTag() 
  {
    angleToTag = Math.atan2(z, x) / Math.PI * 180;
    return angleToTag;
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
    return true;
  }
}
