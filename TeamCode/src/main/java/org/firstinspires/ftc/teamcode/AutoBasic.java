package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name="AutoBasic", group="Robot")

public class AutoBasic extends LinearOpMode{


    private DcMotor lfd, rfd, rbd, lbd = null;

    private ElapsedTime runtime = new ElapsedTime();

    static final double TURN_SPEED = 1.0;
    static final double FORWARD_SPEED = 1.0;
    static final double BACKWARD_SPEED = 1.0;


    @Override
    public void runOpMode() throws InterruptedException {
        
    }
}
