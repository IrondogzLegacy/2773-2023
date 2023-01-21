package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class RobotContainer {
    private final Joystick m_stick = new Joystick(0);
    private final DriveSubsystem driveSubsystem = new DriveSubsystem();
    private final DriveCommand driveCommand = new DriveCommand(driveSubsystem, m_stick);

    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();

        driveSubsystem.setDefaultCommand(driveCommand);
    }

    private void configureButtonBindings() {

    }
}