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
    DigitalChannel digitalTouch;

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
        digitalTouch = hardwareMap.get(DigitalChannel.class, "digitalTouch");

        digitalTouch.setMode(DigitalChannel.Mode.INPUT);
        telemetry.addData("DigitalTouchSensorExample", "Press start to continue...");
        telemetry.update();






        armExtend = hardwareMap.get(CRServo.class, "armExtend");

        while (opModeInInit()) {
            imu.resetYaw();

            clawLeft.setPosition(0.1);
            clawRight.setPosition(0.5);



            telemetry.addData(">", "Robot Heading = %4.0f");
            telemetry.update();





        }






        ServoThrottle thSwingLeft, thSwingRight;
        thSwingLeft= new ServoThrottle(swingLeft, 1, 1);
        thSwingRight = new ServoThrottle(swingRight, 1, 0);








        lfd.setDirection(DcMotor.Direction.REVERSE);
        lbd.setDirection(DcMotor.Direction.REVERSE);
        rfd.setDirection(DcMotor.Direction.FORWARD);
        rbd.setDirection(DcMotor.Direction.FORWARD);




        lfd.setZeroPowerBehavior(FLOAT);
        lbd.setZeroPowerBehavior(FLOAT);
        rbd.setZeroPowerBehavior(FLOAT);
        rfd.setZeroPowerBehavior(FLOAT);
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
            double powerLimit = 0.6;

            ElapsedTime dropTimer = null;





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



            if (digitalTouch.getState() == true) {
                thSwingLeft.setTargetPos(0.7);
                thSwingRight.setTargetPos(0.3);
            telemetry.addData("Arm", "IS DRAGGING, SENDING ARM TO HIGHER POSITION");
            } else {
                telemetry.addData("Arm ", "IS NOT DRAGGING, CONTINUE WITH CURRENT POSITION");
            }

            telemetry.update();






            if (gamepad2.dpad_up) {
                lift.setTargetPosition(-7450);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } else if (gamepad2.dpad_down) {
                lift.setTargetPosition(0);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (gamepad2.dpad_right) {
                lift.setTargetPosition(-3500);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (gamepad2.dpad_left) {
                lift.setTargetPosition(-1500);
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
                clawLeft.setPosition(0.1);
                clawRight.setPosition(0.5);
            }
            if (gamepad2.left_bumper) {
                clawLeft.setPosition(0.2);
                clawRight.setPosition(0.4);
            }
            if(gamepad2.right_trigger > 0.8){
                thSwingLeft.setTargetPos(0.7);
                thSwingRight.setTargetPos(0.3);
            }
            if (gamepad2.left_trigger>0.8) {
                thSwingLeft.setTargetPos(0.1);
                thSwingRight.setTargetPos(0.9);
            }
            if (gamepad2.x) {
                thSwingLeft.setTargetPos(0.4);
                thSwingRight.setTargetPos(0.6);
            }
            if (gamepad2.y) {
                thSwingLeft.setTargetPos(0.6);
                thSwingRight.setTargetPos(0.4);
            }



            if (gamepad1.a)
                powerLimit = 0.6;
            else
                powerLimit = 0.3;


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


            telemetry.addData("Status", "Robot is moving" + runtime.toString());
            telemetry.addData("gamepad controller values", "%4.2f, %4.2f", axial, lateral, yaw);
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
            telemetry.addData("Time", "runtime");
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);
            telemetry.addData("Lift", lift.getCurrentPosition());

            telemetry.update();
        }

    }}
