package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.SwerveDrive;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SwerveCommand extends CommandBase {
    private static SwerveDrive swerveDrive;
    private static XboxController remote;

    public SwerveCommand(SwerveDrive drive) {
        swerveDrive = drive;
    }

    @Override
    public void initialize() {
        remote = RobotContainer.xboxController;
        swerveDrive.setCoast();
    }

    private double applyCurve(double joystickPosition, double threshold, int exponent) {
        if (joystickPosition > 0) {
            return (1 - threshold) * Math.pow(joystickPosition, exponent) + threshold;
        } else if (joystickPosition < 0) {
            return (1 - threshold) * Math.pow(joystickPosition, exponent) - threshold;
        }
        return 0;
    }

    private double deadzone(double value, double deadzone) {
        if (Math.abs(value) < deadzone) {
            return 0;
        }
        return value;
    }

    @Override
    public void execute() {

        
        // double forward = -remote.getRawAxis(1);
        // double strafe = -remote.getRawAxis(0);
        // double rotation = -remote.getRawAxis(4);

        // forward = deadzone(forward, 0.1);
        // strafe = deadzone(strafe, 0.1);
        // rotation = deadzone(rotation, 0.1);

        // forward = applyCurve(forward, 0.1, 3);
        // strafe = applyCurve(strafe, 0.1, 3);
        // rotation = applyCurve(rotation, 0.1, 3);

        // if (Math.abs(forward) >= 0.1 || Math.abs(strafe) >= 0.1 || Math.abs(rotation) >= 0.1) {
        //     swerveDrive.updatePeriodic(strafe, forward, rotation);
        // } else {
        //     swerveDrive.stopAll();
        // }

        if (remote.getBButtonPressed()) {
            swerveDrive.updatePeriodic(0.01, 0, 0);
            swerveDrive.setBrake();
        }
        if (remote.getXButtonPressed()) {
            swerveDrive.updatePeriodic(-0.01, 0, 0);
            swerveDrive.setCoast();
        }

        
        if (Math.abs(remote.getLeftX()) >= 0.1 || Math.abs(remote.getLeftY()) >= 0.1 || Math.abs(remote.getRightX()) >= 0.1) {
            swerveDrive.updatePeriodic(remote.getLeftX(), remote.getLeftY(), remote.getRightX());
            } else {
        swerveDrive.stopAll();
}
    }

    @Override
    public void end(boolean interrupted) {
        NetworkTableInstance.getDefault().getTable("/datatable").getEntry("SwerveCommand").setBoolean(false);
        swerveDrive.stopAll();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}