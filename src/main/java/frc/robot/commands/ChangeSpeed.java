package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.SwerveDrive;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ChangeSpeed extends CommandBase {


  public ChangeSpeed() {
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {

    //changes speed of the arm movement !!!not tested
 


    //different stages of the arm wil move in different directions depending on what button on the controller is pressed
    //note: will need to implement counter movement in the first stage of the arm when moving the second stage
    //NEED TO TEST
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