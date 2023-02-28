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

  private final CANSparkMax ArmMotor = new CANSparkMax(MainConstants.ArmExtensionMotorCANID, MainConstants.motorType);
  private final RelativeEncoder armEncoder = ArmMotor.getEncoder();
  private final CANSparkMax ArmRotationMotor = new CANSparkMax(16, MainConstants.motorType);

  private final DigitalInput limitSwitch = new DigitalInput(9);
  private final AnalogInput lengthSensor = new AnalogInput(0);

  private final NetworkTable table = NetworkTableInstance.getDefault().getTable("Arm");
  private final NetworkTableEntry counterEntry = table.getEntry("count");
  private final NetworkTableEntry distanceEntry = table.getEntry("length");
  private final NetworkTableEntry switchEntry = table.getEntry("switch");

  public ArmSubsystem() {
    // unnecessary but I don't care
    ArmMotor.setInverted(true);
    armEncoder.setPositionConversionFactor(MainConstants.ArmEncoderRatio);
  }

  @Override
  public void periodic() {
    //counterEntry.setDouble(armEncoder.getPosition());
    distanceEntry.setDouble(lengthSensor.getVoltage());
    switchEntry.setBoolean(limitSwitch.get());
  }

  public void stretch() {
    ArmMotor.set(MainConstants.ArmMotorSpeed);
  }

  public void retract() {
    if (limitSwitch.get()) {
      ArmMotor.set(-MainConstants.ArmMotorSpeed);
    } else {
      ArmMotor.set(0);
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

  public void stopArmExtension() {ArmMotor.stopMotor();}

  public void printEncoder() {System.out.println(armEncoder.getPosition());}

  public void ResetArmEncoder() {
    armEncoder.setPosition(0);
    printEncoder();
  }

  public void rotateUp() {ArmRotationMotor.set(MainConstants.ArmRotationSpeed);}
  public void rotateDown() {ArmRotationMotor.set(-MainConstants.ArmRotationSpeed);}
  public void rotateStop() {ArmRotationMotor.set(0);}
}