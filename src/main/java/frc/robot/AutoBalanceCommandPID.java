// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Constants;

public class AutoBalanceCommandPID extends CommandBase {
  private final MainDriveSubsystem driveSubsystem;
  private final MainNavigationSubsystem navigationSubsystem;
  private PIDController autoBalancePID = new PIDController(0.02, 0, 0);


  public AutoBalanceCommandPID(MainDriveSubsystem driveSubsystem, MainNavigationSubsystem navigationSubsystem) {
    this.driveSubsystem = driveSubsystem;
    this.navigationSubsystem = navigationSubsystem;
    addRequirements(driveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    autoBalancePID.setTolerance(2);

  }

  // Called every time the scheduler runs while the command is scheduled.

  @Override
  public void execute() {
    double speed = autoBalancePID.calculate(navigationSubsystem.getPitch());
    speed = MathUtil.clamp(speed, -Constants.BalanceSpeed, Constants.BalanceSpeed);
    driveSubsystem.driveLine(speed);  
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
