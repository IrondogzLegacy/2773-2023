// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Arm;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArmControlCommand extends CommandBase {
  private final XboxController armStick;
  private final ArmSubsystem armSubsystem;

  private PIDController holdAnglePID = new PIDController(0.01, 0, 0);
  private boolean started = false;

  /** Creates a new ArmControlCommand. */
  public ArmControlCommand(ArmSubsystem armSubsystem, XboxController armStick) {
    addRequirements(armSubsystem);
    this.armSubsystem = armSubsystem;
    this.armStick = armStick;
  }

  public void ensureStarted() {
    started = true;
  }

  double holdAt;
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    holdAt = armSubsystem.getRotationAngle();
    holdAnglePID.setSetpoint(holdAt);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (started) {
      double speed = holdAnglePID.calculate(armSubsystem.getRotationAngle());
      speed = MathUtil.clamp(speed, -0.2, 0.2);
      armSubsystem.rotate(speed);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
