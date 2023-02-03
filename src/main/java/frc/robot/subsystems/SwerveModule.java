package frc.robot.subsystems;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotContainer;



public class SwerveModule extends SubsystemBase {

    private CANSparkMax turn;
    private CANSparkMax drive;
    private RelativeEncoder enc;
    private PIDController cont;
    public Rotation2d currentAngle;
    public RelativeEncoder driveEnc;

    public SwerveModule(int turnComponent, int driveComponent, int encoderPort) {


        double p = 0.006;
        double i = 0;
        double d = 0.00001;
        turn = new CANSparkMax(turnComponent, MotorType.kBrushless);
        drive = new CANSparkMax(driveComponent, MotorType.kBrushless);
        drive.setClosedLoopRampRate(0);
        drive.setOpenLoopRampRate(0);
        enc = turn.getEncoder();
        enc.setPositionConversionFactor(1);
        driveEnc = drive.getEncoder();
        driveEnc.setPositionConversionFactor(1);
        turn.setSmartCurrentLimit(80, 80);
        drive.setSmartCurrentLimit(80, 80);
        // enc = new AnalogEncoder(encoderPort);
        // enc.reset();
        // turn.enableVoltageCompensation(12);
        // drive.enableVoltageCompensation(12);

        //turn.setOpenLoopRampRate(0);
        // drive.setOpenLoopRampRate(0.75);
        //turn.setClosedLoopRampRate(0);
        // drive.setClosedLoopRampRate(0.75);
        currentAngle = new Rotation2d(Math.PI/ 2 * -1);
        //ont = new PIDController(0.9, 0.00, 0);
        
        p = (double)NetworkTableInstance.getDefault().getTable("/datatable").getEntry("P").getNumber(0.005);
        i = (double)NetworkTableInstance.getDefault().getTable("/datatable").getEntry("I").getNumber(0);
        d = (double)NetworkTableInstance.getDefault().getTable("/datatable").getEntry("D").getNumber(0.00001);

        //cont = new PIDController(0.004 , 0, 0.00001);
        cont = new PIDController(p,i,d);
        cont.enableContinuousInput(-180, 180);

    }

    

    public double newGet(){
        double nativeUnits = 11.654; //the native units that equates to a full module rotation
        double tempAngle = enc.getPosition();
        if (Math.abs(enc.getPosition()) > nativeUnits){ //if the current encoder position is greater than one full rotation
            tempAngle = tempAngle % nativeUnits;
        }

        tempAngle = tempAngle / nativeUnits;
        tempAngle *= 360;

        tempAngle *= -1;

        
        //System.out.println("newGet() " + tempAngle);
        return tempAngle;
    }

    public double getAngle(){
        double temp = this.newGet(); ///should be an angle between 0 and 360 for the full moduile
        
        //first rotate the module angle by 90 degrees
        temp += 90;

        if (temp > 360){
            temp = temp - 360;
        }

        //convert 0 to 360 to 0 to -180 to 180 to 0
        if (temp <= 180){
            temp *= -1;
        }
        else{
            temp = 360 - temp;
        }

        //System.out.println("getAngle() " + temp);
        return temp;
    }

    // convert current module position into 0 to 360
    // public double getAngle() {
    //     enc.update();
    //     double temp = enc.getBigAngle();
    //     temp += 90;

    //     if (temp > 360){
    //         temp = temp - 360;
    //     }

    //     if (temp <= 180){
    //         temp *= -1;
    //     } else{
    //         temp = 360 - temp;
    //     }
    //     return temp;
    // }

    // public double convertedAngle() {
    //     double temp = enc.getBigAngle();
    //     if (temp > 180)
    //         temp = -(360 - temp);

    //     return temp;
    // }
    // long lastRun;
    // public void update() {
    //     long t1 = System.currentTimeMillis();
    //     enc.update();
    //     SmartDashboard.putNumber("timer", lastRun-t1);
    //     lastRun = t1;
    // }

    public void setCurrentAngle(){
        double temp = Math.toRadians(getAngle());
        currentAngle = new Rotation2d(temp);
    }
 
    // method to set the module angle and drive speed
    public void setModule(Rotation2d angle, double speed) {

        
            
                // SmartDashboard.putNumber("Module " + turn.getDeviceId(), this.getAngle());
                // SmartDashboard.putNumber("Module " + turn.getDeviceId() + " desiredAngle", angle.getDegrees());
                // SmartDashboard.putNumber("Module " + turn.getDeviceId() + " speed", speed);

            double setPoint = cont.calculate(getAngle(), angle.getDegrees());
            if (setPoint < 0){
                turn.set(Math.max(setPoint, -0.4));
            } else{
                turn.set(Math.min(0.4, setPoint));
            }
            drive.set(speed / 4);
            this.setCurrentAngle();
        
    }
    public void reset(){
        enc.setPosition(0);
        driveEnc.setPosition(0);
    }

    public double getDrive(){
        double wheelCircumference = Math.PI * 4;
        double wheelRotations = (driveEnc.getPosition() / 2.58) /  2.5;
        return wheelCircumference * wheelRotations;
    }

    public void driveRest(){
        driveEnc.setPosition(0);
    }

    public void stop(){
        turn.set(0);
        drive.set(0);
    }

    public void setPID(double p, double i, double d){
        cont.setPID(p,i,d);
    }

    public SwerveModulePosition getPosition(){
        return new SwerveModulePosition(getDrive(), new Rotation2d(getAngle()));
    }
}