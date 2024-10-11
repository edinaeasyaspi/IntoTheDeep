package edu.edina.library.util;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class Positioning {
    public AprilTagProcessor getMyAprilTagProc() {
        return myAprilTagProc;
    }

    public AprilTagProcessor myAprilTagProc;

    private IMU imu;

    private ModernRoboticsI2cGyro gyro;

    private double initialHeading;
    private final double[] allmx = {29.381, 35.381, 41.381, 100.0435, 106.0435, 112.0435};
    private final double[] allmy = {132.492908, 132.492908, 132.492908, 132.492908, 132.492908, 132.492908, 0, 0, 0, 0};
    private final double camOffsetX = 5.25, camOffsetY = 8;
    private Position currPos;

    public Positioning(RobotHardware hw) {
        this.gyro = hw.gyro;

        AprilTagProcessor.Builder myAprilTagProcBuilder = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true);

        myAprilTagProc = myAprilTagProcBuilder.build();

        setCurrPos(new Position(72, 72, 0));
    }

    public Position getCurrPos() {
        return currPos;
    }

    public void setCurrPos(Position newPos) {
        currPos = newPos;
        initialHeading = gyro.getHeading() - newPos.a;
    }

    // todo: actually update position
    public Position readAprilTagPosition(boolean updatePosition) {
        List<AprilTagDetection> currentDetections = myAprilTagProc.getDetections();
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null && detection.id <= 6) {
                int i = detection.id - 1;

                double mx = allmx[i];
                double my = allmy[i];

                double px = detection.ftcPose.x + camOffsetX;
                double py = detection.ftcPose.y + camOffsetY;

                double mc = Math.sqrt((px * px) + (py * py));

                double a = readHeading(false);
                double dx = Math.sin(Math.toRadians(detection.ftcPose.yaw));
                double dy = Math.cos(Math.toRadians(detection.ftcPose.yaw));

                double qc = dx * mc;
                double rx = qc + mx;

                double mq = dy * mc;
                double ry = my - mq;

                Position p = new Position(rx, ry, a);

                if(updatePosition) {
                    currPos = p;
                }

                return p;
            }
        }

        return null;
    }

    public double readHeading(boolean updatePosition) {
        double zAxis = gyro.getHeading();

        double heading = zAxis - initialHeading;

        if (updatePosition) {
            currPos = new Position(currPos.x, currPos.y, heading);
        }

        return heading;
    }
}
