package org.firstinspires.ftc.teamcode.teleop.test;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import edina.library.util.RobotHardware;

@Disabled
@Autonomous
public class GyroTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        ModernRoboticsI2cGyro gyro = new RobotHardware(hardwareMap).gyro;
        gyro.calibrate();
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("gyro heading", gyro.getHeading());
            telemetry.update();
        }
    }
}
