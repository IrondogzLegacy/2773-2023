// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**Alright so Major wants TestBot to do the following things
1. Move forward X distance
2. Turn around 4 times, then another quarter turn (ends facing right, 360*4 degrees + 90 degrees)
3. Move forward Y distance again
Now, I'm going to add another one, move back to its starting position.
4. Move backwards Y distance.
5. Turn left (-90)
6. Move backwards X distance.
Good luck to us :P Will have to use encoders in TestEncoderSubsystem for this!
**/
public class MajorsTestCommand extends CommandBase {
  private TestEncoderSubsystem testEncoderSubsystem;
  private TestNavigationSubsystem navigationSubsystem;

/** Creates a new MajorsTestCommand. */
  public MajorsTestCommand(TestEncoderSubsystem testEncoderSubsystem, TestNavigationSubsystem navigationSubsystem) {
    this.testEncoderSubsystem = testEncoderSubsystem;
    this.navigationSubsystem = navigationSubsystem;
    addRequirements(testEncoderSubsystem, navigationSubsystem);// Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
