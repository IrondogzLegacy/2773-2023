// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class RotateDownCommand extends CommandBase {
  private static final ArmSubsystem armSubsytem = new ArmSubsystem();

  public RotateDownCommand(ArmSubsystem armSubsystem) {}

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {armSubsytem.rotateDown();}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {armSubsytem.rotateStop();}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
