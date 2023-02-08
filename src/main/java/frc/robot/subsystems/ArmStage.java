// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmStage extends SubsystemBase {
    private CANSparkMax motor;
    public Rotation2d currentAngle;
    public Rotation2d targetAngle;
    public RelativeEncoder enc;


  /** Creates a new ExampleSubsystem. */
  public ArmStage(int id, int startingAngle) {
    motor = new CANSparkMax(id, MotorType.kBrushless);
    motor.enableVoltageCompensation(12);
    enc = motor.getEncoder();
    enc.setPositionConversionFactor(1);
    motor.setSmartCurrentLimit(80, 80);
    currentAngle = new Rotation2d(startingAngle);
    targetAngle = new Rotation2d(startingAngle);
    //motor.setIdleMode(IdleMode.kBrake);
  }

  public double getAngle(){
    return enc.getPosition();
  }

  public void setTarget(double angleTarget){
    targetAngle = Rotation2d.fromDegrees(angleTarget);
  }

  public double getTarget(){
    return targetAngle.getDegrees();
  }

  public void stop(){
    motor.set(0);
  }



}
