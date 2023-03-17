package frc.robot.commands;

import java.lang.invoke.ConstantCallSite;

import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import com.revrobotics.CANSparkMax.ControlType;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

import frc.robot.subsystems.*;

public class cubeRamp extends CommandBase {
    private SwerveDrive swerveDrive;
    private Arm arm;
    private int step;
    public long time;
    private Claw claw;


    public cubeRamp(SwerveDrive swerve, Arm arm, Claw claw){
        swerveDrive = swerve;
        addRequirements(swerveDrive);
        this.arm = arm;
        swerveDrive.m_frontRightLocation.reset();
        swerveDrive.m_frontLeftLocation.reset();
        swerveDrive.m_backLeftLocation.reset();
        swerveDrive.m_backRightLocation.reset();
        step = 0;
    }

    @Override
    public void initialize(){

    }
    @Override
    public void execute(){

        
        switch(step){
             
            case 0:
            // if(claw.getAngle() <= 20){
            //     step = 1;
            // }
            // claw.m_pidController.setReference((claw.getAngle()-0.2), ControlType.kPosition);
            step = 1;

            case 1:
            if(arm.stageTwo.getAngle() >= -15.1){
                step = 2;
            }
            arm.stageTwo.m_pidController.setReference(arm.stageTwo.getAngle() -  0.3, ControlType.kPosition);


            case 2:

            if(arm.stageTwo.getAngle() <= 40 && arm.stageOne.getAngle() <= 30){
                step = 2;
            }

            arm.stageTwo.m_pidController.setReference(arm.stageOne.getAngle() + 0.2, ControlType.kPosition);
            arm.stageOne.m_pidController.setReference(arm.stageOne.getAngle() + 0.3, ControlType.kPosition);

            case 3:
            if(claw.getAngle() >= 20){
                step = 4;
            }
            claw.m_pidController.setReference((claw.getAngle()+0.2), ControlType.kPosition);


            case 4:
            if(swerveDrive.getDriveDistance() < 100){
                step = 5;
            }
            swerveDrive.updatePeriodic(0, 0.2, 0);


            // case 5:
            // if(System.currentTimeMillis() - time < 1000)
            // if(Math.abs(Constants.gyro.getXComplementaryAngle()) > 10){
            //     step = 2;
            // }
            //     swerveDrive.updatePeriodic(0, 0.15*((System.currentTimeMillis() - time)/500),0);
            // break;  

            // case 5:
            // if((System.currentTimeMillis() - time) < 1500 || Math.abs(Constants.gyro.getXComplementaryAngle()) < 5){
            //     step = 3;
            // }

            // else{
            //     swerveDrive.updatePeriodic(0, 0.5*((Math.abs(Constants.gyro.getXComplementaryAngle())/20)),0);
            // }
            // break; 

            // case 6:{
            //     if(Math.abs(Constants.gyro.getXComplementaryAngle()) > 5){
            //         swerveDrive.updatePeriodic(0, -0.1*((Math.abs(Constants.gyro.getXComplementaryAngle())/20)),0);
            //     }
            //     else{
            //         step = 9;
            //     }
            // }
            



            default:
            step = 9;
            break;
        }   
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){

        

        return step >= 9;
    }

    
    
}