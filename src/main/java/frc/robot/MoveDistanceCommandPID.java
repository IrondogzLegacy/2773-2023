package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.controller.PIDController;

public class MoveDistanceCommandPID extends CommandBase {
    private final MainNavigationSubsystem navigationSubsystem;
    private final MainDriveSubsystem driveSubsystem;
    private final double distance;

    private final PIDController pid = new PIDController (1, 0, 0);

    public MoveDistanceCommandPID(MainDriveSubsystem driveSubsystem, MainNavigationSubsystem navigationSubsystem, 
    double distance) { 
    this.navigationSubsystem = navigationSubsystem;
    this.driveSubsystem = driveSubsystem;
    this.distance = distance;
    addRequirements(driveSubsystem);
    }
    private double StartDistance;
    private double StopDistance;

    @Override
    public void initialize() { 
        StartDistance = navigationSubsystem.getDistance();
        StopDistance = StartDistance + distance;
        pid.setSetpoint(StopDistance);
        pid.setTolerance(2);
    }

    @Override
    public void execute() { 
        var speed = pid.calculate(navigationSubsystem.getDistance());

        speed = Math.min(speed, Constants.SpeedFactorLow);
        speed = Math.max(speed,-Constants.SpeedFactor);

        driveSubsystem.driveLine(speed);
    }
// Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        driveSubsystem.stopAllDrive();
        //stop
    }
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return pid.atSetpoint();
  }
}
