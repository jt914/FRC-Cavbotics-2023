package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmStage;
import frc.robot.subsystems.SwerveDrive;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveArm extends CommandBase {
    private double desired;
    private ArmStage arm;
    private boolean forward;


  public MoveArm(double desired, ArmStage arm, boolean forward) {
    this.desired = desired;
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    System.out.println("moving");
    if(forward){
        arm.motor.set(0.5);
    }
    else if(!forward){
        arm.motor.set(-0.5);
    }


  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    if(forward && arm.currentAngle <= desired){
        return true;
    }else if((!forward) && arm.currentAngle >= desired){
        return true;
    }
    return false;

  }
}