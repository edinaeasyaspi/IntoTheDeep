package org.firstinspires.ftc.teamcode.Test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

// Don't know exactly every part of the code in this project as there are many constant objects interacting,
// so this is just a placeholder and test of the actual thing.

@Autonomous
public class SwingTest extends LinearOpMode {

    private Servo swingLeft;
    private Servo swingRight;
    private ServoThrottle thSwingLeft, thSwingRight;

    @Override
    public void runOpMode() {

        swingLeft = hardwareMap.get(Servo.class, "swingLeft");
        swingRight = hardwareMap.get(Servo.class, "swingRight");


                thSwingLeft = new ServoThrottle(swingLeft, 0.82, 0.1);
                thSwingRight = new ServoThrottle(swingRight, 0.82, 0.1);

                thSwingLeft.setTargetPos(0.0);
                thSwingRight.setTargetPos(0.0);

                waitForStart();
                thSwingLeft.setTargetPos(0.13);

                thSwingRight.setTargetPos(0.87);
                sleep(1000);


                thSwingLeft.setTargetPos(0.87);
                thSwingRight.setTargetPos(0.13);
                sleep(1000);


    }
    }

