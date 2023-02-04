// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;
//import the wpilib folder for Joysticks
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.Timer;


public class LetGoCommand extends CommandBase {
  /** Creates a new LetGoCommand. */
  private final ClawSubsystem ReleaseSubsystem;
  private final Timer timer = new Timer();

  public LetGoCommand(ClawSubsystem ReleaseSubsystem, Joystick m_stick) {
    this.ReleaseSubsystem = ReleaseSubsystem;
    addRequirements(ReleaseSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    ReleaseSubsystem.Release();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    ReleaseSubsystem.stopClawMotor();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer.hasElapsed(1);
  }
}
