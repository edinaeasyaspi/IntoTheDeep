package edu.edina.library.subsystems;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;

import edu.edina.library.util.Robot;

public class MecanumDrive extends Subsystem {
    private double leftStickX;
    private double leftStickY;
    private double rightStickX;

    private org.firstinspires.ftc.teamcode.MecanumDrive drive;
    private Robot robot;

    public MecanumDrive(Robot robot) {
        drive = new org.firstinspires.ftc.teamcode.MecanumDrive(robot.RobotHardware.frontLeftMotor,
                robot.RobotHardware.backLeftMotor, robot.RobotHardware.backRightMotor, robot.RobotHardware.frontRightMotor,
                robot.RobotHardware.imu, robot.RobotHardware.voltageSensor, new Pose2d(0, 0, 0));

        this.robot = robot;
    }

    public void setProperties(double leftStickX, double leftStickY, double rightStickX){
        this.leftStickX = ScaleMotorCube(leftStickX);
        this.leftStickY = ScaleMotorCube(leftStickY);
        this.rightStickX = ScaleMotorCube(rightStickX);
    }

    public void setTrajectory(double positionX, double positionY, double toRadians)
    {
      //  TrajectoryBuilder trajectoryBuilder = new TrajectoryBuilder(new Pose2d(0,0,0));
        //trajectoryBuilder.lineToXLinearHeading(new Pose2d(25, 0, Math.toRadians(0)));

//        drive.trajectoryBuilder(new Pose2d())//-35,61
//                .lineToLinearHeading(new Pose2d(25, 0, Math.toRadians(0)))
    }
    public void driveToPose(double positionX, double positionY, double angle){
        Pose2d myPose = new Pose2d(positionX, positionY, Math.toRadians(angle));

        drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        -positionX,
                        -positionY
                ),
                (angle)
        ));

        drive.updatePoseEstimate();
    }


    @Override
    public void init() { }

    @Override
    public void start() {
    }

    @Override
    public void update() {
        drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        -leftStickY,
                        -leftStickX
                ),
                (-rightStickX/1.5)
        ));

        drive.updatePoseEstimate();
    }

    public static double ScaleMotorCube(double joyStickPosition) {
        return (double) Math.pow(joyStickPosition, 3.0);
    }
}
