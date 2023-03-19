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

public class auto extends CommandBase {
    private SwerveDrive swerveDrive;
    private Arm arm;
    private int step;
    public long time;
    private Claw claw;


    public auto(SwerveDrive swerve, Arm arm, Claw claw){
        swerveDrive = swerve;
        addRequirements(swerveDrive);
        this.arm = arm;
        this.claw = claw;
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

    
            if(step == 0){
            if(claw.getAngle() <= -19){
                System.out.println("pased step 0");
                step = 1;
            }
            claw.m_pidController.setReference(-23, ControlType.kPosition);
            }
            if(step == 1){
            if(arm.stageTwo.getAngle() >= 14.6){
                step = 2;
                System.out.println("pased step 1");

            }
            arm.stageTwo.m_pidController.setReference(14.7, ControlType.kPosition);
        }

            else if(step == 2){

            if(arm.stageOne.getAngle() <= -10.9){
                step = 3;
                System.out.println("pased step 2");
            }
            

            arm.stageOne.m_pidController.setReference(-11, ControlType.kPosition);
        }
            else if(step == 3){
            if(claw.getAngle() >= -6){
                step = 4;
                System.out.println("pased step 3");



            }
            claw.open();
        }

        else if(step == 4){
            if(arm.stageOne.getAngle() >= -2){
                step = 5;
                System.out.println("pased step 2");
            }
            

            arm.stageOne.m_pidController.setReference(0, ControlType.kPosition);


        }
        else if(step == 5){
            if(arm.stageTwo.getAngle() >= -4){
                step = 6;
                System.out.println("pased step 1");
                time = System.currentTimeMillis();


            }
            arm.stageTwo.m_pidController.setReference(0, ControlType.kPosition);
        }


            else if(step == 6){
                swerveDrive.updatePeriodic(0, .4, 0);
                if((System.currentTimeMillis() - time) > 7000){
                    swerveDrive.stopAll();
                    step = 9;
                }
        }
    }

    @Override
    public void end(boolean interrupted){
        swerveDrive.stopAll();

    }

    @Override
    public boolean isFinished(){

        

        return step >= 9;

    }

    
    
}