package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
//imports joystick controls and functions
public class MainRobotContainer {
    private final Joystick m_stick = new Joystick(0);
    private final MainDriveSubsystem driveSubsystem = new MainDriveSubsystem();
    private final MainDriveCommand driveCommand = new MainDriveCommand(driveSubsystem, m_stick);
    private final MainNavigationSubsystem navigationSubsystem = new MainNavigationSubsystem();
    private final ClawSubsystem GrabOnSubsystem = new ClawSubsystem();
    private final MainCamSubsystem camSubsystem = new MainCamSubsystem();
    private final GrabOnCommand grabOnCommand = new GrabOnCommand(GrabOnSubsystem, m_stick);
    private final LetGoCommand letGoCommand = new LetGoCommand(GrabOnSubsystem, m_stick);
    private final TurnToTagCommand turnToTagCommand = new TurnToTagCommand(driveSubsystem, camSubsystem);    
    private final RotationCommand rotationCommand = new RotationCommand(driveSubsystem, navigationSubsystem, 30);
    //Autonomous Section
    public Command getAutonomousCommand() {
    return grabOnCommand;
    }

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
    JoystickButton button4 = new JoystickButton(m_stick, 4);
    private void configureButtonBindings() {
        button1.whileTrue(grabOnCommand);
        button3.whileTrue(letGoCommand);
        button2.whileTrue(turnToTagCommand);
        button4.whileTrue(rotationCommand);
    }
     
}