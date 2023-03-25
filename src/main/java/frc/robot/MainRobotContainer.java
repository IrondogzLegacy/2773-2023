package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.event.BooleanEvent;
import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Arm.ArmSubsystem;
import frc.robot.Arm.RetractCommand;
import frc.robot.Arm.RotateDownCommand;
import frc.robot.Arm.RotateUpCommand;
import frc.robot.Arm.StretchCommand;
import frc.robot.Constants.Constants;

//imports joystick controls and functions
public class MainRobotContainer {
    private final XboxController main_stick = new XboxController(0);
    private final XboxController arm_stick = new XboxController(1);
    private final MainDriveSubsystem driveSubsystem = new MainDriveSubsystem();
    private final MainDriveCommand driveCommand = new MainDriveCommand(driveSubsystem, main_stick, arm_stick);
    private final MainNavigationSubsystem navigationSubsystem = new MainNavigationSubsystem();
    private final ArmSubsystem armSubsystem = Constants.IsTestRobot ? null
            : new ArmSubsystem();
    private final PneumaticsSubsystem pnuematicsSubsystem = new PneumaticsSubsystem();

    // Autonomous Section
    public Command getAutonomousCommand1() {
        InstantCommand openArm = new InstantCommand(pnuematicsSubsystem::retractIntake, pnuematicsSubsystem);
        InstantCommand closeArm = new InstantCommand(pnuematicsSubsystem::deployIntake, pnuematicsSubsystem);
        var driveBack = new RunCommand(driveSubsystem::driveBack, driveSubsystem);

        // Jackson uncommented - new times
        var rotateUpCommand = new ParallelRaceGroup(
                new WaitCommand(5.25), new RotateUpCommand(armSubsystem));
        var extendArmCommand = new ParallelRaceGroup(new WaitCommand(2.05), new StretchCommand(armSubsystem));
        var retractArmCommand = new ParallelRaceGroup(new WaitCommand(2.05), new RetractCommand(armSubsystem));
        var retractArm2 = new ParallelRaceGroup(new WaitCommand(1), retractArmCommand);
        var retractBack = new ParallelRaceGroup(new WaitCommand(4), new RetractCommand(armSubsystem));
        var goBackCommand = new ParallelRaceGroup(new WaitCommand(12), driveBack);

        return closeArm.andThen(retractArm2).andThen(rotateUpCommand)
                .andThen(extendArmCommand).andThen(openArm).andThen(retractBack).andThen(goBackCommand);
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

    public Command getAutonomousCommandTest() { 
        var move3 = new MoveDistanceCommandPID(driveSubsystem, navigationSubsystem, 3);
        var rotate90 = new RotationCommand(driveSubsystem, navigationSubsystem, 90);
        return move3;
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

    private final EventLoop triggerLoop = new EventLoop();

    BooleanEvent leftTrigger1 = new BooleanEvent(triggerLoop, () -> {
        return arm_stick.getLeftTriggerAxis() > 0.5;
    });
    BooleanEvent rightTrigger1 = new BooleanEvent(triggerLoop, () -> {
        return arm_stick.getRightTriggerAxis() > 0.5;
    });

    public void checkTriggers() {
        triggerLoop.poll();
    }

    // Controls how it grabs or lets go
    JoystickButton openCloseButton = new JoystickButton(main_stick, 1);
    JoystickButton button2 = new JoystickButton(main_stick, 2);
    JoystickButton button3 = new JoystickButton(main_stick, 3);
    JoystickButton button4 = new JoystickButton(main_stick, 4);
    JoystickButton button5 = new JoystickButton(main_stick, 5);
    JoystickButton button6 = new JoystickButton(main_stick, 6);
    JoystickButton button7 = new JoystickButton(main_stick, 7);

    JoystickButton Abutton1 = new JoystickButton(arm_stick, 1);
    JoystickButton Abutton2 = new JoystickButton(arm_stick, 2);
    JoystickButton openCloseButtonAtArmStick = new JoystickButton(arm_stick, 3);
    JoystickButton Abutton4 = new JoystickButton(arm_stick, 4);
    JoystickButton rotateDownButton = new JoystickButton(arm_stick, 5);
    JoystickButton rotateUpButton = new JoystickButton(arm_stick, 6);
    JoystickButton Abutton7 = new JoystickButton(arm_stick, 7);

    // Abuttons are for the second controller

    private void configureButtonBindings() {
        final RotationCommand rotationFlip = new RotationCommand(driveSubsystem, navigationSubsystem, 180);
        final ActiveBrakingCommand activeBraking = new ActiveBrakingCommand(driveSubsystem, navigationSubsystem);
        InstantCommand openCloseArm = new InstantCommand(pnuematicsSubsystem::openCloseArm, pnuematicsSubsystem);
        // The below commands are used for printing values / calibration
        InstantCommand speedChange = new InstantCommand(driveSubsystem::changeSpeedMode);

        openCloseButton.onTrue(openCloseArm);
        button2.whileTrue(speedChange);
        button3.whileTrue(activeBraking);
        button4.whileTrue(rotationFlip);

        if (!Constants.IsTestRobot) {
            final RetractCommand retractCommand = new RetractCommand(armSubsystem);
            final StretchCommand stretchCommand = new StretchCommand(armSubsystem);
            final RotateUpCommand rotateUp = new RotateUpCommand(armSubsystem);
            final RotateDownCommand rotateDown = new RotateDownCommand(armSubsystem);
            openCloseButtonAtArmStick.onTrue(openCloseArm);
            rotateDownButton.whileTrue(rotateDown);
            rotateUpButton.whileTrue(rotateUp);
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