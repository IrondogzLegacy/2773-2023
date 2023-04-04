// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Claw;

import edu.wpi.first.wpilibj2.command.CommandBase;
//import the wpilib folder for Joysticks
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;


public class LetGoCommand extends CommandBase {
  /** Creates a new LetGoCommand. */
  private final ClawSubsystem clawSubsystem;

  public LetGoCommand(ClawSubsystem clawSubsystem) {
    this.clawSubsystem = clawSubsystem;
    addRequirements(clawSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    clawSubsystem.release();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    clawSubsystem.stopClawMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
