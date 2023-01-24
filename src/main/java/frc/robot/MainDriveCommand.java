// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
//importing the Joystick commands
import edu.wpi.first.wpilibj.Joystick;
//importing a base for commands in WPILib
import edu.wpi.first.wpilibj2.command.CommandBase;

//defining a class MainDriveCommand which branches off of the CommandBase (imported earlier)
public class MainDriveCommand extends CommandBase {
  //creating a private class for the joysticks
  private final Joystick joystick;
  //creating a private class for the MainDriveSubsystem
  private final MainDriveSubsystem driveSubsystem;
  /** Creates a new DriveCommand using the two above classes */
  public MainDriveCommand(MainDriveSubsystem driveSubsystem, Joystick joystick) {
    // Use addRequirements() here to declare subsystem dependencies, requiring references to those for it to run.
    addRequirements(driveSubsystem);
    //specifying which joystick to use.
    this.joystick = joystick;
    //specifying which subsystem to use for driving
    this.driveSubsystem = driveSubsystem;
  }

    //error message if the below code fails  
  @Override
  //called when the robot is started
  public void initialize() {
    //no code atm
  }

  // Called every time the scheduler runs while the command is scheduled.
  //error message if the below code fails
  @Override
  public void execute() {
    driveSubsystem.arcadeDrive(-joystick.getY(), -joystick.getX());
  }

//error message if the below code fails  
  @Override
  //if the function execute is interrupted, then the below code is called (safety?)
  public void end(boolean interrupted) {
    //no code atm
  }

  //error message if the below code fails
  @Override
  // Returns true when the command should end.
  public boolean isFinished() {
    //as of right now this boolean will always be false
    return false;
  }
}

//Pseudocode updated 1/23/2023, MainDriveCommand