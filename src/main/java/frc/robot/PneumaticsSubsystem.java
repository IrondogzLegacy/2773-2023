// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class PneumaticsSubsystem extends SubsystemBase {

  // Initializes the intake motor
  public static final int intakePneumaticsController = 0;
  private final Solenoid deploySolendoidPCM = new Solenoid(PneumaticsModuleType.CTREPCM, 7);
  private final Solenoid retractSolenoidPCM = new Solenoid(PneumaticsModuleType.CTREPCM, 6);
  public Object retractIntake;
  public static boolean clawState; //false is open, true is closed
  Compressor pcmCompressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
  /** Creates a new TestPneumaticsSubsystem. */
  public PneumaticsSubsystem() {
    boolean pressureSwitch = pcmCompressor.getPressureSwitchValue();
    double current = pcmCompressor.getCurrent();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void deployIntake() {
    deploySolendoidPCM.set(true);
    retractSolenoidPCM.set(false);
    clawState = true;
  }

  public void retractIntake() {
    deploySolendoidPCM.set(false);
    retractSolenoidPCM.set(true);
    clawState = false;
  }

  public void stopIntake() {
    deploySolendoidPCM.set(false);
    retractSolenoidPCM.set(false);
  }

  public void openCloseArm()
  {
    if (clawState = false) {
      deployIntake();
    }
    else retractIntake();
  }
}
