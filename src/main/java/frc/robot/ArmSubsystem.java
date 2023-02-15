// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {

  private final CANSparkMax ArmMotor = new CANSparkMax(MainConstants.ArmMotorCANID, MainConstants.motorType);
  private final RelativeEncoder armEncoder = ArmMotor.getEncoder();

public ArmSubsystem () {
  //unnecessary but I don't care
  ArmMotor.setInverted(true);
  armEncoder.setPositionConversionFactor(0.5*3.14);
}
 @Override
 public void periodic() {
  // This method will be called once per scheduler run
 }
 public void stretch(){
  ArmMotor.set(MainConstants.ArmMotorSpeed);
 }
 public void retract(){
  ArmMotor.set(-MainConstants.ArmMotorSpeed);
 }

 public void stretch1Rev()
 {
  double stretch_start = armEncoder.getPosition();
  double ratio = armEncoder.getCountsPerRevolution();
  double stretch_finish = stretch_start + 1*ratio;
  if (armEncoder.getPosition() < stretch_finish)
  {
    stretch();
  }
 }
 public void stopArm()
 {
  ArmMotor.stopMotor();
 }
 public void ResetArmEncoder()
 {
  armEncoder.setPosition(0);
 }
}