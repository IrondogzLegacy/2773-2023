package frc.robot;

import edu.wpi.first.util.concurrent.Event;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.event.BooleanEvent;
import edu.wpi.first.wpilibj.event.EventLoop;
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
    private final XboxController main_stick = new XboxController(0);
    private final XboxController arm_stick = new XboxController(1);
    private final MainDriveSubsystem driveSubsystem = new MainDriveSubsystem();
    private final MainDriveCommand driveCommand = new MainDriveCommand(driveSubsystem, main_stick, arm_stick);
    private final MainNavigationSubsystem navigationSubsystem = new MainNavigationSubsystem();
    private final ClawSubsystem GrabOnSubsystem = new ClawSubsystem();
    // private final MainCamSubsystem camSubsystem = new MainCamSubsystem();
    private final ArmSubsystem armSubsystem = Constants.IsTestRobot ? null
            : new ArmSubsystem();
    private final PneumaticsSubsystem pnuematicsSubsystem = new PneumaticsSubsystem();
    // Autonomous Section
    public Command getAutonomousCommand1() {
        final MoveDistanceCommand move2 = new MoveDistanceCommand(driveSubsystem, navigationSubsystem, 2);
        final MoveDistanceCommand moveBack2 = new MoveDistanceCommand(driveSubsystem, navigationSubsystem, -2);
        final LetGoCommand letGoCommand = new LetGoCommand(GrabOnSubsystem, main_stick);
        final RotationCommand rotationFlip = new RotationCommand(driveSubsystem, navigationSubsystem, 180);
        final Stretch1RevCommand stretchFull = new Stretch1RevCommand(armSubsystem, 23);

        var retractCommand = new ParallelRaceGroup(
                new WaitCommand(1),
                new RetractCommand(armSubsystem));
        var stretchCommand = new ParallelRaceGroup(
                new WaitCommand(1), new StretchCommand(armSubsystem));
        var rotateUpCommand = new ParallelRaceGroup(
            new WaitCommand(0.5), new RotateUpCommand(armSubsystem));

        return move2.andThen(rotateUpCommand).andThen(stretchFull).andThen(letGoCommand).andThen(moveBack2)
                .andThen(rotationFlip);
                //.andThen(stretchCommand).andThen(retractCommand); 
    }

    public Command getAutonomousCommand2() {
        final MoveDistanceCommand move2 = new MoveDistanceCommand(driveSubsystem, navigationSubsystem, -10);
        final AutoBalanceCommand autoBalance = new AutoBalanceCommand(driveSubsystem, navigationSubsystem);
        // AutoBalance requires gyro.

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

    private final EventLoop m_loop = new EventLoop();

    BooleanEvent leftTrigger1 = new BooleanEvent(m_loop, () -> {
        return arm_stick.getLeftTriggerAxis() > 0.5;
    });
    BooleanEvent rightTrigger1 = new BooleanEvent(m_loop, () -> {
        return arm_stick.getRightTriggerAxis() > 0.5;
    });

    public void checkTriggers() {
        m_loop.poll();
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
    JoystickButton Abutton7 = new JoystickButton(arm_stick, 7);

    // Abuttons are for the second controller

    private void configureButtonBindings() {
        final GrabOnCommand grabOnCommand = new GrabOnCommand(GrabOnSubsystem, main_stick);
        final LetGoCommand letGoCommand = new LetGoCommand(GrabOnSubsystem, main_stick);
        final RotationCommand rotate90 = new RotationCommand(driveSubsystem, navigationSubsystem, 90);
        final MoveDistanceCommand move2 = new MoveDistanceCommand(driveSubsystem, navigationSubsystem, 2);
        final RotationCommand rotationFlip = new RotationCommand(driveSubsystem, navigationSubsystem, 180);
        final ActiveBrakingCommand activeBraking = new ActiveBrakingCommand(driveSubsystem, navigationSubsystem);
        final AutoBalanceCommand autoBalance = new AutoBalanceCommand(driveSubsystem, navigationSubsystem);
        final ReturnArmTo0Command returnArmTo0 = new ReturnArmTo0Command(armSubsystem);
        InstantCommand closeArm = new InstantCommand(pnuematicsSubsystem::deployIntake, pnuematicsSubsystem);
        InstantCommand openArm = new InstantCommand(pnuematicsSubsystem::retractIntake, pnuematicsSubsystem);
        InstantCommand openCloseArm = new InstantCommand(pnuematicsSubsystem::openCloseArm, pnuematicsSubsystem);
        // The below commands are used for printing values / calibration
        InstantCommand printGyroValues = new InstantCommand(navigationSubsystem::printGyroValues);

        button1.whileTrue(autoBalance);
        button2.whileTrue(activeBraking);
        button3.whileTrue(rotationFlip);
        button4.whileTrue(openCloseArm);
        // button2.whileTrue(returnArmTo0);
        // button5.onTrue(turnToTagCommand);
        // button5.onTrue(move2);
        // button6.onTrue(grabOnCommand);
        // final CommandBase majorCommand = createMajorsMainCommand();
        // Abutton1.onTrue(openCloseArm);
        // Abutton2.whileTrue(openArm);

        if (!Constants.IsTestRobot) {
            final RetractCommand retractCommand = new RetractCommand(armSubsystem);
            final StretchCommand stretchCommand = new StretchCommand(armSubsystem);
            final ResetArmEncoderCommand resetArmEncoderCommand = new ResetArmEncoderCommand(armSubsystem);
            final RotateUpCommand rotateUp = new RotateUpCommand(armSubsystem);
            final RotateDownCommand rotateDown = new RotateDownCommand(armSubsystem);
            InstantCommand printVoltage = new InstantCommand(armSubsystem::printVoltage);
            InstantCommand printMap = new InstantCommand(armSubsystem::printMap);
            // button2.onTrue(printVoltage);
            // button3.onTrue(printMap);
            Abutton3.whileTrue(openCloseArm);
            // Abutton4.whileTrue();
            // Abutton5.whileTrue(new InstantCommand(armSubsystem::rotateUp, armSubsystem));
            // //can't use the instant command stuff when I need to use the stop function
            // Abutton6.whileTrue(new InstantCommand(armSubsystem::rotateDown,
            // armSubsystem)); //can't use the instant command stuff when I need to use the
            // stop function

            Abutton5.whileTrue(rotateDown);
            Abutton6.whileTrue(rotateUp);
            leftTrigger1.castTo(Trigger::new).whileTrue(retractCommand);
            rightTrigger1.castTo(Trigger::new).whileTrue(stretchCommand);

        }
    }
}

// final TurnToTagCommand turnToTagCommand = new
// TurnToTagCommand(driveSubsystem, camSubsystem,
// navigationSubsystem, rotate90);
/*
 * Below is not used, attempted to assign commands to triggers a while ago
 * XboxController controllerOne = new XboxController(0);
 * XboxController controllerTwo = new XboxController(1);
 * Trigger retractTrigger = new JoystickButton(controllerOne,
 * XboxController.Trigger.kLeftTrigger.value); // Creates a new JoystickButton
 * object for the `Y` button on exampleController
 */