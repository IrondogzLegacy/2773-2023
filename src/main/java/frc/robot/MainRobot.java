// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.nio.LongBuffer;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
//import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Arm.ArmSubsystem;

public class MainRobot extends TimedRobot {

  MainRobotContainer robotContainer;
  Command autonomousCommand;
  AddressableLED m_led = new AddressableLED(9);
    AddressableLEDBuffer m_ledBuffer;
  
    @Override
  public void robotInit() {
    robotContainer = new MainRobotContainer();
    robotContainer.resetGyro();
    CameraServer.startAutomaticCapture();
    // PWM port 9
    // Must be a PWM header, not MXP or DIO
    
for (var i = 0; i < m_ledBuffer.getLength(); i++) {
   // Sets the specified LED to the RGB values for red
   m_ledBuffer.setRGB(i, 255, 0, 0);


m_led.setData(m_ledBuffer);
    // Reuse buffer
    // Default to a length of 60, start empty output
    // Length is expensive to set, so only set it once, then just update data
    m_ledBuffer = new AddressableLEDBuffer(60);
    m_led.setLength(m_ledBuffer.getLength());

    // Set the data
    m_led.setData(m_ledBuffer);
    m_led.start();
  }
  }

  @Override
  public void autonomousInit() {
    autonomousCommand = robotContainer.getAutonomousCommandTest();

    // schedule the autonomous command (example)
    if (autonomousCommand != null) {
      autonomousCommand.schedule();
    }
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    robotContainer.checkTriggers();
  }

  @Override
  public void teleopPeriodic() {
  }
}
