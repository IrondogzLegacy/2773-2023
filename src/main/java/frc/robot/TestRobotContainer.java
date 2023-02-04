package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class TestRobotContainer {
    private final Joystick m_stick = new Joystick(0);
    private final TestDriveSubsystem driveSubsystem = new TestDriveSubsystem();
    private final TestDriveCommand driveCommand = new TestDriveCommand(driveSubsystem, m_stick);
   // private final TestCam testCam = new TestCam();
    private final TestNavigationSubsystem navigationSubsystem = new TestNavigationSubsystem();
    private final TestEncoderSubsystem testEncoderSubsystem = new TestEncoderSubsystem();

    private final TestTurnAngleCommand turnAngleCommand = new TestTurnAngleCommand(driveSubsystem, navigationSubsystem, 30);
    private final MoveDistanceCommand moveCommand = new MoveDistanceCommand(testEncoderSubsystem, driveSubsystem, 3);

    private final TestPneumaticsSubsystem testPnuematicsSubsystem = new TestPneumaticsSubsystem();
    
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
        MoveDistanceCommand move3b = new MoveDistanceCommand(testEncoderSubsystem, driveSubsystem, 3);
        TestTurnAngleCommand turn1710 = new TestTurnAngleCommand(driveSubsystem, navigationSubsystem, 1710);
        MoveDistanceCommand move2 = new MoveDistanceCommand(testEncoderSubsystem, driveSubsystem, 2);
        MoveDistanceCommand move2b = new MoveDistanceCommand(testEncoderSubsystem, driveSubsystem, 2);
        TestTurnAngleCommand turn180 = new TestTurnAngleCommand(driveSubsystem, navigationSubsystem, 180);
        TestTurnAngleCommand turn180b = new TestTurnAngleCommand(driveSubsystem, navigationSubsystem, 180);
        TestTurnAngleCommand turn90 = new TestTurnAngleCommand(driveSubsystem, navigationSubsystem, 90);
        return move3.andThen(turn1710).andThen(move2)
        .andThen(turn180).andThen(move2b).andThen(turn90)
        .andThen(move3b).andThen(turn180b);
    }
    JoystickButton button1 = new JoystickButton(m_stick, 1);
    JoystickButton button4 = new JoystickButton(m_stick, 4);
    JoystickButton button3 = new JoystickButton(m_stick, 3);
    JoystickButton button5 = new JoystickButton(m_stick, 5);
    JoystickButton button6 = new JoystickButton(m_stick, 6);


    private void configureButtonBindings() {
        button1.whileTrue(turnAngleCommand);
        button3.whileTrue(moveCommand);
        final CommandBase majorCommand = createMajorsTestCommand();
        button4.whileTrue(majorCommand);
        button5.whileTrue(
            new CommandBase() {
                {
                  addRequirements(testPnuematicsSubsystem);
                }
          
                @Override
                public void initialize() {
                    testPnuematicsSubsystem.deployIntake();
                }
          
                @Override
                public boolean isFinished() {
                  return true;
                }
              });
        button6.whileTrue(
            new CommandBase() {
                {
                  addRequirements(testPnuematicsSubsystem);
                }
          
                @Override
                public void initialize() {
                    testPnuematicsSubsystem.retractIntake();
                }
          
                @Override
                public boolean isFinished() {
                  return true;
                }
              });
        
    }
}