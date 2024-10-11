package edu.edina.opmodes.teleop.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import edu.edina.library.util.PiBot;
import edu.edina.library.util.RobotHardware;
import edu.edina.library.util.drivecontrol.Accelerometer;
import edu.edina.library.util.drivecontrol.PiMotor;

@Disabled
@Autonomous
public class AccelTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        PiBot bot = new PiBot(new RobotHardware(hardwareMap));
        waitForStart();

        test(bot, new int[]{1, 1, 1, 1});
        test(bot, new int[]{1, -1, -1, 1});
    }

    private void test(PiBot bot, int[] move) {
        Accelerometer[] a = new Accelerometer[4];
        for (int i = 0; i < 4; i++) {
            PiMotor m = bot.drive.get(i);
            m.resetDeg();
            a[i] = new Accelerometer(300);
        }

        ElapsedTime t = new ElapsedTime();
        while (opModeIsActive()) {
            boolean reverse = false;
            for (int i = 0; i < 4; i++) {
                PiMotor m = bot.drive.get(i);
                m.sample();

                double d = move[i] * m.getDeg();
                double s = move[i] * m.getDegSpeed();

                if (t.seconds() < 0.5) {
                    m.setDriving(true, 1);
                } else {
                    m.setDriving(false, 1);
                    a[i].sample(d);

                    if (s < 0)
                        reverse = true;
                }

                m.run(s, move[i], 1, 0);
            }

            if (reverse)
                break;
        }

        double sum = 0;
        for (int i = 0; i < 4; i++) {
            telemetry.addData("motor", "%d deccel at %.1f", i, a[i].getAccel());
            sum += a[i].getAccel();
            bot.drive.get(i).shutdown();
        }
        telemetry.addData("avg", "deccel at %.1f", sum / 4);
        telemetry.update();

        if (opModeIsActive())
            sleep(2000);
    }
}
