package frc.robot.subsystems;

import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.XboxController;

public class SwerveDrive extends SubsystemBase{

  private SwerveModuleState[] moduleState;
  private ChassisSpeeds speeds;
  private SwerveDriveKinematics kinematics;
  public SwerveModule m_frontRightLocation;
  public SwerveModule m_frontLeftLocation;
  public SwerveModule m_backLeftLocation;
  public SwerveModule m_backRightLocation;
  public double MAX_SPEED;
  public double MAX_RADIANS;
  public AHRS gyro;
  public CommandXboxController remote;
  private static XboxController remote1;
  //public int startingPos;
  // public SwerveDriveOdometry m_Odometry;

  


  public SwerveDrive(double distanceFromOrigin) {

    remote1 = RobotContainer.xboxController;

    // (Y,X) format
    Translation2d frontRightLocation = new Translation2d(distanceFromOrigin, -distanceFromOrigin);
    Translation2d frontLeftLocation = new Translation2d(distanceFromOrigin, distanceFromOrigin);
    Translation2d backLeftLocation = new Translation2d(-distanceFromOrigin, distanceFromOrigin);
    Translation2d backRightLocation = new Translation2d(-distanceFromOrigin, -distanceFromOrigin);


    kinematics = new SwerveDriveKinematics(frontRightLocation, frontLeftLocation, backLeftLocation, backRightLocation);

    MAX_SPEED = 2.4;//4
    MAX_RADIANS = 4.3;//5

    moduleState = new SwerveModuleState[4];
    //startingPos = starting;

    m_frontRightLocation = new SwerveModule(5, 6);
    m_frontLeftLocation = new SwerveModule(3, 4);
    m_backLeftLocation = new SwerveModule(2, 1);
    m_backRightLocation = new SwerveModule(8, 7);

    
    m_frontRightLocation.reset();
    m_frontLeftLocation.reset();
    m_backLeftLocation.reset();
    m_backRightLocation.reset();

    

    try {
      gyro = new AHRS(SPI.Port.kMXP); 
      gyro.reset();
    } catch (RuntimeException ex ) {
        System.out.println("--------------");
        System.out.println("NavX not plugged in");
        System.out.println("--------------");
    }

      }


  public void setBrake(){
    m_frontLeftLocation.setBrake();
    m_frontRightLocation.setBrake();
    m_backLeftLocation.setBrake();
    m_backRightLocation.setBrake();
  }

  public void setCoast(){
    m_frontLeftLocation.setCoast();
    m_frontRightLocation.setCoast();
    m_backLeftLocation.setCoast();
    m_backRightLocation.setCoast();
  }
  public void updatePeriodic(double translateY, double translateX, double yaw) {

    speeds = new ChassisSpeeds(translateY * MAX_SPEED, translateX * MAX_SPEED, yaw * MAX_RADIANS);

    moduleState = kinematics.toSwerveModuleStates(speeds);

    var optimized1 = SwerveModuleState.optimize(moduleState[0], m_frontRightLocation.currentAngle);
    var optimized2 = SwerveModuleState.optimize(moduleState[1], m_frontLeftLocation.currentAngle);
    var optimized3 = SwerveModuleState.optimize(moduleState[2], m_backLeftLocation.currentAngle);
    var optimized4 = SwerveModuleState.optimize(moduleState[3], m_backRightLocation.currentAngle);

    

    m_frontRightLocation.setModule(optimized1.angle, optimized1.speedMetersPerSecond);
    m_frontLeftLocation.setModule(optimized2.angle, optimized2.speedMetersPerSecond);
    m_backRightLocation.setModule(optimized4.angle, optimized4.speedMetersPerSecond);
    m_backLeftLocation.setModule(optimized3.angle, optimized3.speedMetersPerSecond);
  }
  public double getDriveDistance(){
    return m_frontLeftLocation.getDrive();
  }


  public void resetDrive(){
    m_frontRightLocation.driveRest();
    m_frontLeftLocation.driveRest();
    m_backLeftLocation.driveRest();
    m_backRightLocation.driveRest();
  }

  public double getGyroAngle(){
    double angle = gyro.getAngle();
    return (angle >360) ? angle % 360 : angle;
  }
  public void stopAll(){
    m_frontRightLocation.stop();
    m_frontLeftLocation.stop();
    m_backLeftLocation.stop();
    m_backRightLocation.stop();
  }

  public void setPID(double p, double i, double d){
    m_frontLeftLocation.setPID(p, i, d);
    m_frontRightLocation.setPID(p, i, d);
    m_backLeftLocation.setPID(p, i, d);
    m_backRightLocation.setPID(p, i, d);
  }
}