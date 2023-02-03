// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
//import the RobotBase library from wpilib
import edu.wpi.first.wpilibj.RobotBase;

/**
 * Do NOT add any static variables to this class, or any initialization at all. Unless you know what
 * you are doing, do not modify this file except to change the parameter class to the startRobot
 * call.
 */
//create a class TrueMain
public final class TrueMain {
  //private class TrueMain that can be changed, but code should not be modified here
  private TrueMain() {}

  /**
   * Main initialization function. Do not perform any initialization here.
   *
   * <p>If you change your main robot class, change the parameter type.
   */
  //A function for the main robot
   public static void main(String... args) {
    //starting the robot
    RobotBase.startRobot(MainRobot::new);
  }
}
//Test annotation