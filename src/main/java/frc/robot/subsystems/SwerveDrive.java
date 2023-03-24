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

    // (X, Y) format
    Translation2d frontRightLocation = new Translation2d(distanceFromOrigin, -distanceFromOrigin);
    Translation2d frontLeftLocation = new Translation2d(distanceFromOrigin, distanceFromOrigin);
    Translation2d backLeftLocation = new Translation2d(-distanceFromOrigin, distanceFromOrigin);
    Translation2d backRightLocation = new Translation2d(-distanceFromOrigin, -distanceFromOrigin);


    kinematics = new SwerveDriveKinematics(frontRightLocation, frontLeftLocation, backLeftLocation, backRightLocation);

    MAX_SPEED = 2;//4
    MAX_RADIANS = 4;//5

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

    
    // m_Odometry = new SwerveDriveOdometry(kinematics, gyro.getRotation2d(), new SwerveModulePosition[]{
    //   m_frontLeftLocation.getPosition(),
    //   m_frontRightLocation.getPosition(),
    //   m_backLeftLocation.getPosition(),
    //   m_backRightLocation.getPosition()
    // }, getStarting());

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

    

    // m_frontRightLocation.setModule(optimized1.angle, optimized1.speedMetersPerSecond);
    // m_frontLeftLocation.setModule(optimized2.angle, optimized2.speedMetersPerSecond);
    // m_backRightLocation.setModule(optimized4.angle, optimized4.speedMetersPerSecond);
    // m_backLeftLocation.setModule(optimized3.angle, optimized3.speedMetersPerSecond);

        m_frontRightLocation.setModule(moduleState[0].angle, optimized1.speedMetersPerSecond);
    if(Math.abs(remote1.getRightX()) >= 0.1){
      m_frontLeftLocation.setModule(moduleState[1].angle, -optimized2.speedMetersPerSecond);
      m_backRightLocation.setModule(moduleState[3].angle, -optimized4.speedMetersPerSecond);
    } else {
      m_frontLeftLocation.setModule(moduleState[1].angle, optimized2.speedMetersPerSecond);
      m_backRightLocation.setModule(moduleState[3].angle, optimized4.speedMetersPerSecond);
    }
    m_backLeftLocation.setModule(moduleState[2].angle, optimized3.speedMetersPerSecond);



    // PhotonPipelineResult result = Constants.camera.getLatestResult();
    // PhotonTrackedTarget bestTarget = result.getBestTarget();

    // if(result.hasTargets() && result.getBestTarget().getPoseAmbiguity() <= 0.2){

    //   switch(Constants.camera.getLatestResult().getBestTarget().getFiducialId()){
    //       case 1:
    //       m_Odometry.resetPosition(gyro.getRotation2d(), 
    //       new SwerveModulePosition[] {
    //         m_frontLeftLocation.getPosition(), m_frontRightLocation.getPosition(),
    //         m_backLeftLocation.getPosition(), m_backRightLocation.getPosition()
    //       }, new Pose2d(, y, theta));
    //       case 2:
    //       m_Odometry.resetPosition(gyro.getRotation2d(), 
    //       new SwerveModulePosition[] {
    //         m_frontLeftLocation.getPosition(), m_frontRightLocation.getPosition(),
    //         m_backLeftLocation.getPosition(), m_backRightLocation.getPosition()
    //       }, new Pose2d(x, y, theta));

    //       case 3:
    //       m_Odometry.resetPosition(gyro.getRotation2d(), 
    //       new SwerveModulePosition[] {
    //         m_frontLeftLocation.getPosition(), m_frontRightLocation.getPosition(),
    //         m_backLeftLocation.getPosition(), m_backRightLocation.getPosition()
    //       }, new Pose2d(x, y, theta));

    //       case 4:
    //       m_Odometry.resetPosition(gyro.getRotation2d(), 
    //       new SwerveModulePosition[] {
    //         m_frontLeftLocation.getPosition(), m_frontRightLocation.getPosition(),
    //         m_backLeftLocation.getPosition(), m_backRightLocation.getPosition()
    //       }, new Pose2d(x, y, theta));

    //       case 5:
    //       m_Odometry.resetPosition(gyro.getRotation2d(), 
    //       new SwerveModulePosition[] {
    //         m_frontLeftLocation.getPosition(), m_frontRightLocation.getPosition(),
    //         m_backLeftLocation.getPosition(), m_backRightLocation.getPosition()
    //       }, new Pose2d(x, y, theta));

    //       case 6:
    //       m_Odometry.resetPosition(gyro.getRotation2d(), 
    //       new SwerveModulePosition[] {
    //         m_frontLeftLocation.getPosition(), m_frontRightLocation.getPosition(),
    //         m_backLeftLocation.getPosition(), m_backRightLocation.getPosition()
    //       }, new Pose2d(x, y, theta));

    //       case 7:
    //       m_Odometry.resetPosition(gyro.getRotation2d(), 
    //       new SwerveModulePosition[] {
    //         m_frontLeftLocation.getPosition(), m_frontRightLocation.getPosition(),
    //         m_backLeftLocation.getPosition(), m_backRightLocation.getPosition()
    //       }, new Pose2d(x, y, theta));

    //       case 8:
    //       m_Odometry.resetPosition(gyro.getRotation2d(), 
    //       new SwerveModulePosition[] {
    //         m_frontLeftLocation.getPosition(), m_frontRightLocation.getPosition(),
    //         m_backLeftLocation.getPosition(), m_backRightLocation.getPosition()
    //       }, new Pose2d(x, y, theta));

  
      //    }
      
      
      // }
  }

  // public Rotation2d tagToModAngle(int tag){
  //   //figure out later
  //   if(tag == 1){
  //     return null;
  //   }
  //   return null;
  // }

  // public SwerveModulePosition[] tagToModPos(int tag){
  //   //figure out later
  //   if(tag == 1){
  //     return null;
  //   }
  //   return null;
  // }

  // public Pose2d tagToPose(int tag){
  //   if(tag == 1){
  //     return null;
  //   }
  //   return null;
  // }

  public double getDriveDistance(){
    return m_frontLeftLocation.getDrive();
  }

  // public Pose2d getStarting(){
  //  switch(startingPos){
  //    case 1: return new Pose2d(0,0, null);
  //    case 2: return new Pose2d(0,0, null);
  //    case 3: return new Pose2d(0,0, null);

  //  }
  //  return null;
  //   return new Pose2d(0, 0, null);
    
  // }

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