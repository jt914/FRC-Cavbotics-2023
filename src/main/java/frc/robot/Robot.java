  // Copyright (c) FIRST and other WPILib contributors.
  // Open Source Software; you can modify and/or share it under the terms of
  // the WPILib BSD license file in the root directory of this project.

  package frc.robot;

  import java.text.DecimalFormat;

import org.photonvision.PhotonUtils;

import com.kauailabs.navx.frc.AHRS;

  import edu.wpi.first.wpilibj.ADIS16470_IMU;
  import edu.wpi.first.wpilibj.TimedRobot;
  import edu.wpi.first.wpilibj.XboxController;
  import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
  import edu.wpi.first.wpilibj2.command.Command;
  import edu.wpi.first.wpilibj2.command.CommandScheduler;
  import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


  /**
   * The VM is configured to automatically run this class, and to call the functions corresponding to
   * each mode, as described in the TimedRobot documentation. If you change the name of this class or
   * the package after creating this project, you must also update the build.gradle file in the
   * project.
   */
  public class Robot extends TimedRobot {
    public ADIS16470_IMU gyro1 = Constants.gyro;
    private Command m_autonomousCommand;
    public static XboxController xboxController;
    public AHRS gyro2;

    public Robot(){
      // addPeriodic(() -> {
      //   // SmartDashboard.putNumber("ADIS", gyro1.getXComplementaryAngle());
      //   // System.out.println("ADIS" +  gyro1.getXComplementaryAngle());

    

      //   // System.out.println("Gyro 1: x: "+  
      //   // new DecimalFormat("#.##").format(Math.abs(gyro1.getAccelX())) + "\t y: " + 
      //   // new DecimalFormat("#.##").format(Math.abs(gyro1.getAccelY())) + "\t gyro2: x:" + 
      //   // new DecimalFormat("#.##").format(Math.abs(gyro2.getWorldLinearAccelX())) + "\t y:" + 
      //   // new DecimalFormat("#.##").format(Math.abs(gyro2.getWorldLinearAccelY())));



      //   // gyro1.configDecRate(0);

      //   // SmartDashboard.putNumber("accel2X", gyro2.getWorldLinearAccelX());
      //   // SmartDashboard.putNumber("accel2Y", gyro2.getWorldLinearAccelY());


      // }, 0.005, 9);


    }

  //add periodic function figuring out swerve module angle
    private RobotContainer m_robotContainer;

    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
      // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
      // autonomous chooser on the dashboard.

      m_robotContainer = new RobotContainer();

    }

    /**
     * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
     * that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
      // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
      // commands, running already-scheduled commands, removing finished or interrupted commands,
      // and running subsystem periodic() methods.  This must be called from the robot's periodic
      // block in order for anything in the Command-based framework to work.


      // if(Constants.camera.getLatestResult().hasTargets() && Constants.camera.getLatestResult().getBestTarget().getPoseAmbiguity() <= 0.2){

      // }
    }

    /** This function is called once each time the robot enters Disabled mode. */
    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
    @Override
    public void autonomousInit() {
      m_autonomousCommand = m_robotContainer.getAutonomousCommand();

      // schedule the autonomous command (example)
      if (m_autonomousCommand != null) {
        m_autonomousCommand.schedule();
      }
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
      CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
      // This makes sure that the autonomous stops running when
      // teleop starts running. If you want the autonomous to
      // continue until interrupted by another command, remove
      // this line or comment it out.

      Constants.arm.stageOne.reset();
      try {
        gyro2 = new AHRS(); 
        gyro2.reset();
      } catch (RuntimeException ex ) {
          System.out.println("--------------");
          System.out.println("NavX not plugged in");
          System.out.println("--------------");
      }
    
      gyro1.calibrate();
      gyro1.configDecRate(0);

      xboxController = RobotContainer.xboxController;
      if (m_autonomousCommand != null) {
        m_autonomousCommand.cancel();
      }
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
      Constants.arm.stageOne.reset();

      // var result = Constants.camera.getLatestResult();

      // if(result.hasTargets()){
      //   System.out.println(result.getBestTarget().get());
      // }

    }

    @Override
    public void testInit() {
      // Cancels all running commands at the start of test mode.
      CommandScheduler.getInstance().cancelAll();
    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {}

    /** This function is called once when the robot is first started up. */
    @Override
    public void simulationInit() {}

    /** This function is called periodically whilst in simulation. */
    @Override
    public void simulationPeriodic() {}
  }
