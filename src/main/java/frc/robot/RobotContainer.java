
package frc.robot;

import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.*;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.*;
import frc.robot.Constants;


public class RobotContainer {


  // public static Arm arm = Constants.arm;

  public ADIS16470_IMU gyro = Constants.gyro;

  public static XboxController xboxController;

  public static double speed;

  public RobotContainer() {
    speed = 0.5;
    xboxController = new XboxController(0);

    configureBindings();
  }


  private void configureBindings() {

    //starting is which auto pos its starting. Since this if statement will only run if auto has not been run before, we set it as 4 because we do not know the position
    //of the robot.

    if(Constants.swerveDrive == null){
      Constants.swerveDrive = new SwerveDrive(0.2923, 4);
    }



    //left is out right is back
    new JoystickButton(xboxController, XboxController.Button.kStart.value).onTrue(new SwerveCommand(Constants.swerveDrive));
    // new JoystickButton(xboxController, XboxController.Button.kB.value).onTrue(new Score(Constants.arm));
    
    
    // new JoystickButton(xboxController, XboxController.Button.kBack.value).onTrue(new ArmCommand(Constants.arm));


    // new JoystickButton(xboxController, XboxController.Button.kBack.value).whenPressed(getAutonomousCommand())

    // new JoystickButton(xboxController, XboxController.Button.kLeftBumper.value).whileTrue(new RetractArm(arm, 1));
    // new JoystickButton(xboxController, XboxController.Button.kRightBumper.value).whileTrue(new ExtendArm(arm, 1));
    
    


    // new JoystickButton(xboxController, XboxController.Button.kX.value).toggleOnTrue(new ChangeSpeed());


  }



  public Command getAutonomousCommand() {
    int routine = (int)(NetworkTableInstance.getDefault().getTable("/datatable").getEntry("routine").getDouble(0));
    return null;
    // 0 is top, 1 is middle, 2 is bottom

    // if(routine == 0){
    //   Constants.swerveDrive = new SwerveDrive(0.2923, 0);
    //   return new RampAuto(arm, Constants.swerveDrive);
    // }else if(routine == 1){
    //   Constants.swerveDrive = new SwerveDrive(0.2923,1);
    //   return new RampAuto(arm, Constants.swerveDrive);
    // }else if(routine == 2){
    //   Constants.swerveDrive = new SwerveDrive(0.2923, 2);
    //   return new RampAuto(arm, Constants.swerveDrive);
    // }else{
    //   return null;
    // }
  }
}
