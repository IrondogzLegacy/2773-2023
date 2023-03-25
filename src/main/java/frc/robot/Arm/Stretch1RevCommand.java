// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Arm;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class Stretch1RevCommand extends CommandBase {
  private final ArmSubsystem armSubsystem;
  private final double stretchDistance;
  /** Creates a new ArmCommand. */
  public Stretch1RevCommand(ArmSubsystem armSubsystem, double stretchDistance) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.armSubsystem = armSubsystem;
    this.stretchDistance = stretchDistance;
    addRequirements(armSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    armSubsystem.stretchLength(stretchDistance);
    }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    armSubsystem.stopArmExtension();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
