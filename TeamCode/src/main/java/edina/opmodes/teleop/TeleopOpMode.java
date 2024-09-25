package edina.opmodes.teleop;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import edina.library.util.Robot;
import edina.library.util.RobotConfiguration;
import edina.library.util.RobotHardware;
import edina.library.util.RobotState;
import edina.library.util.SmartGamepad;

public class TeleopOpMode extends OpMode {
    protected Robot robot;
    protected SmartGamepad driver1Gamepad;
    protected SmartGamepad driver2Gamepad;

    @Override
    public void start() {
        robot.start();
    }

    @Override
    public void init() {
        driver1Gamepad = new SmartGamepad(gamepad1);
        driver2Gamepad = new SmartGamepad(gamepad2);

    }

    // hit after init is called and before play
    // great place to put vision code to detect where to go for autonomous
    @Override
    public void init_loop() {
        RobotState state = RobotState.getInstance();
        RobotHardware hardware = robot.RobotHardware;
        RobotConfiguration config = RobotConfiguration.getInstance();

        telemetry.update();

    }


    @Override
    public void loop() {

        driver1Gamepad.update();
        driver2Gamepad.update();


        //robot.MecanumDrive.setProperties(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);

        robot.Intake.setProperties(driver1Gamepad.left_bumper, driver1Gamepad.right_bumper);

        robot.Lift.setProperties(gamepad2.right_trigger != 0, gamepad2.left_trigger != 0,
                gamepad2.left_bumper, gamepad2.right_bumper);

        telemetry.addData("Gamepad2.right", gamepad2.right_trigger);
        telemetry.addData("Gamepad2.left", gamepad2.left_trigger);
        telemetry.addData("Lift Position", RobotState.getInstance().liftTipState);
        telemetry.addData("started", robot.Started);
        robot.update();
        robot.telemetry();
    }

    @Override
    public  void stop() {
        robot.stop();
    }
}