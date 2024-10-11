package edu.edina.opmodes.teleop.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp
public class HangingMotorTest extends OpMode {
    public Servo leftLiftServo = null;
    public Servo rightLiftServo = null;

    @Override
    public void init() {
        leftLiftServo = hardwareMap.get(Servo.class, "S1");
        rightLiftServo = hardwareMap.get(Servo.class, "S2");
    }

    @Override
    public void loop() {
        HangingMotorCode();
    }

    public void HangingMotorCode() {
        if (gamepad1.dpad_up) {
            // Extend the servos all the way
            leftLiftServo.setPosition(Servo.MAX_POSITION);
            rightLiftServo.setPosition(Servo.MAX_POSITION);
        } else if (gamepad1.dpad_down) {
            // Retract the servos
            leftLiftServo.setPosition(Servo.MIN_POSITION);
            rightLiftServo.setPosition(Servo.MIN_POSITION);
        } else {
            // Stop the servos
            leftLiftServo.setPosition(Servo.MIN_POSITION); // You can adjust this position if needed
            rightLiftServo.setPosition(Servo.MIN_POSITION);
        }
    }
}
