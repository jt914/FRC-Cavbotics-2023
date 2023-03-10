package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.SwerveDrive;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SwerveCommand extends CommandBase {
  private static SwerveDrive swerveDrive;
  private static XboxController remote;

  public SwerveCommand(SwerveDrive drive) {
    swerveDrive = drive;
  }

  @Override
  public void initialize() {
    remote = RobotContainer.xboxController;

  }

  @Override
  public void execute() {
    System.out.println("wasd");




    if (Math.abs(remote.getLeftX()) >= 0.1 || Math.abs(remote.getLeftY()) >= 0.1
        || Math.abs(remote.getRightX()) >= 0.1) {
      swerveDrive.updatePeriodic(remote.getLeftX(), remote.getLeftY(), remote.getRightX());
    } else {
      swerveDrive.stopAll();
    }


    if(remote.getXButtonPressed()){

    if(Constants.swerveDrive.MAX_SPEED == 2){
      Constants.swerveDrive.MAX_SPEED = 0.2;
    }else{
      Constants.swerveDrive.MAX_SPEED = 2;
    }
  }



  }

  @Override
  public void end(boolean interrupted) {
    // NetworkTableInstance.getDefault().getTable("/limelight-sam").getEntry("ledMode").setDouble(1);
    NetworkTableInstance.getDefault().getTable("/datatable").getEntry("SwerveCommand").setBoolean(false);
    swerveDrive.stopAll();
  }

  @Override
  public boolean isFinished() {
    return false; 
  }
}