/* ==================================================
 * Authors: Lucas Jacobs
 *
 * --------------------------------------------------
 * Description:
 * Makes the robot aim at the target and shoot based on the speed from the vision
 * ================================================== */

package frc.robot.commands.auto;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoVisionShoot extends CommandBase
{
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  // VARIABLES //

  private Robot robot;
  //private Timer timer;
  //private double timeToShot;
  //private final static double CONVEYOR_SPIN_TIME = .6;

  // CONSTANTS //

  // CONSTRUCTOR //

  public AutoVisionShoot(Robot robot/*, double timeToShot*/)
  {
    this.robot = robot;
    //this.timeToShot = timeToShot;
    //timer = new Timer();

    // subsystems that this command requires
    addRequirements(robot.shooter/*, robot.conveyor*/);
  }

  // METHODS //

  // Called when the command is initially scheduled.
  @Override
  public void initialize()
  {
    robot.vision.enableShootingLight();
    robot.shooter.setAutoShooting(true);
    robot.vision.resetPID();
    //timer.start();
    //timer.reset();

    SmartDashboard.putString("Robot Mode:", "Auto Shoot");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute()
  {
    //Spin shooter at auto speed
    robot.vision.calcAlign(robot.drivetrain.gyroAngle());

    double autoSpeed = robot.vision.autoShooterSpeed();
    robot.shooter.spin(autoSpeed);

    /*while (timer.get() < timeToShot) {
      double autoSpeed = robot.vision.autoShooterSpeed();
      robot.shooter.spin(autoSpeed);
    }

    timer.reset();
    
    //Spin and Shoot

    while (timer.get() < CONVEYOR_SPIN_TIME) {
      robot.vision.calcAlign(robot.drivetrain.gyroAngle());

      if (robot.vision.isAligned()) {
        double autoSpeed = robot.vision.autoShooterSpeed();
        robot.shooter.spin(autoSpeed);
        robot.conveyor.feed();
      } else {
        break;
      }
    }*/
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted)
  {
    robot.vision.disableShootingLight();

    robot.shooter.stop();
    robot.conveyor.stop();

    robot.shooter.setAutoShooting(false);

    if (SmartDashboard.getString("Robot Mode:", "").equals("Auto Shoot")) {
      SmartDashboard.putString("Robot Mode:", "TeleOp");
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished()
  {
    return false;
  }
}