// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Claw extends SubsystemBase {
    public CANSparkMax motor;
    public RelativeEncoder enc;
    public double currAngle;
    public SparkMaxPIDController m_pidController;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
    public final double max_increment = 100;

  /** Creates a new ExampleSubsystem. */
  public Claw(int id) {
    motor = new CANSparkMax(id, MotorType.kBrushless);
    motor.enableVoltageCompensation(12);
    enc = motor.getEncoder();
    m_pidController = motor.getPIDController();
    currAngle = 0;
    
    kP = 2; 
    kI = 0;
    kD = 0; 
    kIz = 0; 
    kFF = .00001; 
    kMaxOutput = 1.0/7; 
    kMinOutput = -1.0/7;

    m_pidController.setP(kP); 
    m_pidController.setI(kI);
    m_pidController.setD(kD);
    m_pidController.setIZone(kIz);
    m_pidController.setFF(kFF);
    m_pidController.setOutputRange(kMinOutput, kMaxOutput);
    enc.setPosition(0);
  }

  public void open(){
    m_pidController.setReference(-5, ControlType.kPosition);

  }

  public void close(){
    m_pidController.setReference(-22, ControlType.kPosition);  
  }
  public void closeCone(){
    m_pidController.setReference(-28, ControlType.kPosition);
  }

  // public void closeClaw(){
  //   m_pidController.setReference()
  // }

  

  public double getAngle(){
    return enc.getPosition();
  }

  // public void setTarget(double angleTarget){
  //   targetAngle = Rotation2d.fromDegrees(angleTarget);
  // }

  // public double getTarget(){
  //   return targetAngle.getDegrees();
  // }

  public void stop(){
    motor.set(0);
  }

  public void reset(){
    enc.setPosition(0);
  }

  public void updatePos(){
    currAngle = enc.getPosition();
  }
  
}
