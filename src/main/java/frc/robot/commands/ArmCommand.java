package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.SwerveDrive;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArmCommand extends CommandBase {
  private Arm arm;
  private static XboxController remote;


  public ArmCommand(Arm arm) {
    this.arm = arm;
  }

  @Override
  public void initialize() {
    remote = RobotContainer.swerveController;
  }

  @Override
  public void execute() {
    if(remote.getLeftY() < 0 && Math.abs(remote.getLeftY()) > 0.15){
      if(arm.stageOne.incrementDown(1)){
        arm.stageTwo.incrementDown(0.8);
      }
      arm.stageOne.incrementDown(1);


    } else if (remote.getLeftY() > 0 && Math.abs(remote.getLeftY()) > 0.15){
      if(arm.stageOne.incrementUp(1)){
        arm.stageTwo.incrementUp(0.8);
      }
      arm.stageOne.incrementUp(1);

    }

    if(remote.getRightY() > 0 && Math.abs(remote.getRightY()) > 0.15){
      arm.stageTwo.incrementDown(1.2);
    } else if (remote.getRightY() < 0 && Math.abs(remote.getRightY()) > 0.15){
      arm.stageTwo.incrementUp(1.2);
    }
  }

  @Override
  public void end(boolean interrupted) {
    arm.stopAll();
  }

  @Override
  public boolean isFinished() {
    return false; 
  }
}