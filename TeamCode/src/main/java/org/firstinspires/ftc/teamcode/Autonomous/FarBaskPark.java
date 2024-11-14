package org.firstinspires.ftc.teamcode.Autonomous;

import static java.lang.Math.PI;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="Far Side Basket Park ", group="Robot")
public class FarBaskPark extends LinearOpMode {

    /* Declare OpMode members. */
    private DcMotor lfd, rfd, lbd, rbd  = null;
    private IMU imu = null;
    private Servo clawLeft, clawRight = null;// Control/Expansion Hub IMU

    private double headingError = 0;
    private double targetHeading = 0;
    private double driveSpeed = 0.5;  // Set initial speed to 10%
    private double turnSpeed = 0.4;
    private double  lfdSpeed = 0.5;
    private double rfdSpeed = 0.5;
    private double rbdSpeed = 0.5;
    private double lbdSpeed = 0.5;
    private int lfdTarget, rfdTarget, rbdTarget, lbdTarget = 0;

    // Constants
    static final double COUNTS_PER_MOTOR_REV = 537.6 ;  // Example motor encoder counts
    static final double DRIVE_GEAR_REDUCTION = 1.0 ;     // No External Gearing.
    static final double WHEEL_DIAMETER_INCHES = 3.78 ;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * PI);

    static final double P_TURN_GAIN = 10;
    static final double P_DRIVE_GAIN = 0.001;
    static final double POWER_LIMIT = 0.1;


    double wheelCircumference = WHEEL_DIAMETER_INCHES * Math.PI;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        // Initialize hardware
        lfd = hardwareMap.get(DcMotor.class, "lfd");
        rfd = hardwareMap.get(DcMotor.class, "rfd");
        lbd = hardwareMap.get(DcMotor.class, "lbd");
        rbd = hardwareMap.get(DcMotor.class, "rbd");
        clawLeft = hardwareMap.get(Servo.class, "clawLeft");
        clawRight = hardwareMap.get(Servo.class, "clawRight");

        // Motor directions (adjust if needed)
        lfd.setDirection(DcMotor.Direction.REVERSE);
        lbd.setDirection(DcMotor.Direction.REVERSE);
        rfd.setDirection(DcMotor.Direction.FORWARD);
        rbd.setDirection(DcMotor.Direction.FORWARD);

        // Initialize IMU
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.LEFT;
        RevHubOrientationOnRobot.UsbFacingDirection usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.UP;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(orientationOnRobot));

        // Reset encoders
        lfd.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rfd.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lbd.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rbd.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set zero power behavior
        lfd.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rfd.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lbd.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rbd.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Wait for the game to start
        while (opModeInInit()) {
            clawLeft.setPosition(0.1);
            clawRight.setPosition(0.5);
            imu.resetYaw();// Only reset the yaw once at the start

            telemetry.addData(">", "Robot Heading = %4.0f", getHeading());
            telemetry.update();
        }

        // Set encoders to RUN_USING_ENCODER mode
        lfd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lbd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rfd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rbd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        imu.resetYaw();




        closeClaw();

        driveStraight(0.7,  9, 0);
        turnToHeading(0.6, -90);
        turnToHeading(0.6, -90);
        driveStraight(0.3, 35, -90);
        turnToHeading(0.6, -90);
        openClaw();
        sleep (500);



        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);  // Pause to display last telemetry message.
    }

    /**
     * Reset IMU
     *
     */

    public void resetGyro() {
        imu.resetYaw();
    }

    /**
     * Drive in a straight line, on a fixed compass heading, based on encoder counts.
     *
     * @param maxDriveSpeed MAX Speed for forward/rev motion (range 0 to +1.0).
     * @param distance Distance (in inches) to move from current position. Negative distance means move backward.
     * @param heading Absolute Heading Angle (in Degrees) relative to last gyro reset.
     */


    public void driveStraight(double maxDriveSpeed, double distance, double heading) {
        if (opModeIsActive()) {
            // Determine new target position, and pass to motor controller
            int moveCounts = (int)(distance * COUNTS_PER_INCH);
            lfdTarget = lfd.getCurrentPosition() + moveCounts;
            lbdTarget = lbd.getCurrentPosition() + moveCounts;
            rfdTarget = rfd.getCurrentPosition() + moveCounts;
            rbdTarget = rbd.getCurrentPosition() + moveCounts;

            // Set Target FIRST, then turn on RUN_TO_POSITION
            lfd.setTargetPosition(lfdTarget);
            lbd.setTargetPosition(lbdTarget);
            rfd.setTargetPosition(rfdTarget);
            rbd.setTargetPosition(rbdTarget);

            lfd.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rfd.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lbd.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rbd.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Start moving robot
            maxDriveSpeed = Math.abs(maxDriveSpeed);
            moveRobot(maxDriveSpeed, 0);

            // Loop until all motors reach their target
            while (opModeIsActive() && (lfd.isBusy() && rfd.isBusy() && lbd.isBusy() && rbd.isBusy())) {
                // Adjust heading with proportional control
                turnSpeed = getSteeringCorrection(heading, P_DRIVE_GAIN);

                if (distance < 0) turnSpeed *= -0.1;  // Reverse correction if moving backward

                moveRobot(driveSpeed, turnSpeed);  // Apply drive and turn adjustments
                sendTelemetry(true);
            }

            // Stop all motion
            moveRobot(0, 0);
            lfd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rfd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lbd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rbd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }



    /**
     * Turn the robot to a specified heading.
     *
     * @param maxTurnSpeed Desired MAX speed of turn (range 0 to +1.0).
     * @param heading Absolute Heading Angle (in Degrees) relative to last gyro reset.
     */
//
    /**
     * Turns the robot to a specific heading using the shortest path.
     *
     * @param maxTurnSpeed Maximum turn speed (range -1.0 to 1.0).
     * @param heading Target heading in degrees.
     */
    public void turnToHeading(double maxTurnSpeed, double heading) {
        if (opModeIsActive()) {

            double currentHeading = getHeading();
            double headingDifference = heading - currentHeading;


            if (headingDifference > 180) {
                headingDifference -= 360;
            } else if (headingDifference < -180) {
                headingDifference += 360;
            }

            double turnSpeed = headingDifference / P_TURN_GAIN;
            double minTurnSpeed = 0.2;  // Minimum speed to avoid stalling


            while (opModeIsActive() && Math.abs(headingDifference) > 1) {
                currentHeading = getHeading();
                headingDifference = heading - currentHeading;


                if (headingDifference > 180) {
                    headingDifference -= 360;
                } else if (headingDifference < -180) {
                    headingDifference += 360;
                }


                double decelerationFactor = Math.pow(Math.abs(headingDifference) / 180.0, 2);
                turnSpeed = (headingDifference / P_TURN_GAIN) * decelerationFactor;


                if (Math.abs(turnSpeed) < minTurnSpeed) {
                    turnSpeed = Math.signum(turnSpeed) * minTurnSpeed;
                }


                turnSpeed = Range.clip(turnSpeed, -maxTurnSpeed, maxTurnSpeed);


                moveRobot(0, turnSpeed);
                sendTelemetry(true);
            }


            moveRobot(0, 0);
            sendTelemetry(false);
        }
    }


    /**
     * Opening and closing the claw
     *
     */

    public void openClaw() {
        clawLeft.setPosition(0.3);
        clawRight.setPosition(0.8);

    }

    public void closeClaw() {
        clawLeft.setPosition(0.1);
        clawRight.setPosition(1);
    }

    /**
     * Strafing left and right
     *
     */

    public void strafeLeft(double maxDriveSpeed, double heading, double distance) {
        if (opModeIsActive()) {
            // Determine new target position, and pass to motor controller
            int moveCounts = (int)(distance * COUNTS_PER_INCH);
            lfdTarget = lfd.getCurrentPosition() - moveCounts;
            lbdTarget = lbd.getCurrentPosition() +  moveCounts;
            rfdTarget = rfd.getCurrentPosition() + moveCounts;
            rbdTarget = rbd.getCurrentPosition() - moveCounts;

            // Set Target FIRST, then turn on RUN_TO_POSITION
            lfd.setTargetPosition(lfdTarget);
            lbd.setTargetPosition(lbdTarget);
            rfd.setTargetPosition(rfdTarget);
            rbd.setTargetPosition(rbdTarget);

            lfd.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rfd.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lbd.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rbd.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Start moving robot
            maxDriveSpeed = Math.abs(maxDriveSpeed);
            moveRobot(maxDriveSpeed, 0);

            // Loop until all motors reach their target
            while (opModeIsActive() && (lfd.isBusy() && rfd.isBusy() && lbd.isBusy() && rbd.isBusy())) {
                // Adjust heading with proportional control
                turnSpeed = getSteeringCorrection(heading, P_DRIVE_GAIN);

                if (distance < 0) turnSpeed *= -0.1;  // Reverse correction if moving backward

                moveRobot(driveSpeed, turnSpeed);  // Apply drive and turn adjustments
                sendTelemetry(true);
            }

            // Stop all motion
            moveRobot(0, 0);
            lfd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rfd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lbd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rbd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     * Strafing right
     *
     */

    public void strafeRight(double maxDriveSpeed, double heading, double distance) {
        if (opModeIsActive()) {
            // Determine new target position, and pass to motor controller
            int moveCounts = (int)(distance * COUNTS_PER_INCH);
            lfdTarget = lfd.getCurrentPosition() + moveCounts;
            lbdTarget = lbd.getCurrentPosition() -  moveCounts;
            rfdTarget = rfd.getCurrentPosition() - moveCounts;
            rbdTarget = rbd.getCurrentPosition() + moveCounts;

            // Set Target FIRST, then turn on RUN_TO_POSITION
            lfd.setTargetPosition(lfdTarget);
            lbd.setTargetPosition(lbdTarget);
            rfd.setTargetPosition(rfdTarget);
            rbd.setTargetPosition(rbdTarget);

            lfd.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rfd.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lbd.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rbd.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Start moving robot
            maxDriveSpeed = Math.abs(maxDriveSpeed);
            moveRobot(maxDriveSpeed, 0);

            // Loop until all motors reach their target
            while (opModeIsActive() && (lfd.isBusy() && rfd.isBusy() && lbd.isBusy() && rbd.isBusy())) {
                // Adjust heading with proportional control
                turnSpeed = getSteeringCorrection(heading, P_DRIVE_GAIN);

                if (distance < 0) turnSpeed *= -0.1;  // Reverse correction if moving backward

                moveRobot(driveSpeed, turnSpeed);  // Apply drive and turn adjustments
                sendTelemetry(true);
            }

            // Stop all motion
            moveRobot(0, 0);
            lfd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rfd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lbd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rbd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     *
     * Drive backwards
     */

    public void driveBackwards(double maxDriveSpeed, double heading, double distance) {
        if (opModeIsActive()) {
            // Determine new target position, and pass to motor controller
            int moveCounts = (int)(-distance * COUNTS_PER_INCH);
            lfdTarget = lfd.getCurrentPosition() + moveCounts;
            lbdTarget = lbd.getCurrentPosition() + moveCounts;
            rfdTarget = rfd.getCurrentPosition() + moveCounts;
            rbdTarget = rbd.getCurrentPosition() + moveCounts;

            // Set Target FIRST, then turn on RUN_TO_POSITION
            lfd.setTargetPosition(lfdTarget);
            lbd.setTargetPosition(lbdTarget);
            rfd.setTargetPosition(rfdTarget);
            rbd.setTargetPosition(rbdTarget);

            lfd.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rfd.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lbd.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rbd.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Start moving robot with slightly adjusted right-side motor power for balancing
            maxDriveSpeed = Math.abs(maxDriveSpeed);
            moveRobot(maxDriveSpeed, 0.05);  // Apply small correction to turn right if it's curving left

            // Loop until all motors reach their target
            while (opModeIsActive() && ( rbd.isBusy() && lfd.isBusy() && rfd.isBusy() && lbd.isBusy())) {
                // Adjust heading with proportional control
                turnSpeed = getSteeringCorrection(heading, P_DRIVE_GAIN);

                if (distance < 0) turnSpeed *= -0.1;  // Reverse correction if moving backward

                moveRobot(driveSpeed, turnSpeed);  // Apply drive and turn adjustments
                sendTelemetry(true);
            }

            // Stop all motion
            moveRobot(0, 0);
            lfd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rfd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lbd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rbd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    /**
     *
     * Drive diagonally front left
     */

    public void diagonalFrontLeft(double maxDriveSpeed, double heading, double distance) {
        if (opModeIsActive()) {


            lfd.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            rbd.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);


            int moveCounts = (int) (distance * COUNTS_PER_INCH);


            int lfdTarget = lfd.getCurrentPosition() + moveCounts;
            int rbdTarget = rbd.getCurrentPosition() + moveCounts;


            lfd.setTargetPosition(lfdTarget);
            rbd.setTargetPosition(rbdTarget);


            lbd.setPower(0);
            rfd.setPower(0);


            lfd.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rbd.setMode(DcMotor.RunMode.RUN_TO_POSITION);


            maxDriveSpeed = Math.abs(maxDriveSpeed);


            moveRobot(maxDriveSpeed, 0);


            while (opModeIsActive() && (lfd.isBusy() && rbd.isBusy())) {

                double turnSpeed = getSteeringCorrection(heading, P_DRIVE_GAIN);


                moveRobot(maxDriveSpeed, turnSpeed);

                sendTelemetry(true);
            }


            moveRobot(0, 0);


            lfd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rbd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lbd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rfd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }




    /**
     * Proportional control to determine how much steering correction is needed.
     *
     * @param desiredHeading Desired absolute heading.
     * @param proportionalGain Gain factor for proportional control.
     * @return Steering correction power.
     */
    public double getSteeringCorrection(double desiredHeading, double proportionalGain) {
        targetHeading = desiredHeading;
        headingError = targetHeading - getHeading();

        while (headingError > 12) headingError -= 360;
        while (headingError <= -12) headingError += 360;

        return Range.clip(headingError * proportionalGain, -1, 1);
    }

    /**
     * Apply movement to the robot.
     *
     * @param drive Forward/reverse motor speed.
     * @param turn Clockwise turning motor speed.
     */
    public void moveRobot(double drive, double turn) {
        // Set the left and right speeds
        lfdSpeed  = drive - turn;
        lbdSpeed = drive -turn;
        rfdSpeed = drive + turn;
        rbdSpeed = drive +turn;

        // Scale down if either speed exceeds 1.0
        double max = Math.max(Math.max(Math.abs(lfdSpeed), Math.abs(rfdSpeed)),
                Math.max(Math.abs(lbdSpeed), Math.abs(rbdSpeed)));

        if (max > 0.7) {
            lfdSpeed /= max;
            rfdSpeed /= max;
            lbdSpeed /= max;
            rbdSpeed /= max;
        }


        // Set motor power
        lfd.setPower(lfdSpeed);
        lbd.setPower(lbdSpeed);
        rfd.setPower(rfdSpeed);
        rbd.setPower(rbdSpeed);
    }

    /**
     * Get the current heading of the robot from the IMU.
     *
     * @return Current heading in degrees.
     */
    public double getHeading() {
        YawPitchRollAngles angles = imu.getRobotYawPitchRollAngles();
        return angles.getYaw(AngleUnit.DEGREES);
    }

    /**
     * Send telemetry data to the driver station.
     *
     * @param isDriving Indicates if the robot is currently driving.
     */
    public void sendTelemetry(boolean isDriving) {
        if (isDriving) {
            telemetry.addData("Drive Speed", driveSpeed);
        }
        telemetry.addData("Heading", getHeading());
        telemetry.update();
        telemetry.addData("Left Encoder", lfd.getCurrentPosition());
        telemetry.addData("Right Encoder", rfd.getCurrentPosition());
        telemetry.addData("Heading Error", getHeading());
        telemetry.update();

    }
}



