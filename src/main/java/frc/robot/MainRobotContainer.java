package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

//imports joystick controls and functions
public class MainRobotContainer {
    private final Joystick main_stick = new Joystick(0);
    private final Joystick arm_stick = new Joystick(1);
    private final MainDriveSubsystem driveSubsystem = new MainDriveSubsystem();
    private final MainDriveCommand driveCommand = new MainDriveCommand(driveSubsystem, main_stick);
    private final MainNavigationSubsystem navigationSubsystem = new MainNavigationSubsystem();
    private final ClawSubsystem GrabOnSubsystem = new ClawSubsystem();
    //private final MainCamSubsystem camSubsystem = new MainCamSubsystem();
    private final ArmSubsystem armSubsystem = new ArmSubsystem();
    private final PneumaticsSubsystem PnuematicsSubsystem = new PneumaticsSubsystem();

    // Autonomous Section
    public Command getAutonomousCommand1() {
        final MoveDistanceCommand move2 = new MoveDistanceCommand(driveSubsystem, navigationSubsystem, 2);
        final MoveDistanceCommand moveBack2 = new MoveDistanceCommand(driveSubsystem, navigationSubsystem, -2);
        final LetGoCommand letGoCommand = new LetGoCommand(GrabOnSubsystem, main_stick);
        final RotationCommand rotationFlip = new RotationCommand(driveSubsystem, navigationSubsystem, 180);

        var retractCommand = new ParallelRaceGroup(
                new WaitCommand(1),
                new RetractCommand(armSubsystem));
        var stretchCommand = new ParallelRaceGroup(
                new WaitCommand(1), new StretchCommand(armSubsystem));

        return move2.andThen(letGoCommand).andThen(moveBack2)
                .andThen(rotationFlip).andThen(stretchCommand).andThen(retractCommand);
    }

    public Command getAutonomousCommand2() {
        final MoveDistanceCommand move2 = new MoveDistanceCommand(driveSubsystem, navigationSubsystem, -10);
        final AutoBalanceCommand autoBalance = new AutoBalanceCommand(driveSubsystem, navigationSubsystem);

        var moveUntilandAutoBalance = move2
                .until(() -> navigationSubsystem.getPitch() > 12 || navigationSubsystem.getPitch() < -12)
                .andThen(autoBalance);
        var moveOnCommand = new ParallelRaceGroup(
                new WaitCommand(15),
                moveUntilandAutoBalance);
        return moveOnCommand;
    }

    public void resetGyro() {
        navigationSubsystem.resetGyro();
    }

    // Needed to make the controller function
    public MainRobotContainer() {
        // Configure the button bindings
        configureButtonBindings();

        driveSubsystem.setDefaultCommand(driveCommand);
    }

    // Controls how it grabs or lets go
    JoystickButton button1 = new JoystickButton(main_stick, 1);
    JoystickButton button2 = new JoystickButton(main_stick, 2);
    JoystickButton button3 = new JoystickButton(main_stick, 3);
    JoystickButton button4 = new JoystickButton(main_stick, 4);
    JoystickButton button5 = new JoystickButton(main_stick, 5);
    JoystickButton button6 = new JoystickButton(main_stick, 6);
    JoystickButton button7 = new JoystickButton(main_stick, 7);

    JoystickButton Abutton1 = new JoystickButton(arm_stick, 1);
    JoystickButton Abutton2 = new JoystickButton(arm_stick, 2);
    JoystickButton Abutton3 = new JoystickButton(arm_stick, 3);
    JoystickButton Abutton4 = new JoystickButton(arm_stick, 4);
    JoystickButton Abutton5 = new JoystickButton(arm_stick, 5);
    JoystickButton Abutton6 = new JoystickButton(arm_stick, 6);

    private void configureButtonBindings() {
        final GrabOnCommand grabOnCommand = new GrabOnCommand(GrabOnSubsystem, main_stick);
        final LetGoCommand letGoCommand = new LetGoCommand(GrabOnSubsystem, main_stick);
        final RotationCommand rotate90 = new RotationCommand(driveSubsystem, navigationSubsystem, 90);
        //final TurnToTagCommand turnToTagCommand = new TurnToTagCommand(driveSubsystem, camSubsystem,
                //navigationSubsystem, rotate90);
        final RetractCommand retractCommand = new RetractCommand(armSubsystem);
        final StretchCommand stretchCommand = new StretchCommand(armSubsystem);
        final Stretch1RevCommand stretch1RevCommand = new Stretch1RevCommand(armSubsystem);
        final ResetArmEncoderCommand resetArmEncoderCommand = new ResetArmEncoderCommand(armSubsystem);
        final ActiveBrakingCommand activeBraking = new ActiveBrakingCommand(driveSubsystem, navigationSubsystem);
        /*
         * Below is not used, attempted to assign commands to triggers a while ago
         * XboxController controllerOne = new XboxController(0);
         * XboxController controllerTwo = new XboxController(1);
         * Trigger retractTrigger = new JoystickButton(controllerOne,
         * XboxController.Trigger.kLeftTrigger.value); // Creates a new JoystickButton
         * object for the `Y` button on exampleController
         */
        final RotationCommand rotationFlip = new RotationCommand(driveSubsystem, navigationSubsystem, 180);
        final RotateUpCommand rotateUp = new RotateUpCommand(armSubsystem);
        final RotateDownCommand rotateDown = new RotateDownCommand(armSubsystem);

        // actuate
        button1.whileTrue(
                new CommandBase() {
                    {
                        addRequirements(PnuematicsSubsystem);
                    }

                    @Override
                    public void initialize() {
                        PnuematicsSubsystem.deployIntake();
                    }

                    @Override
                    public boolean isFinished() {
                        return true;
                    }
                });
        // stop actuation
        button2.whileTrue(new CommandBase() {
            {
                addRequirements(PnuematicsSubsystem);
            }

            @Override
            public void initialize() {
                PnuematicsSubsystem.retractIntake();
            }

            @Override
            public boolean isFinished() {
                return true;
            }
        });
        InstantCommand printGyroValues = new InstantCommand(navigationSubsystem::printGyroValues, navigationSubsystem);
        button3.onTrue(printGyroValues);
        button4.whileTrue(activeBraking);
        //button5.onTrue(turnToTagCommand);
        button6.onTrue(grabOnCommand);
        //button7.onTrue(turnToTagCommand);
        // final CommandBase majorCommand = createMajorsMainCommand();
        Abutton1.onTrue(rotationFlip);
        Abutton2.whileTrue(retractCommand);
        Abutton3.whileTrue(stretchCommand);
        // Abutton5.whileTrue(new InstantCommand(armSubsystem::rotateUp, armSubsystem));
        // //can't use the instant command stuff when I need to use the stop function
        // Abutton6.whileTrue(new InstantCommand(armSubsystem::rotateDown,
        // armSubsystem)); //can't use the instant command stuff when I need to use the
        // stop function
        Abutton5.whileTrue(rotateUp);
        Abutton6.whileTrue(rotateDown);
    }
}