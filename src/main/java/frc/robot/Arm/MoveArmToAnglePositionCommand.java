// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Arm;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import frc.robot.Constants.Constants;

public class MoveArmToAnglePositionCommand extends CommandBase {
  /** Creates a new ReturnArmTo0. */
  private ArmSubsystem armSubsystem;
  private PIDController rotateAnglePID = new PIDController(0.02, 0, 0);
  private PIDController stretchDistancePID = new PIDController(0.3, 0, 0);
  private double endAngle;
  private double endPosition;

  public MoveArmToAnglePositionCommand(ArmSubsystem armSubsystem, double endAngle, double endPosition) {
    this.armSubsystem = armSubsystem;
    this.endAngle = endAngle;
    this.endPosition = endPosition;
    addRequirements(armSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  public static CommandBase buildAngleMover(ArmSubsystem armSubsystem, double endAngle) {
    return new ProxyCommand(() -> new MoveArmToAnglePositionCommand(armSubsystem, endAngle, armSubsystem.getArmDistance()));
  }

  public static CommandBase buildPositionMover(ArmSubsystem armSubsystem, double endPosition) {
    return new ProxyCommand(() -> new MoveArmToAnglePositionCommand(armSubsystem, armSubsystem.getRotationAngle(), endPosition));
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    rotateAnglePID.setSetpoint(endAngle);
    rotateAnglePID.setTolerance(2);
    stretchDistancePID.setSetpoint(endPosition);
    stretchDistancePID.setTolerance(1);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = rotateAnglePID.calculate(armSubsystem.getRotationAngle());
    speed = MathUtil.clamp(speed, -Constants.armMaxRotationSpeed, Constants.armMaxRotationSpeed);
    armSubsystem.rotate(speed);

    double stretchSpeed = stretchDistancePID.calculate(armSubsystem.getArmDistance());
    stretchSpeed = MathUtil.clamp(stretchSpeed, -0.5, 0.55);
    armSubsystem.stretch(stretchSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    armSubsystem.rotateStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
   return stretchDistancePID.atSetpoint() && rotateAnglePID.atSetpoint();
  }
}
