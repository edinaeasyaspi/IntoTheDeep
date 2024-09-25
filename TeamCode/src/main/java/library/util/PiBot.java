package library.util;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;

import android.util.Size;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.vision.VisionPortal;

import edina.library.util.drivecontrol.DriveDirection;
import edina.library.util.drivecontrol.PiDrive;
import edina.library.util.drivecontrol.ServoThrottle;
import edina.opmodes.teleop.test.PropDetectingVisionProcessor;

public class PiBot {
    private final RobotHardware hw;
    private final Positioning posn;
    private final PropDetectingVisionProcessor imageProcessor;

    //rotation
    private double targetHeading;
    private DcMotor[] motors;
    private DcMotor.RunMode preRotateRunMode;
    private boolean firstRotate;

    //Driving
    private Position stoppingPoint;
    public final PiDrive drive;
    private double predriveHeading;
    private ServoThrottle armSwingRight, armSwingLeft;

    public PiBot(RobotHardware hw) {
        this.hw = hw;
        this.posn = new Positioning(hw);
        motors = new DcMotor[]{hw.frontLeftMotor, hw.backLeftMotor, hw.frontRightMotor, hw.backRightMotor};
        armSwingRight = new ServoThrottle(hw.intakeSwingRight, 0.7, 0);
        armSwingLeft = new ServoThrottle(hw.intakeSwingLeft, 0.7, 1);
        drive = new PiDrive(hw, posn);

        imageProcessor = new PropDetectingVisionProcessor();

        VisionPortal.Builder visionPortalBuilder = new VisionPortal.Builder();
        visionPortalBuilder.enableLiveView(true)
                .addProcessor(imageProcessor)
                .addProcessor(posn.getMyAprilTagProc())
                .setCamera(hw.webcam)
                .setCameraResolution(new Size(640, 480))
                .build();
    }

    public PropDetectingVisionProcessor.Selected getSelection() {
        PropDetectingVisionProcessor.Selected position = imageProcessor.getSelection();

        if (hw.telemetry != null) {
            hw.telemetry.addData("Selected value", position);
            hw.telemetry.addData("diag", imageProcessor.getDiagString());
        }

        return position;
    }

    public Positioning getPositioning() {
        return posn;
    }

    public void planDriveToClosestPoint(Point target, DriveDirection direction) {
        double tgtDist = 0;

        this.predriveHeading = posn.readHeading(true);

        Position currentPos = posn.getCurrPos();

        if (direction == DriveDirection.Lateral) {
            tgtDist = currentPos.toRobotRel(target).x;

            stoppingPoint = currentPos.addRobotRel(new Point(tgtDist, 0));
        } else if (direction == DriveDirection.Axial) {
            tgtDist = currentPos.toRobotRel(target).y;

            stoppingPoint = currentPos.addRobotRel(new Point(0, tgtDist));
        }

        if (Math.abs(tgtDist) < 0.1) return;

        drive.preRun(tgtDist, direction);
        if (hw.telemetry != null) {
            hw.telemetry.addData("tgtDist", "%.1fin", tgtDist);
            hw.telemetry.update();
        }
    }

    public DriveStatus runDrive() {
        if (drive.run()) {
            posn.readHeading(true);
            posn.setCurrPos(stoppingPoint);
            return DriveStatus.Done;
        } else {
            return DriveStatus.Driving;
        }
    }

    public void planRotateToHeading(double targetHeading) {
        this.targetHeading = targetHeading;
        preRotateRunMode = hw.frontLeftMotor.getMode();
        firstRotate = true;
    }

    public void planRotateToPoint(Point p) {
        Position c = posn.getCurrPos();

        double x = p.x - c.x;
        double y = p.y - c.y;

        double angle = Math.atan2(-x, y);

        planRotateToHeading(Math.toDegrees(angle));
    }

    public DriveStatus runRotate() {
        double ppd = 537.0 / 63.15;

        double targetAngle = targetHeading - posn.readHeading(true);

        while (targetAngle < -180) {
            targetAngle = targetAngle + 360;
        }

        while (targetAngle > 180) {
            targetAngle = targetAngle - 360;
        }

        if (firstRotate && targetAngle == 0)
            return doneWithRotate();

        if (hw.telemetry != null) {
            hw.telemetry.addData("heading", "%.1f", posn.readHeading(false));
            hw.telemetry.addData("target", "%.1f", targetAngle);
            hw.telemetry.update();
        }

        if (Math.abs(targetAngle) > 1) {
            int targetPos = (int) (targetAngle * ppd);
            double power = 0.5;

            for (DcMotor m : motors) {
                int p = m.getCurrentPosition();
                if (m.getDirection() == DcMotorSimple.Direction.REVERSE) {
                    m.setTargetPosition(p - targetPos);
                } else {
                    m.setTargetPosition(p + targetPos);
                }

                m.setPower(power);
                m.setMode(RUN_TO_POSITION);
            }
        }

        if (areIdle()) {
            return doneWithRotate();
        } else {
            return DriveStatus.Driving;
        }
    }

    private DriveStatus doneWithRotate() {
        for (DcMotor m : motors) {
            m.setPower(0);
            m.setMode(preRotateRunMode);
        }

        return DriveStatus.Done;
    }

    private boolean areIdle() {
        for (DcMotor m : motors) {
            if (m.isBusy()) {
                return false;
            }
        }
        return true;
    }

    public void positionGrabber(double targetPosition) {
        armSwingRight.setTargetPos(targetPosition);
        armSwingLeft.setTargetPos(1 - targetPosition);
    }

    public void runGrabber() {
        armSwingLeft.run();
        armSwingRight.run();
    }

    public boolean grabberInPosition() {
        throw new RuntimeException();
    }

    public void grab(GrabberSide grabberSide) {
        if (grabberSide == GrabberSide.Right)
            hw.intakeRight.setPosition(0.6);
        if (grabberSide == GrabberSide.Left)
            hw.intakeLeft.setPosition(0);
        if (grabberSide == GrabberSide.Both) {
            hw.intakeLeft.setPosition(0);
            hw.intakeRight.setPosition(0.6);
        }
    }

    public void drop(GrabberSide grabberSide) {
        if (grabberSide == GrabberSide.Right)
            hw.intakeRight.setPosition(0.4);
        if (grabberSide == GrabberSide.Left)
            hw.intakeLeft.setPosition(0.2);
        if (grabberSide == GrabberSide.Both) {
            hw.intakeLeft.setPosition(0.2);
            hw.intakeRight.setPosition(0.4);
        }
    }

    public void positionHooks(Hanging hanging) {
        if (hanging == Hanging.Extend) ;
        hw.hangLeft.setPosition(Servo.MIN_POSITION + 0.05);
        hw.hangRight.setPosition(Servo.MAX_POSITION - 0.05);
        hw.hangLiftLeft.setPosition(0.8);
        hw.hangLiftRight.setPosition(0.2);
    }
    //merry summer


    public void retractHooks(Hanging hanging) {
        if (hanging == Hanging.Retract) ;
        hw.hangLeft.setPosition(Servo.MIN_POSITION + 0.05);
        hw.hangRight.setPosition(Servo.MAX_POSITION - 0.05);
        hw.hangLiftLeft.setPosition(0.2);
        hw.hangLiftRight.setPosition(0.8);
    }

    public void planLatchOnBackboard() {
        //happy hanukkah
    }

    public boolean latchOn() {
        //happy MLKJ day
        throw new RuntimeException();
    }

    public void dropPixelOnBackground() {
        //enum LEFTMIDDLERIGHT
    }

    public void intakePixel() {
        //happy memorial day
    }


    public void launch(DroneLauncher droneLauncher) {
        if (droneLauncher == DroneLauncher.Idle)
            hw.droneLauncher.setPower(0);
        if (droneLauncher == DroneLauncher.Expel) {
            hw.droneLauncher.setPower(-1);
        }
    }
}