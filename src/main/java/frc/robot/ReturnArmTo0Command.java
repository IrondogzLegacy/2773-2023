// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ReturnArmTo0Command extends CommandBase {
  /** Creates a new ReturnArmTo0. */
  private ArmSubsystem armSubsystem;
  public ReturnArmTo0Command(ArmSubsystem armSubsystem) {
    this.armSubsystem = armSubsystem;
    addRequirements(armSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  private double startingArmAngle;
  private double currentArmAngle;
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startingArmAngle = armSubsystem.getRotationAngle();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentArmAngle = armSubsystem.getRotationAngle();
    if (startingArmAngle < -1) {
      while (currentArmAngle < -1)
      {armSubsystem.rotateUp();}
      armSubsystem.rotateStop();
    }
    if (startingArmAngle > 1) {
      while (currentArmAngle > 1)
      {armSubsystem.rotateDown();}
      armSubsystem.rotateStop();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    armSubsystem.rotateStop();
    return true;
  }
}
