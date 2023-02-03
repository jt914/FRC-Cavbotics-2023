// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants;

import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.*;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;




/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static SwerveDrive swerveDrive;
  public static final Arm arm = new Arm();
  public static double armSpeed = 0.04;
  public static ADIS16470_IMU gyro = new ADIS16470_IMU();
  public static final PhotonCamera camera = new PhotonCamera("photonvision"); //FIND TABLE NAME LATER


  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;

  }



}
