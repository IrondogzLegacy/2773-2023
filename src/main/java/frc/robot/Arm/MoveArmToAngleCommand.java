// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Arm;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveArmToAngleCommand extends CommandBase {
  /** Creates a new ReturnArmTo0. */
  private ArmSubsystem armSubsystem;
  private PIDController rotateAnglePID = new PIDController(0.01, 0, 0);
  private double endAngle;


  public MoveArmToAngleCommand(ArmSubsystem armSubsystem, double endAngle) {
    this.armSubsystem = armSubsystem;
    this.endAngle = endAngle;
    addRequirements(armSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    rotateAnglePID.setSetpoint(endAngle);
    rotateAnglePID.setTolerance(5);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double rotateSpeed = rotateAnglePID.calculate(armSubsystem.getRotationAngle());
      rotateSpeed = MathUtil.clamp(rotateSpeed, -0.2, 0.2);
      armSubsystem.rotate(rotateSpeed);
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
