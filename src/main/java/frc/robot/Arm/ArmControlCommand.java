// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Arm;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Constants;

public class ArmControlCommand extends CommandBase {
  private final XboxController armStick;
  private final ArmSubsystem armSubsystem;

  private PIDController rotateAnglePID = new PIDController(0.02, 0, 0);

  private PIDController StretchDistancePID = new PIDController(0.4, 0, 0);

  /** Creates a new ArmControlCommand. */
  public ArmControlCommand(ArmSubsystem armSubsystem, XboxController armStick) {
    addRequirements(armSubsystem);
    this.armSubsystem = armSubsystem;
    this.armStick = armStick;
  }

  double holdAt;
  double endPosition;

  private void resetSetpoints() {
    holdAt = armSubsystem.getRotationAngle();
    endPosition = armSubsystem.getArmDistance();
    rotateAnglePID.setSetpoint(holdAt);
    StretchDistancePID.setSetpoint(endPosition);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    StretchDistancePID.setTolerance(1);
    rotateAnglePID.setTolerance(5);
    resetSetpoints();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Check if two buttons pressed to reset zero.
    boolean resetZero = armStick.getRawButton(7) && armStick.getRawButton(8);
    if (resetZero) {
      armSubsystem.resetArmEncoders();
      resetSetpoints();
    }

    boolean overrideZero = armStick.getRawButton(7);
    double minHoldAngle = overrideZero ? Constants.armMaxRotationOverride : 0;
    double maxHoldAngle = Constants.armMaxAngle;
    double minDistance = overrideZero ? Constants.armMaxPositionOverride : 0;
    double maxDistance = Constants.armMaxPosition;
    holdAt += -0.8 * MathUtil.applyDeadband(armStick.getLeftY(), Constants.ControllerDeadzone);
    holdAt = MathUtil.clamp(holdAt, minHoldAngle, maxHoldAngle);
    rotateAnglePID.setSetpoint(holdAt);
    double speed = rotateAnglePID.calculate(armSubsystem.getRotationAngle());
    speed = MathUtil.clamp(speed, -Constants.ArmMaxRotationSpeed, Constants.ArmMaxRotationSpeed);
    armSubsystem.rotate(speed);

    endPosition += -0.3 * MathUtil.applyDeadband(armStick.getRightY(), Constants.ControllerDeadzone);
    endPosition = MathUtil.clamp(endPosition, minDistance, maxDistance);
    StretchDistancePID.setSetpoint(endPosition);
    double stretchSpeed = StretchDistancePID.calculate(armSubsystem.getArmDistance());
    stretchSpeed = MathUtil.clamp(stretchSpeed, -Constants.armMaxExtensionSpeed, Constants.armMaxExtensionSpeed);
    armSubsystem.stretch(stretchSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    armSubsystem.rotateStop();
    armSubsystem.stopArmExtension();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
