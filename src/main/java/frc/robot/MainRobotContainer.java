package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
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
    private final MoveDistanceCommand moveCommand = new MoveDistanceCommand (driveSubsystem, navigationSubsystem, 2);

    //Autonomous Section
    public Command getAutonomousCommand() {
        final MoveDistanceCommand move2 = new MoveDistanceCommand (driveSubsystem, navigationSubsystem, 2);    
        final MoveDistanceCommand moveBack2 = new MoveDistanceCommand (driveSubsystem, navigationSubsystem, -2);
        final LetGoCommand letGoCommand = new LetGoCommand(GrabOnSubsystem, m_stick);
        final RotationCommand rotationFlip = new RotationCommand(driveSubsystem, navigationSubsystem, 180);
        return move2.andThen(letGoCommand).andThen(moveBack2).andThen(rotationFlip);
    }

   //Needed to make the controller function
    public MainRobotContainer() {
        // Configure the button bindings
        configureButtonBindings();

        driveSubsystem.setDefaultCommand(driveCommand);
        }
        private CommandBase createMajorsMainCommand() {
            MoveDistanceCommand move3 = new MoveDistanceCommand(driveSubsystem, navigationSubsystem, 3);
            RotationCommand rotate90 = new RotationCommand(driveSubsystem, navigationSubsystem, 90);
            MoveDistanceCommand move3b = new MoveDistanceCommand(driveSubsystem, navigationSubsystem, 3);
            return move3.andThen(rotate90.andThen(move3b));
        }
// Controls how it grabs or lets go
    JoystickButton button1 = new JoystickButton(m_stick, 1);
    JoystickButton button2 = new JoystickButton(m_stick, 2);
    JoystickButton button3 = new JoystickButton(m_stick, 3);
    JoystickButton button4 = new JoystickButton(m_stick, 4);
    JoystickButton button5 = new JoystickButton(m_stick, 5);
    private void configureButtonBindings() {
        button1.whileTrue(grabOnCommand);
        button2.whileTrue(turnToTagCommand);
        button3.whileTrue(letGoCommand);
        button4.whileTrue(moveCommand);
        final CommandBase majorCommand = createMajorsMainCommand();
        button5.onTrue(majorCommand);
    }
     
}