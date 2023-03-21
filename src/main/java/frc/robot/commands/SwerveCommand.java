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
        double forward = -remote.getRawAxis(1);
        double strafe = -remote.getRawAxis(0);
        double rotation = -remote.getRawAxis(4);

        double deadzoneValue = 0.1;
        forward = deadzone(forward, deadzoneValue);
        strafe = deadzone(strafe, deadzoneValue);
        rotation = deadzone(rotation, deadzoneValue);

        double torqueResistanceThreshold = 0.1;
        int curveExponent = 3;
        forward = applyCurve(forward, torqueResistanceThreshold, curveExponent);
        strafe = applyCurve(strafe, torqueResistanceThreshold, curveExponent);
        rotation = applyCurve(rotation, torqueResistanceThreshold, curveExponent);

        if (Math.abs(forward) >= 0.1 || Math.abs(strafe) >= 0.1 || Math.abs(rotation) >= 0.1) {
            swerveDrive.updatePeriodic(strafe, forward, rotation);
        } else {
            swerveDrive.stopAll();
        }

        if (remote.getBButtonPressed()) {
            swerveDrive.updatePeriodic(0.01, 0, 0);
            swerveDrive.setBrake();
        }
        if (remote.getXButtonPressed()) {
            swerveDrive.updatePeriodic(-0.01, 0, 0);
            swerveDrive.setCoast();
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