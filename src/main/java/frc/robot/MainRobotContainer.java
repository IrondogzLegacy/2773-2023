package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
//imports joystick controls and functions
public class MainRobotContainer {
    private final Joystick m_stick = new Joystick(0);
    private final MainDriveSubsystem driveSubsystem = new MainDriveSubsystem();
    private final MainDriveCommand driveCommand = new MainDriveCommand(driveSubsystem, m_stick);
    private final FlagMotorSubsystem flagMotorSubsystem = new FlagMotorSubsystem();
    private final MainCamSubsystem MainCamSubsystem = new MainCamSubsystem();
    private final FlagMotorCommand flagMotorCommand = new FlagMotorCommand(flagMotorSubsystem, m_stick);
//Needed to make the controller function
    public MainRobotContainer() {
        // Configure the button bindings
        configureButtonBindings();

        driveSubsystem.setDefaultCommand(driveCommand);
        }

    JoystickButton button2 = new JoystickButton(m_stick, 2);
    private void configureButtonBindings() {
        button2.whileTrue(flagMotorCommand);
    }
     
}