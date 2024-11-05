package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Test.ServoThrottle;

import org.firstinspires.ftc.teamcode.TeleOp.Enums.IntakeState;

@TeleOp
public class ArmExtendTest extends LinearOpMode {
    @Override
    public void runOpMode() {
    //    CRServo armExtend = hardwareMap.get(CRServo.class, "armExtend");
        Servo clawLeft = hardwareMap.get(Servo.class, "clawLeft");
        Servo clawRight = hardwareMap.get(Servo.class, "clawRight");
       Servo swingLeft = hardwareMap.get(Servo.class, "swingLeft");
        Servo swingRight = hardwareMap.get(Servo.class, "swingRight");



        waitForStart();
        while (opModeIsActive()) {
//            if (gamepad2.right_bumper) {
//                armExtend.setPower(1);
//            }
//            if (gamepad2.right_trigger > 0.8) {
//                armExtend.setPower(-1.0);
//            }
                if (gamepad1.right_bumper) {
                    clawLeft.setPosition(0.1);
                    clawRight.setPosition(1);
                }
                if (gamepad1.left_bumper) {
                    clawLeft.setPosition(0.3);
                    clawRight.setPosition(0.8);
                }
                if (gamepad1.dpad_up) {
                    swingRight.setPosition(0.55);
                    swingLeft.setPosition(0.45);
                }
                if (gamepad1.dpad_down) {
                    swingRight.setPosition(0.1);
                    swingLeft.setPosition(0.9);
                }



        }}}