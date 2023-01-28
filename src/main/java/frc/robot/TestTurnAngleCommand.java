// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class TestTurnAngleCommand extends CommandBase {
  private final TestDriveSubsystem driveSubsystem;
  private final TestNavigationSubsystem navigationSubsystem;
  /** Creates a new TestTurnAngleCommand. */
  public TestTurnAngleCommand(TestDriveSubsystem driveSubsystem, TestNavigationSubsystem navigationSubsystem) {
    this.driveSubsystem = driveSubsystem;
    this.navigationSubsystem = navigationSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveSubsystem);
  }

  private double startAngle;
  private double stopAngle;


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startAngle = navigationSubsystem.getAngle();
    stopAngle = startAngle + 30;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveSubsystem.rotation(0.75);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveSubsystem.stopAll();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(stopAngle <= navigationSubsystem.getAngle())
    {
      return true;
    }
    else return false;
  }
}
