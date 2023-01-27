// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//importing the subsystem base commands
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//importing motorcontrol commands for PMWSparkMax's
import com.revrobotics.CANSparkMax;

//creates a public class FlagMotorSubsystem that is based off of SystemBase
public class FlagMotorSubsystem extends SubsystemBase {
 
  private final CANSparkMax flagMotor = new CANSparkMax(MainConstants.FlagMotorCANID, MainConstants.motorType);
  public FlagMotorSubsystem () { 
    flagMotor.setInverted(true);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void stopMotor() {
    flagMotor.set(0);
  }
  public void startMotor() {
    flagMotor.set(MainConstants.flagMotorSpeed);
    
  }
}
