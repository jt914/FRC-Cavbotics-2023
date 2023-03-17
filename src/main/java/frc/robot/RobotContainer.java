
package frc.robot;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.*;

import java.lang.invoke.ConstantCallSite;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.*;
import frc.robot.Constants;


public class RobotContainer {

  public ADIS16470_IMU gyro = Constants.gyro;

  public static XboxController swerveController, armController;

  public static double speed;

  public RobotContainer() {
    speed = 0.5;
    swerveController = new XboxController(0);
    armController = new XboxController(1);
    configureBindings();
  }


  private void configureBindings() {
    new JoystickButton(swerveController, XboxController.Button.kStart.value).onTrue(new SwerveCommand(Constants.swerveDrive));
    new JoystickButton(armController, XboxController.Button.kStart.value).onTrue(new ArmCommand(Constants.arm));
    new JoystickButton(armController, XboxController.Button.kStart.value).onTrue(new ClawCommand(Constants.claw));
  }



  public Command getAutonomousCommand() {
      return new auto(Constants.swerveDrive, Constants.arm, Constants.claw);
  }
}
