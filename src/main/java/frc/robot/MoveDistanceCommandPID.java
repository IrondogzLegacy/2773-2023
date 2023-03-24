// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.controller.PIDController;

public class MoveDistanceCommandPID extends CommandBase {
    private final MainNavigationSubsystem navigationSubsystem;
    private final MainDriveSubsystem driveSubsystem;
    private final double distance;

    private final PIDController pid = new PIDController(0.5, 0, 0);

    public MoveDistanceCommandPID(MainDriveSubsystem driveSubsystem, MainNavigationSubsystem navigationSubsystem,
            double distance) {
        this.navigationSubsystem = navigationSubsystem;
        this.driveSubsystem = driveSubsystem;
        this.distance = distance;
        addRequirements(driveSubsystem);
        // Use addRequirements() here to declare subsystem dependencies.
    }

    private double StartDistance;
    private double StopDistance;

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        StartDistance = navigationSubsystem.getDistance();
        StopDistance = StartDistance + distance;
        // Goal = Start + Action
        pid.setSetpoint(StopDistance);
        pid.setTolerance(0.1);
        /*
         * //Tolerance is the amount of difference the robot will allow between the goal
         * //location when finished
         * //and the actual location when finished.
         */
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        var speed = pid.calculate(navigationSubsystem.getDistance());
        // Speed is based on the distance left. Longer distance creates higher speed and
        // vice versa.

        speed = Math.min(speed, Constants.SpeedFactor);
        speed = Math.max(speed, -Constants.SpeedFactor);

        driveSubsystem.driveLine(speed);
        // Above line is the line that actually moves the robot based on the speed.
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        driveSubsystem.stopAllDrive();
        // stop
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return pid.atSetpoint();
    }
}
