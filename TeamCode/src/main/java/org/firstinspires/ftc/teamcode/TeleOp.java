package org.firstinspires.ftc.teamcode;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.PwmControl;


import org.firstinspires.ftc.robotcontroller.external.samples.RobotHardware;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Basic Mecanum Drive", group="Linear OpMode")
//@Disabled
public class TeleOp extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor lfd = null;
    private DcMotor lbd = null;
    private DcMotor rfd = null;
    private DcMotor rbd = null;

    public RobotHardware hardware = null;

    //private DcMotor liftMotor = null;
  //  private Servo clawRight, clawLeft, bar1left, bar1right = null;
    //private CRServo armExtend = null;





    @Override
    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        lfd  = hardwareMap.get(DcMotor.class, "lfd");
        lbd  = hardwareMap.get(DcMotor.class, "lbd");
        rfd = hardwareMap.get(DcMotor.class, "rfd");
        rbd = hardwareMap.get(DcMotor.class, "rbd");
        //liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
        //clawLeft = hardwareMap.get(Servo.class, "clawLeft");
        //clawRight = hardwareMap.get(Servo.class, "clawRight");
        //bar1left = hardwareMap.get(Servo.class, "bar1left");
        //bar1Right = hardwareMap.get(Servo.class, "bar1right");
        //armExtend = hardwareMap.get(CRServo.class, "armExtend");






        // ########################################################################################
        // !!!            IMPORTANT Drive Information. Test your motor directions.            !!!!!
        // ########################################################################################
        // Most robots need the motors on one side to be reversed to drive forward.
        // The motor reversals shown here are for a "direct drive" robot (the wheels turn the same direction as the motor shaft)
        // If your robot has additional gear reductions or uses a right-angled drive, it's important to ensure
        // that your motors are turning in the correct direction.  So, start out with the reversals here, BUT
        // when you first test your robot, push the left joystick forward and observe the direction the wheels turn.
        // Reverse the direction (flip FORWARD <-> REVERSE ) of any wheel that runs backward
        // Keep testing until ALL the wheels move the robot forward when you push the left joystick forward.
        lfd.setDirection(DcMotor.Direction.REVERSE);
        lbd.setDirection(DcMotor.Direction.REVERSE);
        rfd.setDirection(DcMotor.Direction.FORWARD);
        rbd.setDirection(DcMotor.Direction.FORWARD);


        lfd.setZeroPowerBehavior(BRAKE);
        lbd.setZeroPowerBehavior(BRAKE);
        rbd.setZeroPowerBehavior(BRAKE);
        rfd.setZeroPowerBehavior(BRAKE);
     //   liftMotor.setZeroPowerBehavior(BRAKE);

//       PwmControl[] otherServos = new PwmControl[]{
//                ((PwmControl) clawRight),
//                ((PwmControl) clawLeft),
//                ((PwmControl) bar1left),
//                ((PwmControl) bar1right),
//                ((PwmControl) armExtend),
//
//        };

        // Wait for the game to start (driver presses START)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            double max;
            double powerLimit = 1;
         //   double noLift = liftMotor.getCurrentPosition();
         //   double liftPosition = noLift;

            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double axial   = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            double lateral =  gamepad1.left_stick_x;
            double yaw     =  gamepad1.right_stick_x;


            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.
            double leftFrontPower  = axial + lateral + yaw;
            double rightFrontPower = axial - lateral - yaw;
            double leftBackPower   = axial - lateral + yaw;
            double rightBackPower  = axial + lateral - yaw;

            // Normalize the values so no wheel power exceeds 100%
            // This ensures that the robot maintains the desired motion.
            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));



            if (max > 1) {
                leftFrontPower  /= max;
                rightFrontPower /= max;
                leftBackPower   /= max;
                rightBackPower  /= max;

            }



            if (gamepad1.a)
                powerLimit = 1;
            else
                powerLimit = 0.5;


            lfd.setPower(leftFrontPower * powerLimit);
            lbd.setPower(leftBackPower * powerLimit);
            rfd.setPower(rightFrontPower * powerLimit);
            rbd.setPower(rightBackPower * powerLimit);

 /*    if (gamepad2.dpad_up) {
                            if (liftMotor.getCurrentPosition() < noLift + 750) {
                                liftMotor.setPower(0.7);
                                liftPosition = liftMotor.getCurrentPosition();
                            }
                        } else if (gamepad2.dpad_down) {
                            liftMotor.setPower(-0.7);
                            liftPosition = liftMotor.getCurrentPosition();
                        } else {
                            double y = liftMotor.getCurrentPosition() - liftPosition;
                            liftMotor.setPower(y / 100);
                        }


            /*
            leftFrontPower  = gamepad1.x ? 1.0 : 0.0;  // X gamepad
            leftBackPower   = gamepad1.a ? 1.0 : 0.0;  // A gamepad
            rightFrontPower = gamepad1.y ? 1.0 : 0.0;  // Y gamepad
            rightBackPower  = gamepad1.b ? 1.0 : 0.0;  // B gamepad
            */

            // Send calculated power to wheels
            lfd.setPower(leftFrontPower);
            rfd.setPower(rightFrontPower);
            rbd.setPower(rightBackPower);
            lbd.setPower(leftBackPower);
            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Karthik is really bad at driving" + runtime.toString());
            telemetry.addData("gamepad controller values", "%4.2f, %4.2f", axial, lateral, yaw);
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);

            telemetry.update();
        }
    }}
