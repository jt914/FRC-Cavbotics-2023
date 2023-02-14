package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.*;


//not currently in use because of the way the axis are controlled, but
//potential future use if we need to set simultaneous setment to control both
//stages to set in one axis
public class Arm extends SubsystemBase{


  public ArmStage stageOne;
  public ArmStage stageTwo;


  public Arm() {
    stageOne = new ArmStage(8, 0);
    stageTwo = new ArmStage(9, 0);
  }

  public void changeSpeed(){
    if(stageOne.kP == 0.0001){
      stageOne.kP = 0.00015;
      stageTwo.kP = 0.00015;
    }else{
      stageOne.kP = 0.0001;
      stageTwo.kP = 0.0001;

    }
  }

  public void stopAll(){
    stageOne.stop();
    stageTwo.stop();
  }


}