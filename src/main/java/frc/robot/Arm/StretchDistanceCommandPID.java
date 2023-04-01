// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Arm;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class StretchDistanceCommandPID extends CommandBase {
  /** Creates a new ReturnArmTo0. */
  private ArmSubsystem armSubsystem;
  private PIDController StretchDistancePID = new PIDController(0.01, 0, 0);
  //Parameters are numbers in the PID formula
  private double endPosition;


  public StretchDistanceCommandPID(ArmSubsystem armSubsystem, double endPosition) {
    this.armSubsystem = armSubsystem;
    this.endPosition = endPosition;
    addRequirements(armSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    StretchDistancePID.setSetpoint(endPosition);
    StretchDistancePID.setTolerance(1);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double StretchSpeed = StretchDistancePID.calculate(armSubsystem.getArmDistance());
    //PID Calculation. Current distance is put into the PID formula, speed of arm is given.
      StretchSpeed = MathUtil.clamp(StretchSpeed, -0.2, 0.2);
      //Limits speed
      armSubsystem.stretch(StretchSpeed);
      //Tells the armSubsystem to stretch
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {armSubsystem.stopArmExtension();}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}