// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//importing the subsystem base commands
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//importing motorcontrol commands for PMWSparkMax's
import com.revrobotics.CANSparkMax;

//creates a public class FlagMotorSubsystem that is based off of SystemBase
public class ClawSubsystem extends SubsystemBase {
 
  private final CANSparkMax ClawMotor = new CANSparkMax(MainConstants.ClawMotorCANID, MainConstants.motorType);

  public ClawSubsystem () { 
    ClawMotor.setInverted(true);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void stopMotor() {
    ClawMotor.set(0);
  }
  public void Grab() {
    ClawMotor.set(MainConstants.ClawMotorSpeed);
  }
  public void Release() {
    ClawMotor.set(MainConstants.ReleaseSpeed);
  }
}
