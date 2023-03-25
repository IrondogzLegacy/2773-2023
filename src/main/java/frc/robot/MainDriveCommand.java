// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//importing the XboxController commands
import edu.wpi.first.wpilibj.XboxController;
//importing a base for commands in WPILib
import edu.wpi.first.wpilibj2.command.CommandBase;

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

  // Called every time the scheduler runs while the command is scheduled.
  // error message if the below code fails
  @Override
  public void execute() {
    if (driveSubsystem.lowSpeed == false) {
      driveSubsystem.arcadeDrive(-joystick.getLeftY(), -joystick.getLeftX());
    } else {
      driveSubsystem.slowDrive(-joystick.getLeftY(), -joystick.getLeftY());
    }
    double DpadSpeed = 0.3;
    if (!driveSubsystem.isMoving) {
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