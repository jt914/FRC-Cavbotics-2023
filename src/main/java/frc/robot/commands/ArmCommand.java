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

    if(remote.getAButton()){
        arm.changeSpeed();
        
    }

    if(remote.getRightBumper())
    {
        arm.stageOne.rotations += .01;
        arm.stageOne.m_pidController.setReference(arm.stageOne.rotations, CANSparkMax.ControlType.kPosition);    

    }
    if(remote.getLeftBumper())
    {
        arm.stageOne.rotations -= .01;
        arm.stageOne.m_pidController.setReference(arm.stageOne.rotations, CANSparkMax.ControlType.kPosition);    

    }
    if(remote.getRightTriggerAxis() > 0.1)
    {
        //can modify change speed to accept an input and multiply that by the trigger axis
        arm.stageTwo.rotations += .01; 
        arm.stageTwo.m_pidController.setReference(arm.stageTwo.rotations, CANSparkMax.ControlType.kPosition);    

    }
    if(remote.getLeftTriggerAxis() > 0.1)
    {
        arm.stageTwo.rotations -= .01;
        arm.stageTwo.m_pidController.setReference(arm.stageTwo.rotations, CANSparkMax.ControlType.kPosition);    

    }






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