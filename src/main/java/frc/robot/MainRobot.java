// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
//import timing for the robot
import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
//import command scheduler for the robot
import edu.wpi.first.wpilibj2.command.CommandScheduler;
//import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs
 * the motors with arcade steering.
 */
public class MainRobot extends TimedRobot {

  MainRobotContainer m_robotContainer;
  Command m_autonomousCommand;
  @Override
  public void robotInit() {
    m_robotContainer = new MainRobotContainer();
    m_robotContainer.resetGyro();
  }
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand1();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
   m_robotContainer.checkTriggers();
  }

  @Override
  public void teleopPeriodic() {}
}
