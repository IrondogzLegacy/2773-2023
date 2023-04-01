package frc.robot;

import java.net.Proxy;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.event.BooleanEvent;
import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Arm.ArmSubsystem;
import frc.robot.Arm.MoveArmToAnglePositionCommand;
import frc.robot.Arm.RetractCommand;
import frc.robot.Arm.RotateDownCommand;
import frc.robot.Arm.RotateUpCommand;
import frc.robot.Arm.StretchCommand;
import frc.robot.Arm.StretchDistanceCommandPID;
import frc.robot.Arm.ArmControlCommand;
import frc.robot.Claw.ClawSubsystem;
import frc.robot.Claw.GrabOnCommand;
import frc.robot.Claw.LetGoCommand;
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
    private final ClawSubsystem clawSubsystem = new ClawSubsystem();

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
        InstantCommand driveSlow = new InstantCommand(driveSubsystem::driveSlow, driveSubsystem);
        final ActiveBrakingCommandPID activeBrakingPID = new ActiveBrakingCommandPID(driveSubsystem,
                navigationSubsystem);
        final AutoBalanceCommandPID autoBalance = new AutoBalanceCommandPID(driveSubsystem, navigationSubsystem);
        // AutoBalance requires gyro.

        var moveUntilandAutoBalance = driveSlow
                .until(() -> navigationSubsystem.getPitch() <= -1)
                .andThen(activeBrakingPID);
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
    JoystickButton autoBalanceButton = new JoystickButton(main_stick, 1);
    JoystickButton speedChangeButton = new JoystickButton(main_stick, 2);
    JoystickButton activeBreakingButton = new JoystickButton(main_stick, 3);
    JoystickButton stowArmButton = new JoystickButton(main_stick, 4);

    JoystickButton grabOnButton = new JoystickButton(arm_stick, 1);
    JoystickButton letGoButton = new JoystickButton(arm_stick, 2);
    JoystickButton moveArmToFirstButton = new JoystickButton(arm_stick, 3);
    JoystickButton moveArmToSecondButton = new JoystickButton(arm_stick, 4);
    JoystickButton moveArmToThirdButton = new JoystickButton(arm_stick, 5);
    JoystickButton stowArmAtArmStickButton = new JoystickButton(arm_stick, 6);

    // Abuttons are for the second controller

    private void configureButtonBindings() {
        final ActiveBrakingCommandPID activeBrakingPID = new ActiveBrakingCommandPID(driveSubsystem,
                navigationSubsystem);
        InstantCommand openCloseArm = new InstantCommand(pnuematicsSubsystem::openCloseArm, pnuematicsSubsystem);
        final GrabOnCommand grabOnCommand = new GrabOnCommand(clawSubsystem, arm_stick);
        final LetGoCommand letGoCommand = new LetGoCommand(clawSubsystem, arm_stick);
        final AutoBalanceCommandPID autoBalanceCommandPID = new AutoBalanceCommandPID(driveSubsystem,
                navigationSubsystem);

        autoBalanceButton.onTrue(autoBalanceCommandPID);
        activeBreakingButton.whileTrue(activeBrakingPID);

        if (!Constants.IsTestRobot) {
            final ArmControlCommand armControl = new ArmControlCommand(armSubsystem, arm_stick);
            armSubsystem.setDefaultCommand(armControl);
            final MoveArmToAnglePositionCommand moveArmToSafe1 = new MoveArmToAnglePositionCommand(armSubsystem,
                    Constants.SafeAngle, Constants.SafePosition);
            final MoveArmToAnglePositionCommand moveArmToSafe2 = new MoveArmToAnglePositionCommand(armSubsystem,
                    Constants.SafeAngle, Constants.SafePosition);
            final MoveArmToAnglePositionCommand moveArmToSafe3 = new MoveArmToAnglePositionCommand(armSubsystem,
                    Constants.SafeAngle, Constants.SafePosition);
            final MoveArmToAnglePositionCommand extendArmTo3rd = new MoveArmToAnglePositionCommand(armSubsystem,
                    Constants.ThirdAngle, Constants.ThirdPosition);
            final MoveArmToAnglePositionCommand stowArmCommand1 = new MoveArmToAnglePositionCommand(armSubsystem,
                    Constants.StowedAngle, Constants.StowedPosition);
            final MoveArmToAnglePositionCommand stowArmCommand2 = new MoveArmToAnglePositionCommand(armSubsystem,
                    Constants.StowedAngle, Constants.StowedPosition);

            final MoveArmToAnglePositionCommand retractFullCommand1 = MoveArmToAnglePositionCommand
                    .buildPositionMover(armSubsystem, Constants.StowedPosition);
            final MoveArmToAnglePositionCommand retractFullCommand2 = MoveArmToAnglePositionCommand
                    .buildPositionMover(armSubsystem, Constants.StowedPosition);

            final MoveArmToAnglePositionCommand extendArmTo2nd = new MoveArmToAnglePositionCommand(armSubsystem,
                    Constants.SecondAngle, Constants.SecondPosition);
            final MoveArmToAnglePositionCommand extendArmTo1st = new MoveArmToAnglePositionCommand(armSubsystem,
                    Constants.FirstAngle, Constants.FirstPosition);

            var firstLevel = new ProxyCommand(() -> {
                if (armSubsystem.getRotationAngle() > 50) {
                    return extendArmTo1st;
                }
                return moveArmToSafe1.andThen(extendArmTo1st);
            });

            var secondLevel = new ProxyCommand(() -> {
                if (armSubsystem.getRotationAngle() > 50) {
                    return extendArmTo2nd;
                }
                return moveArmToSafe2.andThen(extendArmTo2nd);
            });

            var thirdLevel = new ProxyCommand(() -> {
                if (armSubsystem.getRotationAngle() > 50) {
                    return extendArmTo3rd;
                }
                return moveArmToSafe3.andThen(extendArmTo3rd);
            });

            stowArmButton.whileTrue(retractFullCommand1.andThen(stowArmCommand1));
            grabOnButton.whileTrue(grabOnCommand);
            letGoButton.whileTrue(letGoCommand);
            moveArmToFirstButton.whileTrue(firstLevel);
            moveArmToSecondButton.whileTrue(secondLevel);
            moveArmToThirdButton.whileTrue(thirdLevel);
            stowArmAtArmStickButton.whileTrue(retractFullCommand2.andThen(stowArmCommand2));
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
// annotation for testing github