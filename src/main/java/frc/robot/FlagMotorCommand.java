// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class FlagMotorCommand extends CommandBase {
  /** Creates a new FlagMotorCommand. */
  private final FlagMotorSubsystem flagMotorSubsystem;

  public FlagMotorCommand(FlagMotorSubsystem flagMotorSubsystem) {
    this.flagMotorSubsystem = flagMotorSubsystem;
    addRequirements(flagMotorSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    flagMotorSubsystem.startMotor();
  
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    flagMotorSubsystem.stopMotor();
  }

  
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
