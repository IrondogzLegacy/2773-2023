package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.event.BooleanEvent;
import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.ProxyCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Arm.ArmSubsystem;
import frc.robot.Arm.MoveArmToAnglePositionCommand;
import frc.robot.Arm.RetractCommand;
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
        private final ClawSubsystem clawSubsystem = new ClawSubsystem();

        // Autonomous Section
        private Command getAutonomousCommandScoreTop() {
                RetractCommand retractBack = new RetractCommand(armSubsystem);
                InstantCommand resetArmEncoders = new InstantCommand (armSubsystem::resetArmEncoders);
                var moveArmToSafe = new MoveArmToAnglePositionCommand(armSubsystem,
                                Constants.SafeAngle, Constants.SafePosition);
                var extendArmTo3rd = new MoveArmToAnglePositionCommand(armSubsystem,
                                Constants.ThirdAngle, Constants.ThirdPosition);

                var releaseAndHold = new ParallelRaceGroup(
                                new WaitCommand(1.5),
                                new LetGoCommand(clawSubsystem),
                                new MoveArmToAnglePositionCommand(armSubsystem,
                                                () -> Constants.ThirdAngle, () -> Constants.ThirdPosition, true));

                var pullBack = new MoveArmToAnglePositionCommand(armSubsystem, Constants.ThirdAngle,
                                Constants.SafePosition);
                var driveBack = new ParallelRaceGroup(
                                new WaitCommand(2),
                                new MoveDistanceCommand(driveSubsystem, navigationSubsystem, -6),
                                new MoveArmToAnglePositionCommand(armSubsystem,
                                                () -> Constants.SafePosition, () -> Constants.SafePosition, true));
                var retractArmBack = new ParallelRaceGroup(new WaitCommand(0.5), retractBack);
                return new SequentialCommandGroup(
                                retractArmBack,
                                resetArmEncoders,
                                moveArmToSafe,
                                extendArmTo3rd,
                                releaseAndHold,
                                pullBack);
        }

        private Command getAutonomousCommandAutoBalance() {
                InstantCommand driveSlow = new InstantCommand(driveSubsystem::driveSlow, driveSubsystem);
                final ActiveBrakingCommandPID activeBrakingPID = new ActiveBrakingCommandPID(driveSubsystem,
                                navigationSubsystem);
                final AutoBalanceCommandPID autoBalance = new AutoBalanceCommandPID(driveSubsystem,
                                navigationSubsystem);
                // AutoBalance requires gyro

                var moveUntilandAutoBalance = driveSlow
                                .until(() -> navigationSubsystem.getPitch() <= -1)
                                .andThen(activeBrakingPID);
                var moveOnCommand = new ParallelRaceGroup(
                                new WaitCommand(15),
                                moveUntilandAutoBalance);
                return moveOnCommand;
        }

        public Command getAutonomousCommand() {
                return getAutonomousCommandScoreTop();
                // return getAutonomousCommandScoreThird();
                // return getAutonomousCommandAutoBalance();
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
                final AutoBalanceCommandPID autoBalanceCommandPID = new AutoBalanceCommandPID(driveSubsystem,
                                navigationSubsystem);

                autoBalanceButton.whileTrue(autoBalanceCommandPID);
                activeBreakingButton.whileTrue(activeBrakingPID);

                if (!Constants.IsTestRobot) {
                        final ArmControlCommand armControl = new ArmControlCommand(armSubsystem, arm_stick);
                        armSubsystem.setDefaultCommand(armControl);
                        final MoveArmToAnglePositionCommand stowArmCommand1 = new MoveArmToAnglePositionCommand(
                                        armSubsystem,
                                        Constants.StowedAngle, Constants.StowedPosition);
                        final var retractFullCommand1 = MoveArmToAnglePositionCommand
                                        .buildPositionMover(armSubsystem, Constants.StowedPosition);
                        stowArmButton.whileTrue(retractFullCommand1.andThen(stowArmCommand1));

                        final MoveArmToAnglePositionCommand stowArmCommand2 = new MoveArmToAnglePositionCommand(
                                        armSubsystem,
                                        Constants.StowedAngle, Constants.StowedPosition);
                        final var retractFullCommand2 = MoveArmToAnglePositionCommand
                                        .buildPositionMover(armSubsystem, Constants.StowedPosition);
                        stowArmAtArmStickButton.whileTrue(retractFullCommand2.andThen(stowArmCommand2));

                        final GrabOnCommand grabOnCommand = new GrabOnCommand(clawSubsystem, arm_stick);
                        grabOnButton.whileTrue(grabOnCommand);
                        final LetGoCommand letGoCommand = new LetGoCommand(clawSubsystem);
                        letGoButton.whileTrue(letGoCommand);

                        var firstLevel = new ProxyCommand(() -> {
                                final MoveArmToAnglePositionCommand extendArmTo1st = new MoveArmToAnglePositionCommand(
                                                armSubsystem,
                                                Constants.FirstAngle, Constants.FirstPosition);
                                if (armSubsystem.getRotationAngle() > 50) {
                                        return extendArmTo1st;
                                }
                                final MoveArmToAnglePositionCommand moveArmToSafe1 = new MoveArmToAnglePositionCommand(
                                                armSubsystem,
                                                Constants.SafeAngle, Constants.SafePosition);
                                return moveArmToSafe1.andThen(extendArmTo1st);
                        });
                        moveArmToFirstButton.whileTrue(firstLevel);

                        var secondLevel = new ProxyCommand(() -> {
                                final MoveArmToAnglePositionCommand extendArmTo2nd = new MoveArmToAnglePositionCommand(
                                                armSubsystem,
                                                Constants.SecondAngle, Constants.SecondPosition);
                                if (armSubsystem.getRotationAngle() > 50) {
                                        return extendArmTo2nd;
                                }
                                final MoveArmToAnglePositionCommand moveArmToSafe2 = new MoveArmToAnglePositionCommand(
                                                armSubsystem,
                                                Constants.SafeAngle, Constants.SafePosition);
                                return moveArmToSafe2.andThen(extendArmTo2nd);
                        });
                        moveArmToSecondButton.whileTrue(secondLevel);

                        var thirdLevel = new ProxyCommand(() -> {
                                final MoveArmToAnglePositionCommand extendArmTo3rd = new MoveArmToAnglePositionCommand(
                                                armSubsystem,
                                                Constants.ThirdAngle, Constants.ThirdPosition);
                                if (armSubsystem.getRotationAngle() > 50) {
                                        return extendArmTo3rd;
                                }
                                final MoveArmToAnglePositionCommand moveArmToSafe3 = new MoveArmToAnglePositionCommand(
                                                armSubsystem,
                                                Constants.SafeAngle, Constants.SafePosition);
                                return moveArmToSafe3.andThen(extendArmTo3rd);
                        });
                        moveArmToThirdButton.whileTrue(thirdLevel);
                }
        }
}

// annotation for testing github