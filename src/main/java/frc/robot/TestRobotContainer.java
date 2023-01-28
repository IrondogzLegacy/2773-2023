package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class TestRobotContainer {
    private final Joystick m_stick = new Joystick(0);
    private final TestDriveSubsystem driveSubsystem = new TestDriveSubsystem();
    private final TestDriveCommand driveCommand = new TestDriveCommand(driveSubsystem, m_stick);
    private final TestCam testCam = new TestCam();
    private final TestNavigationSubsystem navigationSubsystem = new TestNavigationSubsystem();
    private final TestEncoderSubsystem testEncoderSubsystem = new TestEncoderSubsystem();

    private final TestTurnAngleCommand turnAngleCommand = new TestTurnAngleCommand(driveSubsystem, navigationSubsystem, 30);
    private final MoveDistanceCommand moveCommand = new MoveDistanceCommand(testEncoderSubsystem, driveSubsystem, 3);

    // private final MajorsTestCommand majorCommand = new MajorsTestCommand(moveDistanceCommand, turnAngleCommand); 
    
    public TestRobotContainer() {
        // Configure the button bindings
        configureButtonBindings();

        driveSubsystem.setDefaultCommand(driveCommand);
    }

    private CommandBase createMajorsTestCommand() {
    /*Alright so Major wants TestBot to do the following things
    1. Move forward X distance
    2. Turn around 4 times, then another quarter turn (ends facing right, 360*4 degrees + 90 degrees)
    3. Move forward Y distance.
    4. Spin around 180 degrees
    Now, I'm going to add another one, move back to its starting position.
    5. Move forwards Y distance again.
    6. Turn right (90)
    7. Move backwards X distance.
    8. Turn around 180 degrees.
    Good luck to us :P Will have to use encoders in TestEncoderSubsystem for this!
    **/
        MoveDistanceCommand move3 = new MoveDistanceCommand(testEncoderSubsystem, driveSubsystem, 3);
        TestTurnAngleCommand turn1710 = new TestTurnAngleCommand(driveSubsystem, navigationSubsystem, 1710);
        MoveDistanceCommand move2 = new MoveDistanceCommand(testEncoderSubsystem, driveSubsystem, 2);
        TestTurnAngleCommand turn180 = new TestTurnAngleCommand(driveSubsystem, navigationSubsystem, 180);
        TestTurnAngleCommand turn90 = new TestTurnAngleCommand(driveSubsystem, navigationSubsystem, 90);
        return move3.andThen(turn1710).andThen(move2).andThen(turn180).andThen(move2).andThen(turn90).andThen(move3).andThen(turn180);
    }
    JoystickButton button1 = new JoystickButton(m_stick, 1);
    JoystickButton button4 = new JoystickButton(m_stick, 4);
    JoystickButton button3 = new JoystickButton(m_stick, 3);
    private void configureButtonBindings() {
        button1.whileTrue(turnAngleCommand);
        button3.whileTrue(moveCommand);
        final CommandBase majorCommand = createMajorsTestCommand();
        button4.whileTrue(majorCommand);
    }
}