package frc.robot;

import javax.print.attribute.standard.JobHoldUntil;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class TestRobotContainer {
    private final Joystick m_stick = new Joystick(0);
    private final TestDriveSubsystem driveSubsystem = new TestDriveSubsystem();
    private final TestDriveCommand driveCommand = new TestDriveCommand(driveSubsystem, m_stick);
    private final TestCam testCam = new TestCam();
    private final TestNavigationSubsystem navigationSubsystem = new TestNavigationSubsystem();
    private final TestEncoderSubsystem testEncoderSubsystem = new TestEncoderSubsystem();

    private final TestTurnAngleCommand turnAngleCommand = new TestTurnAngleCommand(driveSubsystem, navigationSubsystem);
    private final MajorsTestCommand majorCommand = new MajorsTestCommand(testEncoderSubsystem, navigationSubsystem); 

    public TestRobotContainer() {
        // Configure the button bindings
        configureButtonBindings();

        driveSubsystem.setDefaultCommand(driveCommand);
    }

    JoystickButton button1 = new JoystickButton(m_stick, 1);
    JoystickButton button4 = new JoystickButton(m_stick, 4);
    private void configureButtonBindings() {
        button1.whileTrue(turnAngleCommand);
        button4.whileTrue(majorCommand);
    }
}