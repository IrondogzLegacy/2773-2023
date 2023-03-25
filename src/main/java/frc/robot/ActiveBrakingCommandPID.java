package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Constants;
import edu.wpi.first.math.controller.PIDController;


public class ActiveBrakingCommandPID extends CommandBase {
    private final MainDriveSubsystem driveSubsystem;
    private final MainNavigationSubsystem navigationSubsystem;

    private final PIDController pid = new PIDController(0.5, 0, 0);
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

        pid.setSetpoint(StartDistance);
        //Goal is where the bot started
        pid.setTolerance(0.2);
    }
    private static final double ErrorDistance = 1./10;

    @Override
    public void execute() {
        var CurrentDistance = navigationSubsystem.getDistance();
        //Current Distance, not goal distance
        double DeltaDistance = CurrentDistance - startDistance; 
        //DeltaDistance is error from start. The error will be moved

        if (DeltaDistance > ErrorDistance)
        {
          driveSubsystem.driveLine(-Constants.AutoBrakingSpeed);
          System.out.println("Delta:" + DeltaDistance);
        }
        //If the error, which is DeltaDistance, is greater than the error threshold, which is ErrorDistance,
        //then the AutoBrakingSpeed will be used.

        if (DeltaDistance < -ErrorDistance)
        {
          driveSubsystem.driveLine(Constants.AutoBrakingSpeed);
          System.out.println("Delta:" + DeltaDistance);
        }
    }

    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}

