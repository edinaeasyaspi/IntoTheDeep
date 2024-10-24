package org.firstinspires.ftc.teamcode;


import com.acmerobotics.roadrunner.ftc.LynxFirmware;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotHardware2 {
    //public final DistanceSensor distanceSensor;
    public final DcMotor lfd, lbd, rfd, rbd;

    public final VoltageSensor voltageSensor;

 //   public final ModernRoboticsI2cGyro gyro;

    public final IMU imu;

    public final Servo clawLeft, clawRight;


    //public final CRServo leftIntakeServo, rightIntakeServo;

    public final DcMotorEx liftMotor;
//    public final DistanceSensor distanceSensor;

 //   public final WebcamName webcam;

 //   public final int cameraMonitorViewId;
    public final Telemetry telemetry;
 //   public WebcamName LogitechC270_8034PI;

    public RobotHardware2(HardwareMap hardwareMap) {
        this(hardwareMap, null);
    }

    public  RobotHardware2(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        LynxFirmware.throwIfModulesAreOutdated(hardwareMap);

        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        lfd = hardwareMap.get(DcMotor.class, "lfd");
        rfd = hardwareMap.get(DcMotor.class, "rfd");
        lbd = hardwareMap.get(DcMotor.class, "lbd");
        rbd = hardwareMap.get(DcMotor.class, "rbd");


        //HangingMotor = hardwareMap.get(DcMotorEx.class, "hangingMotor");

        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);

//        gyro = hardwareMap.get(ModernRoboticsI2cGyro.class, "mrGyro");
//        gyro.calibrate();

        voltageSensor = hardwareMap.voltageSensor.iterator().next();


//        leftLiftServo = hardwareMap.get(Servo.class, "S1");
//        rightLiftServo = hardwareMap.get(Servo.class, "S2");
//        //droneLauncherServo = hardwareMap.get(Servo.class, "droneLauncherServo");
//
//        leftIntakeServo = hardwareMap.get(CRServo.class, "F2");
//        rightIntakeServo = hardwareMap.get(CRServo.class, "F1");

        liftMotor = hardwareMap.get(DcMotorEx.class, "liftMotor");
        clawLeft = hardwareMap.get(Servo.class, "clawLeft");
        clawRight = hardwareMap.get(Servo.class, "clawRight");
 //       distanceSensor = hardwareMap.get(DistanceSensor.class, "distanceSensor");




        //distanceSensor = hardwareMap.get(DistanceSensor.class, "distance_sensor");

     //   webcam = hardwareMap.get(WebcamName.class, "LogitechC270_8034PI");
    //    cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());



    }
}
