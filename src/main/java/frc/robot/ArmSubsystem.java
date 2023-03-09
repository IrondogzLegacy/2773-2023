// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAnalogSensor;
import com.revrobotics.SparkMaxLimitSwitch;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {

  private final CANSparkMax armMotor = Constants.IsTestRobot ? null
      : new CANSparkMax(Constants.ArmExtensionMotorCANID, Constants.motorType);
  private final RelativeEncoder armEncoder = Constants.IsTestRobot ? null
      : armMotor.getEncoder();
  private final CANSparkMax armRotationMotor = Constants.IsTestRobot ? null
      : new CANSparkMax(Constants.ArmRotationMotorCANID, Constants.motorType);

  private final DigitalInput limitSwitch = new DigitalInput(9);
  private final SparkMaxLimitSwitch limit2 = armMotor.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
  private final AnalogInput lengthSensor = new AnalogInput(0);

  private final NetworkTable table = NetworkTableInstance.getDefault().getTable("Arm");
  private final NetworkTableEntry counterEntry = table.getEntry("count");
  private final NetworkTableEntry distanceEntry = table.getEntry("length");
  private final NetworkTableEntry switchEntry = table.getEntry("switch");
  private final NetworkTableEntry armAngleEntry = table.getEntry("angle");
  private final NetworkTableEntry armVoltageEntry = table.getEntry("voltage");

  SparkMaxAnalogSensor armPotent = Constants.IsTestRobot ? null
      : armMotor.getAnalog(SparkMaxAnalogSensor.Mode.kAbsolute);

  AnalogInput armPotent2 = new AnalogInput(1);

  public ArmSubsystem() {
    // unnecessary but I don't care
    armMotor.setInverted(true);
    armEncoder.setPositionConversionFactor(Constants.ArmEncoderRatio);
  }

  @Override
  public void periodic() {
    counterEntry.setDouble(armEncoder.getPosition());
    distanceEntry.setDouble(lengthSensor.getVoltage());
    switchEntry.setBoolean(limit2.isPressed());
    armAngleEntry.setDouble(getRotationAngle());
    armVoltageEntry.setDouble(armPotent2.getVoltage());
    // System.out.println(armEncoder.getPosition());
  }

  public void stretch() {
    if ((getRotationAngle() > -70))
      armMotor.set(Constants.ArmMotorSpeed);
  }

  public void retract() {
    if (!limit2.isPressed()) {
      armMotor.set(-Constants.ArmMotorSpeed);
    } else {
      armMotor.set(0);
      ResetArmEncoder();

    }
  }

  public void stretchLength(double stretchDistance) {
    double stretch_start = armEncoder.getPosition();
    double ratio = armEncoder.getPositionConversionFactor();
    double stretch_finish = stretch_start + stretchDistance;
    if (stretchDistance > 0) {
      if (armEncoder.getPosition() < stretch_finish) {
        stretch();
      } else
        stopArmExtension();
    }
    if (stretchDistance < 0) {
      if (armEncoder.getPosition() > stretch_finish) {
        retract();
      } else
        stopArmExtension();
    } else
      stopArmExtension();
  }

  public void stopArmExtension() {
    armMotor.stopMotor();
  }

  public void printEncoder() {
    System.out.println(armEncoder.getPosition());
  }

  public void ResetArmEncoder() {
    armEncoder.setPosition(0);
    // printEncoder();
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

  private static double map(double x, double x1, double x2, double y1, double y2) {
    return (x - x1) / (x2 - x1) * (y2 - y1) + y1;
  }

  public double getRotationAngle() {
    return map(armPotent2.getVoltage(), Constants.ArmBottomVoltage, Constants.ArmTopVoltage, Constants.ArmMinDeg,
        Constants.ArmMaxDeg);
  }

  public void printVoltage() {
    System.out.println(armPotent2.getVoltage());
  }

  public void printMap() {
    System.out.println(getRotationAngle());
  }
}