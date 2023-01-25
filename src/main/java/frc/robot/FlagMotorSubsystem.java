// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//importing the subsystem base commands
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//importing motorcontrol commands for PMWSparkMax's
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

//creates a public class FlagMotorSubsystem that is based off of SystemBase
public class FlagMotorSubsystem extends SubsystemBase {
 
  private final PWMSparkMax m_flagMotor = new PWMSparkMax(MainConstants.flagMotorChannel);

   /** Creates a new FlagMotorSubsystem. */
  public FlagMotorSubsystem() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
