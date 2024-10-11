package edu.edina.opmodes.teleop.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp
public class IntakeTest extends LinearOpMode {
    Servo intakeLeft = hardwareMap.get(Servo.class, "intakeLeft");
    Servo intakeRight = hardwareMap.get(Servo.class, "intakeRight");
    Servo intakeSwingLeft = hardwareMap.get(Servo.class, "intakeSwingLeft");
    Servo intakeSwingRight = hardwareMap.get(Servo.class, "intakeSwingRight");

    @Override
    public void runOpMode() {


        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.right_bumper) {
                intakeRight.setPosition(0.4);
            }
            if (gamepad1.left_bumper) {
                intakeLeft.setPosition(0.6);
            }
            if (gamepad1.right_bumper && gamepad1.left_bumper) {
                intakeRight.setPosition(0.4);
                intakeLeft.setPosition(0.6);
            }
            if (gamepad1.x) {
                intakeRight.setPosition(0.6);
                intakeLeft.setPosition(0.4);
            }
        }
    }
}