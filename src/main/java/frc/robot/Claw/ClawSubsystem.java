// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Claw;

//importing the subsystem base commands
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Constants;

//importing motorcontrol commands for PMWSparkMax's
import com.revrobotics.CANSparkMax;

//creates a public class FlagMotorSubsystem that is based off of SystemBase
public class ClawSubsystem extends SubsystemBase {

  private final CANSparkMax ClawMotor = Constants.ClawMotorCANID < 0 ? null
      : new CANSparkMax(Constants.ClawMotorCANID, Constants.DriveMotorType);

  public ClawSubsystem() {
    if(Constants.ClawMotorCANID < 0) {} else ClawMotor.setInverted(true);
    ClawMotor.setSmartCurrentLimit (20);
    ClawMotor.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  // Controls the claw motor
  public void stopClawMotor() {
    ClawMotor.set(0);
  }

  public void Grab() {
    ClawMotor.set(Constants.ClawMotorSpeed);
  }

  public void Release() {
    ClawMotor.set(Constants.ReleaseSpeed);
  }
}
// Commented out code since this was removed from the bot, will add it back when
// it is added back!
// 2/16/2023 Added back!
