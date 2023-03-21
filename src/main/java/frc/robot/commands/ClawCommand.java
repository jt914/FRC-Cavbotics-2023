package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.SwerveDrive;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClawCommand extends CommandBase {
  private Claw claw;
  private static XboxController remote;
  boolean coast = false;


  public ClawCommand(Claw claw) {
    this.claw = claw;
  }

  @Override
  public void initialize() {
    remote = RobotContainer.xboxController2;
  }

  @Override
  public void execute() {
    if(remote.getLeftBumperPressed()){
        claw.close();

    } else if (remote.getRightBumperPressed()){
        claw.open();
    } else if (remote.getBButtonPressed())
    {
        claw.closeCone();
    }
    else if(remote.getXButtonPressed()){
      claw.enc.setPosition(0);

    }
  }
  @Override
  public void end(boolean interrupted) {
    // NetworkTableInstance.getDefault().getTable("/limelight-sam").getEntry("ledMode").setDouble(1);
  }

  @Override
  public boolean isFinished() {
    return false; 
  }
}