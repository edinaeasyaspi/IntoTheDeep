package edina.opmodes.teleop.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import edina.library.util.PiBot;
import edina.library.util.RobotHardware;

@Disabled
@Autonomous
public class VisionTestMode extends LinearOpMode {
    @Override
    public void runOpMode() {
        PiBot bot = new PiBot(new RobotHardware(hardwareMap, telemetry));

        while (opModeInInit()) {
            bot.getSelection();
            telemetry.update();
        }

        waitForStart();

        while (opModeIsActive()) {
            sleep(1);
        }
    }
}
