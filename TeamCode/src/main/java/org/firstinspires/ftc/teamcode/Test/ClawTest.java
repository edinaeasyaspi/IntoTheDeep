package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class ClawTest extends LinearOpMode {

    private Servo clawLeft;
    private Servo clawRight;
    private ServoThrottle thClawLeft, thClawRight;

    @Override
    public void runOpMode() {

        clawLeft = hardwareMap.get(Servo.class, "clawLeft");
        clawRight = hardwareMap.get(Servo.class, "clawRight");

        thClawLeft = new ServoThrottle(clawLeft, 1.0, 0.2);
        thClawRight = new ServoThrottle(clawRight, 1.0, 0.2);

        thClawLeft.setTargetPos(0.2);
        thClawRight.setTargetPos(0.2);

        waitForStart();

        // Open the claw
        thClawLeft.setTargetPos(1.0);
        thClawRight.setTargetPos(1.0);
        sleep(1000);

        // Close the claw
        thClawLeft.setTargetPos(0.2);
        thClawRight.setTargetPos(0.2);
        sleep(1000);
    }
}