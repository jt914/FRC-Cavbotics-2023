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
    remote = RobotContainer.xboxController;

  }

  @Override
  public void execute() {
    SmartDashboard.putNumber("current", arm.stageOne.enc.getPosition());
    SmartDashboard.putNumber("desired", arm.stageOne.desiredAngle);

    arm.stageOne.currentAngle = arm.stageOne.enc.getPosition();
    arm.stageTwo.currentAngle = arm.stageTwo.enc.getPosition();

    //changes speed of the arm movement !!!not tested
    if(remote.getAButton()){
        arm.changeSpeed();
        
    }

    if(remote.getRightBumper()){
    if(arm.stageOne.currentAngle <= arm.stageOne.desiredAngle){
      arm.stageOne.motor.set(0.1);
    }
  }
  else if(remote.getLeftBumper()){
    if(arm.stageOne.currentAngle >= arm.stageOne.desiredAngle){
      arm.stageOne.motor.set(-0.1);
    }
  }
  if(remote.getRightBumperReleased()){
    arm.stageOne.motor.set(0);

  }

  if(remote.getLeftBumperReleased()){
    arm.stageOne.motor.set(0);

  }


  if(remote.getRightTriggerAxis() > 0.5){
    if(arm.stageTwo.currentAngle <= arm.stageTwo.desiredAngle){
      arm.stageTwo.motor.set(0.025);
    }
  }
  else if(remote.getRightTriggerAxis() > 0.5){
    if(arm.stageTwo.currentAngle >= arm.stageTwo.desiredAngle){
      arm.stageTwo.motor.set(0.025);
    }
  }
  if(remote.getRightTriggerAxis() < 0.1){
    arm.stageTwo.motor.set(0);

  }

  if(remote.getLeftTriggerAxis() < 0.1){
    arm.stageTwo.motor.set(0);

  }




    //different stages of the arm wil move in different directions depending on what button on the controller is pressed
    //note: will need to implement counter movement in the first stage of the arm when moving the second stage
    //NEED TO TEST
    if(remote.getRightBumper())
    {

      arm.stageOne.desiredAngle+=0.5;

    }
    if(remote.getLeftBumper())
    {
      arm.stageOne.desiredAngle-=0.5;
    }

    if(remote.getLeftTriggerAxis() > 0.5)
    {

      arm.stageTwo.desiredAngle+=0.5;

    }
    if(remote.getRightTriggerAxis() > 0.5)
    {
      arm.stageTwo.desiredAngle-=0.5;
    }
    arm.stageOne.desiredAngle = arm.stageOne.currentAngle;
    arm.stageTwo.desiredAngle = arm.stageTwo.currentAngle;




  }

  @Override
  public void end(boolean interrupted) {
    // NetworkTableInstance.getDefault().getTable("/limelight-sam").getEntry("ledMode").setDouble(1);
    arm.stopAll();
  }

  @Override
  public boolean isFinished() {
    return false; 
  }
}