// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RotationCommand extends CommandBase {
  private final MainDriveSubsystem driveSubsystem;
  private final MainNavigationSubsystem navigationSubsystem;
  private final double turnAngle;
  
  private final PIDController pid = new PIDController (MainConstants.RotateKP, MainConstants.RotateKI, MainConstants.RotateKD); 

  public RotationCommand(MainDriveSubsystem driveSubsystem, MainNavigationSubsystem navigationSubsystem,
      double turnAngle) {

    this.driveSubsystem = driveSubsystem;
    this.navigationSubsystem = navigationSubsystem;
    this.turnAngle = turnAngle;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveSubsystem);
  }

  private double startAngle;
  private double stopAngle;

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startAngle = navigationSubsystem.getAngle();
    stopAngle = startAngle + turnAngle;
    //Goal = Start + Action
    pid.setSetpoint(stopAngle);
    pid.setTolerance(2);
    //System.out.println(startAngle);
    //System.out.println(stopAngle);
   
    /*Above lines are commented due to them not being needed. 
    //Tolerance is the difference between what the actual angle is and what we want it to be.
    //If the robot turns 92 degrees instead of 90, its probaly fine. Tolerance should be lowered
    //If we need to be more precise. 
    */
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    var speed = pid.calculate(navigationSubsystem.getAngle());
    //Speed is based on angle
    //System.out.println(navigationSubsystem.getAngle());
    speed = Math.min(speed, MainConstants.MaxRotationSpeed);
    speed = Math.max(speed,-MainConstants.MaxRotationSpeed);
    //Speed is the range of rotationspeed.
    driveSubsystem.rotation(speed);
    //rotate
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveSubsystem.stopAllDrive();
    //stop
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return pid.atSetpoint();
  }
}