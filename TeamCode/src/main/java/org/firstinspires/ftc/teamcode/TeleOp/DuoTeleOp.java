package org.firstinspires.ftc.teamcode.TeleOp;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


import org.firstinspires.ftc.teamcode.RoadRunner.messages.PoseMessage;
import org.firstinspires.ftc.teamcode.Test.ServoThrottle;


import org.firstinspires.ftc.teamcode.RobotHardware;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="DuoTeleOp", group="Linear OpMode")
public class    DuoTeleOp extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor lfd = null;
    private DcMotor lbd = null;
    private DcMotor rfd = null;
    private DcMotor rbd = null;
    private DcMotor lift = null;
    private CRServo armExtend = null;
    private IMU imu = null;


    private Servo swingLeft, swingRight;


    private Servo clawRight, clawLeft = null;


    @Override
    public void runOpMode() {


        lfd  = hardwareMap.get(DcMotor.class, "lfd");
        lbd  = hardwareMap.get(DcMotor.class, "lbd");
        rfd = hardwareMap.get(DcMotor.class, "rfd");
        rbd = hardwareMap.get(DcMotor.class, "rbd");
        lift = hardwareMap.get(DcMotor.class, "lift");
        clawLeft = hardwareMap.get(Servo.class, "clawLeft");
        clawRight = hardwareMap.get(Servo.class, "clawRight");
        swingLeft = hardwareMap.get(Servo.class, "swingLeft");
        swingRight = hardwareMap.get(Servo.class, "swingRight");
        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.LEFT;
        RevHubOrientationOnRobot.UsbFacingDirection usbDirection = RevHubOrientationOnRobot.UsbFacingDirection.UP;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);
        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(orientationOnRobot));







        armExtend = hardwareMap.get(CRServo.class, "armExtend");

        while (opModeInInit()) {
            imu.resetYaw();

            clawLeft.setPosition(0.1);
            clawRight.setPosition(0.5);



            telemetry.addData(">", "Robot Heading = %4.0f");
            telemetry.update();





        }






        ServoThrottle thSwingLeft, thSwingRight;
        thSwingLeft= new ServoThrottle(swingLeft, 0.75, 0.85);
        thSwingRight = new ServoThrottle(swingRight, 0.75, 0.15 );








        lfd.setDirection(DcMotor.Direction.REVERSE);
        lbd.setDirection(DcMotor.Direction.REVERSE);
        rfd.setDirection(DcMotor.Direction.FORWARD);
        rbd.setDirection(DcMotor.Direction.FORWARD);




        lfd.setZeroPowerBehavior(BRAKE);
        lbd.setZeroPowerBehavior(BRAKE);
        rbd.setZeroPowerBehavior(BRAKE);
        rfd.setZeroPowerBehavior(BRAKE);
        lift.setZeroPowerBehavior(BRAKE);
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setTargetPosition(0);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(1);



        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();


        while (opModeIsActive()) {

            ElapsedTime elapsedTime = new ElapsedTime();

            double max;
                    double powerLimit = 0.3;






            double axial   = -gamepad1.left_stick_y;
            double lateral =  gamepad1.left_stick_x;
            double yaw     =  gamepad1.right_stick_x;

            double leftFrontPower  = axial + lateral + yaw;
            double rightFrontPower = axial - lateral - yaw;
            double leftBackPower   = axial - lateral + yaw;
            double rightBackPower  = axial + lateral - yaw;



            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 0.3) {
                leftBackPower /= max;
                rightBackPower /= max;
                leftFrontPower /= max;
                rightFrontPower /= max;
            }

            lfd.setPower(leftFrontPower);
            lbd.setPower(leftBackPower);
            rfd.setPower(rightFrontPower);
            rbd.setPower(rightBackPower);








            if (gamepad2.dpad_up) {
                lift.setTargetPosition(-3800);
                lift.setPower(1);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } else if (gamepad2.dpad_down) {
                lift.setTargetPosition(100);
                lift.setPower(0.6);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (gamepad2.dpad_right) {
                lift.setTargetPosition(-2900);
                lift.setPower(0.7);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (gamepad2.dpad_left) {
                lift.setTargetPosition(-1500);
                lift.setPower(1);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (gamepad2.a ) {
                armExtend.setPower(-1);
            } else if (gamepad2.b) {
                armExtend.setPower(1);
            } else {
                armExtend.setPower(0);
            }
            if (gamepad2.right_bumper) {
                clawLeft.setPosition(0.75);
                clawRight.setPosition(0.5);
            }
            if (gamepad2.left_bumper) {
                clawLeft.setPosition(0.25);
                clawRight.setPosition(0.4);
            }
            if(gamepad2.right_trigger > 0.8){
                thSwingLeft.setTargetPos(0.85);
                thSwingRight.setTargetPos(0.15);
            }
            if (gamepad2.left_trigger>0.8) {
                thSwingLeft.setTargetPos(0.1);
                thSwingRight.setTargetPos(0.9);
            }
            if (gamepad2.x) {
                thSwingLeft.setTargetPos(0.8);
                thSwingRight.setTargetPos(0.2);


            }
            if (gamepad2.y) {
                thSwingLeft.setTargetPos(0.6);
                thSwingRight.setTargetPos(0.4);
            }



            if (gamepad1.a)
                powerLimit = 0.3;
            else
                powerLimit = 0.2;


            lfd.setPower(leftFrontPower * powerLimit);
            lbd.setPower(leftBackPower * powerLimit);
            rfd.setPower(rightFrontPower * powerLimit);
            rbd.setPower(rightBackPower * powerLimit);


            thSwingLeft.run();
            thSwingRight.run();


            lfd.setPower(leftFrontPower);
            rfd.setPower(rightFrontPower);
            rbd.setPower(rightBackPower);
            lbd.setPower(leftBackPower);


            telemetry.addData("Status", "Robot is moving" + elapsedTime.toString());
            telemetry.addData("gamepad controller values", "%4.2f, %4.2f", axial, lateral, yaw);
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
            telemetry.addData("Time", "runtime");
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);
            telemetry.addData("Lift", lift.getCurrentPosition());
            telemetry.addData("armPos", thSwingLeft.getPosEstimate());
            telemetry.addData("armPos", thSwingRight.getPosEstimate());

            telemetry.update();
        }

    }}
