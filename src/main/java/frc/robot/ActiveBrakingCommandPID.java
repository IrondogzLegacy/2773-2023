package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Constants;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;


public class ActiveBrakingCommandPID extends CommandBase {
    private final MainDriveSubsystem driveSubsystem;
    private final MainNavigationSubsystem navigationSubsystem;

    private final PIDController activeBreakingPID = new PIDController(0.5, 0, 0);
    //Change the parameters to change how PID is calculated.

    public ActiveBrakingCommandPID(MainDriveSubsystem driveSubsystem, MainNavigationSubsystem navigationSubsystem) {
        this.driveSubsystem = driveSubsystem;
        this.navigationSubsystem = navigationSubsystem;
        addRequirements(driveSubsystem);
    }
    double startDistance;
    private double StartDistance;


    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        StartDistance = navigationSubsystem.getDistance();

        activeBreakingPID.setSetpoint(StartDistance);
        //Goal is where the bot started
    }

    @Override
    public void execute() {
    double speed = activeBreakingPID.calculate(navigationSubsystem.getDistance());
      speed = MathUtil.clamp(speed, -Constants.SpeedFactorLow, Constants.SpeedFactorLow);
      driveSubsystem.driveLine(speed);
    }

    @Override
    public void end(boolean interrupted) {
        driveSubsystem.stopAllDrive();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}

