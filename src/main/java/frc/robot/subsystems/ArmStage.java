// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmStage extends SubsystemBase {
    public CANSparkMax motor;
    public double currentAngle, desiredAngle;
    public RelativeEncoder enc;
    public SparkMaxPIDController m_pidController;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
  



  /** Creates a new ExampleSubsystem. */
  public ArmStage(int id) {
    motor = new CANSparkMax(id, MotorType.kBrushless);
    motor.enableVoltageCompensation(12);
    enc = motor.getEncoder();
    enc.setPositionConversionFactor(1);
    motor.setSmartCurrentLimit(80, 80);
    m_pidController = motor.getPIDController();
    currentAngle = enc.getPosition();
    desiredAngle = currentAngle;
    
    kP = 0.03; 
    kI = 0;
    kD = 0; 
    kIz = 0; 
    kFF = .00001; 
    kMaxOutput = 1.0/6; 
    kMinOutput = -1.0/6;

    m_pidController.setP(kP); 
    m_pidController.setI(kI);
    m_pidController.setD(kD);
    m_pidController.setIZone(kIz);
    m_pidController.setFF(kFF);
    m_pidController.setOutputRange(kMinOutput, kMaxOutput);


    

    //motor.setIdleMode(IdleMode.kBrake);
  }

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
    
  }

  public void updatePos(){
    currentAngle = enc.getPosition();
  }



}
