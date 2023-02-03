package frc.robot.commands;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ChangeSpeed extends CommandBase {
  public ChangeSpeed() {
  }

  @Override
  public void initialize() {
    if(Constants.armSpeed == 0.1){
        Constants.armSpeed = 0.5;
    }else{
        Constants.armSpeed = 0.1;
    }

  }

  @Override
  public void execute() {
        
    
  }

  @Override
  public void end(boolean interrupted) {
  
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}