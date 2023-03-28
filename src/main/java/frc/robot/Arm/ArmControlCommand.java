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

  private PIDController rotateAnglePID = new PIDController(0.01, 0, 0);

  private PIDController StretchDistancePID = new PIDController(0.01, 0, 0);

  /** Creates a new ArmControlCommand. */
  public ArmControlCommand(ArmSubsystem armSubsystem, XboxController armStick) {
    addRequirements(armSubsystem);
    this.armSubsystem = armSubsystem;
    this.armStick = armStick;
  }


  double holdAt;
  double endPosition;

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    holdAt = armSubsystem.getRotationAngle();
    endPosition = armSubsystem.getArmDistance();
    rotateAnglePID.setSetpoint(holdAt);
    StretchDistancePID.setSetpoint(endPosition);
    StretchDistancePID.setTolerance(1);
    rotateAnglePID.setTolerance(5);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      holdAt += 20*armStick.getLeftY();
      holdAt = MathUtil.clamp(holdAt, -5, 105);
      rotateAnglePID.setSetpoint(holdAt); 
      double speed = rotateAnglePID.calculate(armSubsystem.getRotationAngle());
      speed = MathUtil.clamp(speed, -Constants.armMaxRotationSpeed, Constants.armMaxRotationSpeed);
      armSubsystem.rotate(speed);
      
      endPosition += 5*armStick.getRightY();
      endPosition = MathUtil.clamp(endPosition, -5, 26);
      StretchDistancePID.setSetpoint(endPosition); 
    double StretchSpeed = StretchDistancePID.calculate(armSubsystem.getArmDistance());
      StretchSpeed = MathUtil.clamp(StretchSpeed, -0.2, 0.2);
      armSubsystem.stretch(StretchSpeed);
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
