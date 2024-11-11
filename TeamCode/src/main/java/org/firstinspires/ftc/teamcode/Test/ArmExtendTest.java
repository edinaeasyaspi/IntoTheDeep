package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class ArmExtendTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        CRServo armExtend = hardwareMap.get(CRServo.class, "armExtend");
        Servo clawLeft = hardwareMap.get(Servo.class, "clawLeft");
        Servo clawRight = hardwareMap.get(Servo.class, "clawRight");
    Servo swingLeft = hardwareMap.get(Servo.class, "swingLeft");
        Servo swingRight = hardwareMap.get(Servo.class, "swingRight");
      //  DcMotor lift = hardwareMap.get(DcMotor.class, "lift");

         ElapsedTime     runtime = new ElapsedTime();

        ServoThrottle thSwingLeft, thSwingRight;
        thSwingLeft= new ServoThrottle(swingLeft, 0.82, 0.87);
        thSwingRight = new ServoThrottle(swingRight, 0.82, 0.13);
        
       



        waitForStart();
        while (opModeIsActive()) {
//            if (gamepad2.right_bumper) {
//                armExtend.setPower(1);
//            }
//            if (gamepad2.right_trigger > 0.8) {
//                armExtend.setPower(-1.0);
//            }

            if (gamepad2.a ) {
                armExtend.setPower(-1);
            } else if (gamepad2.b) {
                armExtend.setPower(1);
            } else {
                armExtend.setPower(0);
            }
                if (gamepad1.right_bumper) {
                    clawLeft.setPosition(0.1);
                    clawRight.setPosition(0.5);
                }
                if (gamepad1.left_bumper) {
                    clawLeft.setPosition(0.2);
                    clawRight.setPosition(0.4);
                }
//                if (gamepad1.dpad_up) {
//                    swingRight.setPosition(1);
//                    swingLeft.resetDeviceConfigurationForOpMode();
//                }


            if(gamepad2.right_trigger > 0.8){
                thSwingLeft.setTargetPos(0.1);
                thSwingRight.setTargetPos(0.9);
            }

            if (gamepad2.left_trigger>0.8) {
                thSwingLeft.setTargetPos(0.87);
                thSwingRight.setTargetPos(0.13);
            }



                if (gamepad1.dpad_left) {
                    swingLeft.setPosition(1);

                    //swingRight.resetDeviceConfigurationForOpMode();
                }
                if (gamepad1.y) {
                    swingLeft.setPosition(0);
                }
//                if (gamepad1.a) {
//                    lift.setPower(0.5);
//                            lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//                } else if (gamepad1.b) {
//                    lift.setPower(-0.5);
//                    lift.setTargetPosition(2000);
//                    lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//                } else
//                    lift.setPower(0);
                if (gamepad2.a) {
                    swingRight.setPosition(0);

                }
                if (gamepad2.y) {
                    swingRight.setPosition(1);
                }

            thSwingLeft.run();
            thSwingRight.run();

        }}}