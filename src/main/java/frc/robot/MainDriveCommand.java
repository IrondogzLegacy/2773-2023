// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.math.filter.SlewRateLimiter;
//importing the XboxController commands
import edu.wpi.first.wpilibj.XboxController;
//importing a base for commands in WPILib
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Constants;

//defining a class MainDriveCommand which branches off of the CommandBase (imported earlier)
public class MainDriveCommand extends CommandBase {
  // creating a private class for the joysticks
  private final XboxController joystick;
  // creating a private class for the MainDriveSubsystem
  private final MainDriveSubsystem driveSubsystem;

  private final XboxController armStick;

  /** Creates a new DriveCommand using the two above classes */
  public MainDriveCommand(MainDriveSubsystem driveSubsystem, XboxController joystick,
      XboxController secondaryJoystick) {
    // Use addRequirements() here to declare subsystem dependencies, requiring
    // references to those for it to run.
    addRequirements(driveSubsystem);
    // specifying which joystick to use.
    this.joystick = joystick;
    // specifying which subsystem to use for driving
    this.driveSubsystem = driveSubsystem;

    this.armStick = secondaryJoystick;
  }

  // error message if the below code fails
  @Override
  // called when the robot is started
  public void initialize() {
    driveSubsystem.lowSpeed = false;
    // no code atm
  }

  // Creates a SlewRateLimiter that limits the rate of change of the signal to 0.5
  // units per second
  SlewRateLimiter filter = new SlewRateLimiter(0.9);
  LinearFilter linfilter = LinearFilter.singlePoleIIR(0.05, 0.02);
  LinearFilter moveAvgFilter = LinearFilter.movingAverage(3);

  // Called every time the scheduler runs while the command is scheduled.
  // error message if the below code fails
  @Override
  public void execute() {
    boolean isMoving = false;
    boolean isSlow = joystick.getRawButton(6);
    if (!isSlow) {
      // Calculates the next value of the output
      // double filterOutput = filter.calculate(-joystick.getLeftY() * Constants.SpeedFactor);
      double speedRateControl = Math.abs(joystick.getRightTriggerAxis()) >= 0.01 ? joystick.getRightTriggerAxis()
          : -joystick.getRightY();
      double filterOutput = -joystick.getLeftY() * Constants.SpeedFactor
          * (1 + Constants.SpeedIncrease * speedRateControl);
      driveSubsystem.arcadeDrive(
          filterOutput, -joystick.getLeftX() * Constants.RotationFactor);
      isMoving = Math.abs(filterOutput) > 0.01 || Math.abs(joystick.getLeftX()) > 0.01;

    } else {
      driveSubsystem.arcadeDrive(-joystick.getLeftY() * Constants.SpeedFactorLow,
          -joystick.getLeftX() * Constants.RotationFactorLow);
      isMoving = Math.abs(joystick.getLeftY()) > 0.01 || Math.abs(joystick.getLeftX()) > 0.01;
    }
    double DpadSpeed = Constants.DpadSpeed;
    if (!isMoving) {
      switch (armStick.getPOV()) {
        case 0:
          driveSubsystem.driveLine(DpadSpeed);
          return;
        case 90:
          driveSubsystem.rotation(DpadSpeed);
          return;
        case 180:
          driveSubsystem.driveLine(-DpadSpeed);
          return;
        case 270:
          driveSubsystem.rotation(-DpadSpeed);
          return;
      }
      if (Math.abs(armStick.getLeftX()) > 0.01) {
        driveSubsystem.rotation(armStick.getLeftX() * DpadSpeed);
      }
    }
  }

  // error message if the below code fails
  @Override
  // if the function execute is interrupted, then the below code is called
  // (safety?)
  public void end(boolean interrupted) {
    // no code atm
  }

  // error message if the below code fails
  @Override
  // Returns true when the command should end.
  public boolean isFinished() {
    // as of right now this boolean will always be false
    return false;
  }
}

// Pseudocode updated 1/23/2023, MainDriveCommand