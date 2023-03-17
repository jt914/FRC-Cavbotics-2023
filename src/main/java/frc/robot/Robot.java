
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


  public class Robot extends TimedRobot {
    public ADIS16470_IMU gyro1 = Constants.gyro;
    private Command m_autonomousCommand;
    public static XboxController xboxController;
    public AHRS gyro2;

    private RobotContainer m_robotContainer;
    @Override
    public void robotInit() {
      Constants.arm.stageOne.reset();
      Constants.arm.stageTwo.reset();


      m_robotContainer = new RobotContainer();

    }

    @Override
    public void robotPeriodic() {
      
      CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void autonomousInit() {
      m_autonomousCommand = m_robotContainer.getAutonomousCommand();

      if (m_autonomousCommand != null) {
        m_autonomousCommand.schedule();
      }
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {

      if (m_autonomousCommand != null) {
        m_autonomousCommand.cancel();
      }
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testInit() {
      CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {}

    @Override
    public void simulationInit() {}

    @Override
    public void simulationPeriodic() {}
  }