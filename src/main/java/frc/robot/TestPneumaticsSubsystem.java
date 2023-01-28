// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class TestPneumaticsSubsystem extends SubsystemBase {
  /** Creates a new TestPneumaticsSubsystem. */
  public TestPneumaticsSubsystem() {}
  // Initializes the intake motor
  public static final int intakePneumaticsController = 0;
  private final Solenoid deploySolendoidPCM = new Solenoid(PneumaticsModuleType.CTREPCM, 7);
  private final Solenoid retractSolenoidPCM = new Solenoid(PneumaticsModuleType.CTREPCM, 6);
  public Object retractIntake;
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void deployIntake() {
    deploySolendoidPCM.set(true);
    retractSolenoidPCM.set(false);
  }

  public void retractIntake() {
    deploySolendoidPCM.set(false);
    retractSolenoidPCM.set(true);
  }

  public void stopIntake(){
    deploySolendoidPCM.set(false);
    retractSolenoidPCM.set(false);
  }
}
