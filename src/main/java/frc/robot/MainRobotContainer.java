package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
//imports joystick controls and functions
public class MainRobotContainer {
    private final Joystick m_stick = new Joystick(0);
    private final MainDriveSubsystem driveSubsystem = new MainDriveSubsystem();
    private final MainDriveCommand driveCommand = new MainDriveCommand(driveSubsystem, m_stick);
    private final ClawSubsystem GrabOnSubsystem = new ClawSubsystem();
    private final MainCamSubsystem camSubsystem = new MainCamSubsystem();
    private final GrabOnCommand grabOnCommand = new GrabOnCommand(GrabOnSubsystem, m_stick);
    private final LetGoCommand letGoCommand = new LetGoCommand(GrabOnSubsystem, m_stick);
//Needed to make the controller function
    public MainRobotContainer() {
        // Configure the button bindings
        configureButtonBindings();

        driveSubsystem.setDefaultCommand(driveCommand);
        }
// Controls how it grabs or lets go
    JoystickButton button1 = new JoystickButton(m_stick, 1);
    JoystickButton button2 = new JoystickButton(m_stick, 2);
    JoystickButton button3 = new JoystickButton(m_stick, 3);
    private void configureButtonBindings() {
        button1.whileTrue(grabOnCommand);
        button3.whileTrue(letGoCommand);
        button2.whileTrue(new TurnToTagCommand(driveSubsystem, camSubsystem));
    }
     
}