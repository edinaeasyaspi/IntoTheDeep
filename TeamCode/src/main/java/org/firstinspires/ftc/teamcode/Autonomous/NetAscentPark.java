// VERY BASIC HAVENT CHANGED BUT JUST THE RIBCAGE OF THE ACTUAL THING.
// WE HAVE TO TRIAL AND ERROR AND SEE WHAT TO CHANGE.


package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Test.ServoThrottle;
import org.opencv.dnn.Net;


public class NetAscentPark {
    /* Copyright (c) 2017 FIRST. All rights reserved.
     *
     * Redistribution and use in source and binary forms, with or without modification,
     * are permitted (subject to the limitations in the disclaimer below) provided that
     * the following conditions are met:
     *
     * Redistributions of source code must retain the above copyright notice, this list
     * of conditions and the following disclaimer.
     *
     * Redistributions in binary form must reproduce the above copyright notice, this
     * list of conditions and the following disclaimer in the documentation and/or
     * other materials provided with the distribution.
     *
     * Neither the name of FIRST nor the names of its contributors may be used to endorse or
     * promote products derived from this software without specific prior written permission.
     *
     * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
     * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
     * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
     * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
     * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
     * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
     * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
     * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
     * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
     * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
     * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
     */

    /*
     * This OpMode illustrates the concept of driving a path based on encoder counts.
     * The code is structured as a LinearOpMode
     *
     * The code REQUIRES that you DO have encoders on the wheels,
     *   otherwise you would use: RobotAutoDriveByTime;
     *
     *  This code ALSO requires that the drive Motors have been configured such that a positive
     *  power command moves them forward, and causes the encoders to count UP.
     *
     *   The desired path in this example is:
     *   - Drive forward for 48 inches
     *   - Spin right for 12 Inches
     *   - Drive Backward for 24 inches
     *   - Stop and close the claw.
     *
     *  The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
     *  that performs the actual movement.
     *  This method assumes that each movement is relative to the last stopping place.
     *  There are other ways to perform encoder based moves, but this method is probably the simplest.
     *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
     *
     * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
     * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
     */


    @Disabled
    public class RobotAutoDriveByEncoder_Linear extends LinearOpMode {

        /* Declare OpMode members. */
        private DcMotor lfd, lbd, rfd, rbd = null;


        private ElapsedTime runtime = new ElapsedTime();

        // Calculate the COUNTS_PER_INCH for your specific drive train.
        // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
        // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
        // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
        // This is gearing DOWN for less speed and more torque.
        // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
        static final double COUNTS_PER_MOTOR_REV = 537.7;    // eg: TETRIX Motor Encoder
        static final double DRIVE_GEAR_REDUCTION = 1.0;     // No External Gearing.
        static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
        static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                (WHEEL_DIAMETER_INCHES * 3.1415);
        static final double DRIVE_SPEED = 0.6;
        static final double TURN_SPEED = 0.5;

        @Override
        public void runOpMode() {

            // Initialize the drive system variables.
            lfd = hardwareMap.get(DcMotor.class, "lfd");
            rfd = hardwareMap.get(DcMotor.class, "rfd");
            rbd = hardwareMap.get(DcMotor.class, "rbd");
            lbd = hardwareMap.get(DcMotor.class, "lbd");


            // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
            // When run, this OpMode should start both motors driving forward. So adjust these two lines based on your first test drive.
            // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
            lfd.setDirection(DcMotor.Direction.REVERSE);
            rfd.setDirection(DcMotor.Direction.FORWARD);
            rbd.setDirection(DcMotor.Direction.FORWARD);
            lbd.setDirection(DcMotor.Direction.REVERSE);

            lfd.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rfd.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            lbd.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rbd.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


            lfd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rfd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lbd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rbd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            // Send telemetry message to indicate successful Encoder reset
            telemetry.addData("Starting at", "%7d :%7d",
                    lfd.getCurrentPosition(),
                    rfd.getCurrentPosition(),
                    lbd.getCurrentPosition(),
                    rbd.getCurrentPosition());
            telemetry.update();

            // Wait for the game to start (driver presses START)
            waitForStart();

            // Step through each leg of the path,
            // Note: Reverse movement is obtained by setting a negative distance (not speed)
            encoderDrive(DRIVE_SPEED, 48, 48, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
            encoderDrive(TURN_SPEED, 12, -12, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
            encoderDrive(DRIVE_SPEED, -24, -24, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout

            telemetry.addData("Path", "Complete");
            telemetry.update();
            sleep(1000);  // pause to display final telemetry message.
        }

        /*
         *  Method to perform a relative move, based on encoder counts.
         *  Encoders are not reset as the move is based on the current position.
         *  Move will stop if any of three conditions occur:
         *  1) Move gets to the desired position
         *  2) Move runs out of time
         *  3) Driver stops the OpMode running.
         */
        public void encoderDrive(double speed,
                                 double leftInches, double rightInches,
                                 double timeoutS) {
            int newlfdTarget;
            int newlbdTarget;
            int newrfdTarget;
            int newrbdTarget;


            // Ensure that the OpMode is still active
            if (opModeIsActive()) {

                // Determine new target position, and pass to motor controller
                newlfdTarget = lfd.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
                newlbdTarget = lbd.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
                newrfdTarget = rfd.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
                newrbdTarget = rbd.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
                lfd.setTargetPosition(newlfdTarget);
                lbd.setTargetPosition(newlbdTarget);
                rbd.setTargetPosition(newrbdTarget);
                rfd.setTargetPosition(newrfdTarget);

                // Turn On RUN_TO_POSITION
                lfd.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rfd.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lbd.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rbd.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                // reset the timeout time and start motion.
                runtime.reset();
                lfd.setPower(Math.abs(speed));
                rfd.setPower(Math.abs(speed));
                lbd.setPower(Math.abs(speed));
                rbd.setPower(Math.abs(speed));

                // keep looping while we are still active, and there is time left, and both motors are running.
                // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
                // its target position, the motion will stop.  This is "safer" in the event that the robot will
                // always end the motion as soon as possible.
                // However, if you require that BOTH motors have finished their moves before the robot continues
                // onto the next step, use (isBusy() || isBusy()) in the loop test.
                while (opModeIsActive() &&
                        (runtime.seconds() < timeoutS) &&
                        (lfd.isBusy() && rfd.isBusy() && lbd.isBusy() && rbd.isBusy())) {

                    // Display it for the driver.
                    telemetry.addData("Running to", " %7d :%7d", newlfdTarget, newrfdTarget, newlbdTarget, newrbdTarget);
                    telemetry.addData("Currently at", " at %7d :%7d",
                            lfd.getCurrentPosition(), rfd.getCurrentPosition(), lbd.getCurrentPosition(), rbd.getCurrentPosition());
                    telemetry.update();
                }

                // Stop all motion;
                lfd.setPower(0);
                rfd.setPower(0);
                lbd.setPower(0);
                rbd.setPower(0);

                // Turn off RUN_TO_POSITION
                lfd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                lbd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rbd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rfd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                sleep(250);   // optional pause after each move.


                @TeleOp
                 class ArmExtendTest extends NetAscentPark {

                    public void runOpMode() {
                        CRServo armExtend = hardwareMap.get(CRServo.class, "armExtend");
                        Servo clawLeft = hardwareMap.get(Servo.class, "clawLeft");
                        Servo clawRight = hardwareMap.get(Servo.class, "clawRight");
                        Servo swingLeft = hardwareMap.get(Servo.class, "swingLeft");
                        Servo swingRight = hardwareMap.get(Servo.class, "swingRight");
                        DcMotor lift = hardwareMap.get(DcMotor.class, "lift");

                        ElapsedTime     runtime = new ElapsedTime();

                        ServoThrottle thSwingLeft, thSwingRight;
                        thSwingLeft= new ServoThrottle(swingLeft, 0.82, 1.0);
                        thSwingRight = new ServoThrottle(swingRight, 0.82, 0.0);
                        double noLift = lift.getCurrentPosition();
                        double liftPosition = noLift;






                        waitForStart();
                        while (opModeIsActive()) {
//            if (gamepad2.right_bumper) {
//                armExtend.setPower(1);
//            }
//            if (gamepad2.right_trigger > 0.8) {
//                armExtend.setPower(-1.0);
//            }

                            if (gamepad2.a ) {
                                armExtend.setPower(-1);
                            } else if (gamepad2.b) {
                                armExtend.setPower(1);
                            } else {
                                armExtend.setPower(0);
                            }
                            if (gamepad1.right_bumper) {
                                clawLeft.setPosition(0.1);
                                clawRight.setPosition(0.5);
                            }
                            if (gamepad1.left_bumper) {
                                clawLeft.setPosition(0.2);
                                clawRight.setPosition(0.4);
                            }
//                if (gamepad1.dpad_up) {
//                    swingRight.setPosition(1);
//                    swingLeft.resetDeviceConfigurationForOpMode();
//                }


                            if(gamepad2.right_trigger > 0.8){
                                thSwingLeft.setTargetPos(0.1);
                                thSwingRight.setTargetPos(0.9);
                            }

                            if (gamepad2.left_trigger>0.8) {
                                thSwingLeft.setTargetPos(0.87);
                                thSwingRight.setTargetPos(0.13);
                            }



                            if (gamepad1.dpad_left) {
                                swingLeft.setPosition(1);

                                //swingRight.resetDeviceConfigurationForOpMode();
                            }
                            if (gamepad1.y) {
                                swingLeft.setPosition(0);
                            }

                            if (gamepad2.dpad_up) {
                                if (lift.getCurrentPosition() <  750) {
                                    lift.setPower(0.3);
                                    liftPosition = lift.getCurrentPosition();
                                }
                            } else if (gamepad2.dpad_down) {
                                lift.setPower(-0.3);
                                liftPosition = lift.getCurrentPosition();
                            } else {
                                double y = lift.getCurrentPosition() - liftPosition;
                                lift.setPower(y / 100);
                            }

                            if (gamepad2.y) {
                                swingRight.setPosition(1);
                            }

                            thSwingLeft.run();
                            thSwingRight.run();


//


                        }
                    }
                }

            }
        }
    }
}

