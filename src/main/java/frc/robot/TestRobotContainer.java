package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class TestRobotContainer {
    private final Joystick m_stick = new Joystick(0);
    private final TestDriveSubsystem driveSubsystem = new TestDriveSubsystem();
    private final TestDriveCommand driveCommand = new TestDriveCommand(driveSubsystem, m_stick);
    private final TestCam testCam = new TestCam();
    public TestRobotContainer() {
        // Configure the button bindings
        configureButtonBindings();

        driveSubsystem.setDefaultCommand(driveCommand);
        testCam.setDefaultCommand(new TestCamCommand(testCam));
    }

    private void configureButtonBindings() {

    }
}