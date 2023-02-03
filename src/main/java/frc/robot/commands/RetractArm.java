package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.ArmStage;
import frc.robot.subsystems.SwerveDrive;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RetractArm extends CommandBase {
  private static ArmStage armStage;
  private Arm arm;

  public RetractArm(Arm arm, int stage) {

    armStage = arm.stageOne;
    if(stage == 2){
      armStage = arm.stageTwo;
    }
    this.arm = arm;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    armStage.set(-RobotContainer.speed);
  }

  @Override
  public void end(boolean interrupted) {
    NetworkTableInstance.getDefault().getTable("/datatable").getEntry("SwerveCommand").setBoolean(false);

    arm.stopAll();
  
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}