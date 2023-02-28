// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {

  private final CANSparkMax armMotor = new CANSparkMax(Constants.ArmExtensionMotorCANID, Constants.motorType);
  private final RelativeEncoder armEncoder = armMotor.getEncoder();
  private final CANSparkMax armRotationMotor = Constants.IsTestRobot ? null
      : new CANSparkMax(Constants.ArmRotationMotorCANID, Constants.motorType);

  private final DigitalInput limitSwitch = new DigitalInput(9);
  private final AnalogInput lengthSensor = new AnalogInput(0);

  private final NetworkTable table = NetworkTableInstance.getDefault().getTable("Arm");
  private final NetworkTableEntry counterEntry = table.getEntry("count");
  private final NetworkTableEntry distanceEntry = table.getEntry("length");
  private final NetworkTableEntry switchEntry = table.getEntry("switch");

  public ArmSubsystem() {
    // unnecessary but I don't care
    armMotor.setInverted(true);
    armEncoder.setPositionConversionFactor(Constants.ArmEncoderRatio);
  }

  @Override
  public void periodic() {
    // counterEntry.setDouble(armEncoder.getPosition());
    distanceEntry.setDouble(lengthSensor.getVoltage());
    switchEntry.setBoolean(limitSwitch.get());
  }

  public void stretch() {
    armMotor.set(Constants.ArmMotorSpeed);
  }

  public void retract() {
    if (limitSwitch.get()) {
      armMotor.set(-Constants.ArmMotorSpeed);
    } else {
      armMotor.set(0);
    }
  }

  public void stretch1Rev() {
    double stretch_start = armEncoder.getPosition();
    double ratio = armEncoder.getCountsPerRevolution();
    double stretch_finish = stretch_start + 1 * ratio;
    if (armEncoder.getPosition() < stretch_finish) {
      stretch();
    }
  }

  public void stopArmExtension() {
    armMotor.stopMotor();
  }

  public void printEncoder() {
    System.out.println(armEncoder.getPosition());
  }

  public void ResetArmEncoder() {
    armEncoder.setPosition(0);
    printEncoder();
  }

  public void rotateUp() {
    armRotationMotor.set(Constants.ArmRotationSpeed);
  }

  public void rotateDown() {
    armRotationMotor.set(-Constants.ArmRotationSpeed);
  }

  public void rotateStop() {
    armRotationMotor.set(0);
  }
}