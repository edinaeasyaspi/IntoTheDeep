package edu.edina.opmodes.teleop.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class LiftAdjustmentMode extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotorEx liftMotor = hardwareMap.get(DcMotorEx.class, "liftMotor");

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.dpad_up)
                liftMotor.setPower(0.5);
            else if (gamepad1.dpad_down)
                liftMotor.setPower(-0.4);
            else
                liftMotor.setPower(0);

            telemetry.addData("motor pos", liftMotor.getCurrentPosition());
            telemetry.update();
        }
    }
}

