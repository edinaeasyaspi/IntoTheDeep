package org.firstinspires.ftc.teamcode.TeleOp;


import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Test.ServoThrottle;


import org.firstinspires.ftc.teamcode.RobotHardware;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="Linear OpMode")
//@Disabled
public class SoloTeleOp extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor lfd = null;
    private DcMotor lbd = null;
    private DcMotor rfd = null;
    private DcMotor rbd = null;
    private DcMotor lift = null;
    private CRServo armExtend = null;

    private Servo swingLeft, swingRight;

    public RobotHardware hw = null;


    double clawOffset = 0;
    double liftPos = 0;


    //    private DcMotor liftMotor = null;
    private Servo clawRight, clawLeft = null;
    //    private Servo armExtend = null;
    public static final double LIFT_POWER = 0.4;








    @Override
    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        lfd  = hardwareMap.get(DcMotor.class, "lfd");
        lbd  = hardwareMap.get(DcMotor.class, "lbd");
        rfd = hardwareMap.get(DcMotor.class, "rfd");
        rbd = hardwareMap.get(DcMotor.class, "rbd");
        lift = hardwareMap.get(DcMotor.class, "lift");
        clawLeft = hardwareMap.get(Servo.class, "clawLeft");
        clawRight = hardwareMap.get(Servo.class, "clawRight");
        swingLeft = hardwareMap.get(Servo.class, "swingLeft");
        swingRight = hardwareMap.get(Servo.class, "swingRight");


//        bar1left = hardwareMap.get(Servo.class, "bar1left");
//        bar1right = hardwareMap.get(Servo.class, "bar1right");
        armExtend = hardwareMap.get(CRServo.class, "armExtend");

        ServoThrottle thSwingLeft, thSwingRight;
        thSwingLeft= new ServoThrottle(swingLeft, 0.82, 0.87);
        thSwingRight = new ServoThrottle(swingRight, 0.82, 0.13);







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
        lift.setDirection(DcMotorSimple.Direction.FORWARD);



        lfd.setZeroPowerBehavior(BRAKE);
        lbd.setZeroPowerBehavior(BRAKE);
        rbd.setZeroPowerBehavior(BRAKE);
        rfd.setZeroPowerBehavior(BRAKE);
        lift.setZeroPowerBehavior(BRAKE);
    /*
    liftMotor.setZeroPowerBehavior(BRAKE);
       PwmControl[] otherServos = new PwmControl[]{
                ((PwmControl) clawRight),
                ((PwmControl) clawLeft),
                ((PwmControl) bar1left),
                ((PwmControl) bar1right),
                ((PwmControl) armExtend),

        };
 Wait for the game to start (driver presses START)
*/



        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {


            double powerLimit = 0.2;
            //   double noLift = liftMotor.getCurrentPosition();
            //   double liftPosition = noLift;

            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double axial   = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            double lateral =  gamepad1.left_stick_x;
            double yaw     =  gamepad1.right_stick_x;
            // lift = gamepad2.dpad_up;


            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.
            double leftFrontPower  = axial + lateral + yaw;
            double rightFrontPower = axial - lateral - yaw;
            double leftBackPower   = axial - lateral + yaw;
            double rightBackPower  = axial + lateral - yaw;
            //    double liftPower = lift;

            // Normalize the values so no wheel power exceeds 100%
            // This ensures that the robot maintains the desired motion.
            double max = Math.max(Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower)),
                    Math.max(Math.abs(leftBackPower), Math.abs(rightBackPower)));

            if (max > 0.5) {
                leftBackPower /= max;
                rightBackPower /= max;
                leftFrontPower /= max;
                rightFrontPower /= max;
            }




            // Set motor power
            lfd.setPower(leftFrontPower);
            lbd.setPower(leftBackPower);
            rfd.setPower(rightFrontPower);
            rbd.setPower(rightBackPower);

            if (gamepad1.dpad_up) {
                lift.setPower(0.7);
            } else if (gamepad1.dpad_down) {
                lift.setPower(-0.5);
            } else {
                lift.setPower(0);
            }
            if (gamepad1.a ) {
                armExtend.setPower(-1);
            } else if (gamepad1.b) {
                armExtend.setPower(1);
            } else {
                armExtend.setPower(0);
            }





            //INTAKE and EXPEL
//            if (gamepad2.right_bumper) {
//                clawLeft.setPosition(0.1);
//                clawRight.setPosition(1);
//            }
//
//            if (gamepad2.left_bumper) {
//                clawLeft.setPosition(0.3);
//                    clawRight.setPosition(0.8);
//            }
            if (gamepad1.right_bumper) {
                clawLeft.setPosition(0.1);
                clawRight.setPosition(0.5);
            }
            if (gamepad1.left_bumper) {
                clawLeft.setPosition(0.2);
                clawRight.setPosition(0.4);
            }


            if(gamepad1.right_trigger > 0.8){
                thSwingLeft.setTargetPos(0.1);
                thSwingRight.setTargetPos(0.9);
            }

            if (gamepad1.left_trigger>0.8) {
                thSwingLeft.setTargetPos(0.87);
                thSwingRight.setTargetPos(0.13);
            }

//            public void openClaw() {
//                clawLeft.setPosition();
//            }

//            if (gamepad1.a) {
//           //     armExtend.setPosition(1.0);
//            } else if (gamepad1.b) {
//                armExtend.setPosition(0);
//            }
            //LIFT AND RETRACT SLIDES


  /*          if (gamepad2.dpad_up) {
                liftMotor.setPower(LIFT_POWER);
                liftMotor.setZeroPowerBehavior(BRAKE);
            } else if (gamepad2.dpad_down) {
                liftMotor.setPower(-LIFT_POWER);
                liftMotor.setZeroPowerBehavior(BRAKE);
            } else liftMotor.setPower(0);

            if (gamepad1.a) {
               clawLeft.setPosition(1.0);
               clawRight.setPosition(-1.0);
            }







**/










            if (gamepad1.y)
                powerLimit = 0.5;
            else
                powerLimit = 0.2;


            lfd.setPower(leftFrontPower * powerLimit);
            lbd.setPower(leftBackPower * powerLimit);
            rfd.setPower(rightFrontPower * powerLimit);
            rbd.setPower(rightBackPower * powerLimit);

   /*  if (gamepad2.dpad_up) {
                            if (liftMotor.getCurrentPosition()) {
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
            **/

            thSwingLeft.run();
            thSwingRight.run();

            // Send calculated power to wheels
            lfd.setPower(leftFrontPower);
            rfd.setPower(rightFrontPower);
            rbd.setPower(rightBackPower);
            lbd.setPower(leftBackPower);
            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Karthik is really bad at driving" + runtime.toString());
            telemetry.addData("gamepad controller values", "%4.2f, %4.2f", axial, lateral, yaw);
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
            telemetry.addData("Time", "runtime");
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);

            telemetry.update();
        }

    }}
