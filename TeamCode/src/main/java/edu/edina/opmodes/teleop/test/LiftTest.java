package edu.edina.opmodes.teleop.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@TeleOp
public class LiftTest extends LinearOpMode {
    public DcMotor liftMotor = null;

    @Override
    public void runOpMode() throws InterruptedException {
        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setDirection(DcMotor.Direction.FORWARD);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad2.right_bumper) {
               liftMotor.setPower(0.5);
               liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            } else {
               liftMotor.setPower(0);
            }
        }
    }
}



