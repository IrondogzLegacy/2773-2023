// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Arm;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Constants;
import java.util.function.Supplier;

public class MoveArmToAnglePositionCommand extends CommandBase {
  /** Creates a new ReturnArmTo0. */
  private ArmSubsystem armSubsystem;
  private PIDController rotateAnglePID = new PIDController(0.03, 0, 0);
  private PIDController stretchDistancePID = new PIDController(0.3, 0, 0);
  private Supplier<Double> endAngle;
  private Supplier<Double> endPosition;
  private boolean dontStop;

  public MoveArmToAnglePositionCommand(ArmSubsystem armSubsystem, double endAngle, double endPosition) {
    this(armSubsystem, () -> endAngle, () -> endPosition, false);
  }

  public MoveArmToAnglePositionCommand(ArmSubsystem armSubsystem, double endAngle, double endPosition,
      boolean dontStop) {
    this(armSubsystem, () -> endAngle, () -> endPosition, dontStop);
  }

  public MoveArmToAnglePositionCommand(ArmSubsystem armSubsystem, Supplier<Double> endAngle,
      Supplier<Double> endPosition, boolean dontStop) {
    this.armSubsystem = armSubsystem;
    this.endAngle = endAngle;
    this.endPosition = endPosition;
    this.dontStop = dontStop;
    addRequirements(armSubsystem);
  }

  public static MoveArmToAnglePositionCommand buildAngleMover(ArmSubsystem armSubsystem, double endAngle) {
    return new MoveArmToAnglePositionCommand(armSubsystem, () -> endAngle, () -> armSubsystem.getArmDistance(), false);
  }

  public static MoveArmToAnglePositionCommand buildPositionMover(ArmSubsystem armSubsystem, double endPosition) {
    return new MoveArmToAnglePositionCommand(armSubsystem, armSubsystem::getRotationAngle, () -> endPosition, false);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    rotateAnglePID.setSetpoint(endAngle.get());
    rotateAnglePID.setTolerance(2);
    stretchDistancePID.setSetpoint(endPosition.get());
    stretchDistancePID.setTolerance(1);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = rotateAnglePID.calculate(armSubsystem.getRotationAngle());
    speed = MathUtil.clamp(speed, -Constants.ArmMaxRotationSpeed, Constants.ArmMaxRotationSpeed);
    armSubsystem.rotate(speed);

    double stretchSpeed = stretchDistancePID.calculate(armSubsystem.getArmDistance());
    stretchSpeed = MathUtil.clamp(stretchSpeed, -0.5, 0.5);
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
    if (dontStop) {
      return false;
    }
    return stretchDistancePID.atSetpoint() && rotateAnglePID.atSetpoint();
  }
}
