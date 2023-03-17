package frc.robot.commands;

import java.lang.invoke.ConstantCallSite;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

import frc.robot.subsystems.*;

public class ramp extends CommandBase {
    private SwerveDrive swerveDrive;
    private int step;
    public long time;

    public ramp(SwerveDrive swerve){
        swerveDrive = swerve;
        addRequirements(swerveDrive);
        swerveDrive.m_frontRightLocation.reset();
        swerveDrive.m_frontLeftLocation.reset();
        swerveDrive.m_backLeftLocation.reset();
        swerveDrive.m_backRightLocation.reset();
        step = 0;
    }

    @Override
    public void initialize(){
        System.out.println("initialize");
        time = System.currentTimeMillis();




        //claw.reset
        //SmartDashboard.putNumber("Auto works", 1);
        //System.out.println("Wotks 2");
    }
    @Override
    public void execute(){
        System.out.println("working");

        
        
        
        switch(step){
            
            
            case 0:
            if(Math.abs(Constants.gyro.getXComplementaryAngle()) > 10){
                step = 1;
                time = System.currentTimeMillis();

            }

            else{
                swerveDrive.updatePeriodic(0, 0.15*((System.currentTimeMillis() - time)/500),0);
            }
            break;  

            case 1:
            if((System.currentTimeMillis() - time) < 1500 || Math.abs(Constants.gyro.getXComplementaryAngle()) < 5){
                step = 2;
            }

            else{
                swerveDrive.updatePeriodic(0, 0.5*((Math.abs(Constants.gyro.getXComplementaryAngle())/20)),0);
            }
            break; 

            case 2:{
                if(Math.abs(Constants.gyro.getXComplementaryAngle()) > 5){
                    swerveDrive.updatePeriodic(0, -0.1*((Math.abs(Constants.gyro.getXComplementaryAngle())/20)),0);
                }
                else{
                    step = 9;
                }
            }
            



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