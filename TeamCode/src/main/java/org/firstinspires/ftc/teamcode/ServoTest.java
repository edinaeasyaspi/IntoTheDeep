package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp
public class ServoTest extends LinearOpMode {
    private CRServo intakeServo = null;

    @Override
    public void runOpMode() {
        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.right_bumper) {
                telemetry.addData("Servo", "position");
                telemetry.update();

                intakeServo.setPower(1);



                sleep(5000);



            }
            if (gamepad1.left_bumper) {
               intakeServo.setPower(0);


        }
    }
}}


