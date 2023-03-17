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
            if(claw.getAngle() <= 20){
                step = 1;
            }
            claw.m_pidController.setReference(20, ControlType.kPosition);

            case 1:
            if(arm.stageOne.getAngle() >= 0.5){
                step = 2;
            }
            arm.stageOne.m_pidController.setReference(0.5, ControlType.kPosition);


            case 2:

            if(arm.stageTwo.getAngle() <= 40){
                step = 3;
            }

            arm.stageTwo.m_pidController.setReference(40, ControlType.kPosition);

            case 3:
            if(claw.getAngle() >= 20){
                step = 4;
            }
            claw.m_pidController.setReference(20, ControlType.kPosition);


            case 4:
            if(swerveDrive.getDriveDistance() < 100){
                step = 9;
            }
            swerveDrive.updatePeriodic(0, 0.2, 0);



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