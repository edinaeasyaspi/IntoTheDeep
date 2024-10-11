package edu.edina.opmodes.teleop.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import edu.edina.library.util.DriveStatus;
import edu.edina.library.util.PiBot;
import edu.edina.library.util.Point;
import edu.edina.library.util.RobotHardware;
import edu.edina.library.util.drivecontrol.DriveDirection;

@Disabled
@Autonomous
public class RouteTest extends LinearOpMode {
    private PiBot piBot;

    @Override
    public void runOpMode() {
        RobotHardware hw = new RobotHardware(hardwareMap, telemetry);
        piBot = new PiBot(hw);

        waitForStart();

        Point startLocation = new Point(72, 72);

        while (opModeIsActive()) {
            drive(new Point(72, 96), DriveDirection.Axial);
            drive(new Point(96, 96), DriveDirection.Lateral);
            rotate(startLocation);
            drive(startLocation, DriveDirection.Axial);
            rotate(new Point(72, 96));
        }
    }

    private void drive(Point to, DriveDirection direction) {
        piBot.planDriveToClosestPoint(to, direction);

        while (opModeIsActive()) {
            if (piBot.runDrive() == DriveStatus.Done) break;
        }
    }

    private void rotate(Point to) {
        piBot.planRotateToPoint(to);
        while (opModeIsActive()) {
            if (piBot.runRotate() == DriveStatus.Done) break;
            sleep(1);
        }

        showPos();
    }

    private void rotate(double to) {
        piBot.planRotateToHeading(to);
        while (opModeIsActive()) {
            if (piBot.runRotate() == DriveStatus.Done) break;
            sleep(1);
        }

        showPos();
    }

    private void showPos() {
        telemetry.addData("pos", piBot.getPositioning().getCurrPos());
        telemetry.update();
        sleep(1000);
    }
}
