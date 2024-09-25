package edina.opmodes.teleop.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import edina.library.util.Positioning;
import edina.library.util.RobotHardware;
import edina.library.util.drivecontrol.DriveDirection;
import edina.library.util.drivecontrol.PiDrive;

;

@Disabled
@Autonomous
public class DriveTestMode extends LinearOpMode {
    public DriveTestMode() {
        dir = DriveDirection.Axial;
    }

    private PiDrive pd;

    protected DriveDirection dir;

    // how to do dpi calibration: drive, read actualDeg from Driver Station, measure distance with tape measure,divide actualDeg by measured distance
    @Override
    public void runOpMode() {
        RobotHardware hw = new RobotHardware(hardwareMap, telemetry);
        Positioning posn = new Positioning(hw);

        pd = new PiDrive(hw, posn);
        waitForStart();

        test(20);
        sleep(1000);
        test(-20);

        for (int trial = 0; trial < 10; trial++) {
            if (pd.getActiveMoveCal().deccel >= 0)
                break;

            test(12);
            sleep(1000);
            test(-12);
        }
    }

    private void test(double targetDist) {
        pd.preRun(targetDist, dir);

        telemetry.addData("deccel", "deccel=%.1f", pd.getActiveMoveCal().deccel);
        telemetry.addData("pos", "target=%.1f", targetDist);
        telemetry.update();
        sleep(500);

        while (opModeIsActive()) {
            telemetry.addData("drive", pd.moveString());
            telemetry.update();

            boolean done = pd.run();
            if (done) break;
        }

        double actualPos = pd.getPos();
        double actualDeg = pd.getDeg();

        telemetry.addData("deccel", "deccel=%.1f", pd.getActiveMoveCal().deccel);
        telemetry.addData("pos", "target=%.1f, actual=%.1f, actualDeg=%.1f", targetDist, actualPos, actualDeg);
        telemetry.update();
        sleep(3000);
    }
}
