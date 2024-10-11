package edu.edina.library.util;

//import com.acmerobotics.roadrunner.ftc.LynxFirmware;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class RobotHardware {
    //public final DistanceSensor distanceSensor;
    public final DcMotorEx frontLeftMotor, frontRightMotor, backRightMotor, backLeftMotor;

    public final VoltageSensor voltageSensor;

    public final ModernRoboticsI2cGyro gyro;

    public final IMU imu;

    public final Servo hangRight, hangLeft, hangLiftLeft, hangLiftRight, intakeLeft, intakeRight, intakeSwingLeft, intakeSwingRight;


    //public final CRServo leftIntakeServo, rightIntakeServo;

    public final DcMotorEx liftMotor;
    public final CRServo droneLauncher;

    public final WebcamName webcam;

    public final int cameraMonitorViewId;
    public final Telemetry telemetry;
    public WebcamName LogitechC270_8034PI;

    public RobotHardware(HardwareMap hardwareMap) {
        this(hardwareMap, null);
    }

    public RobotHardware(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        //LynxFirmware.throwIfModulesAreOutdated(hardwareMap);

        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "frontLeftMotor");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "backLeftMotor");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "backRightMotor");
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "frontRightMotor");
        //HangingMotor = hardwareMap.get(DcMotorEx.class, "hangingMotor");

        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);

        gyro = hardwareMap.get(ModernRoboticsI2cGyro.class, "mrGyro");
        gyro.calibrate();

        voltageSensor = hardwareMap.voltageSensor.iterator().next();


//        leftLiftServo = hardwareMap.get(Servo.class, "S1");
//        rightLiftServo = hardwareMap.get(Servo.class, "S2");
//        //droneLauncherServo = hardwareMap.get(Servo.class, "droneLauncherServo");
//
//        leftIntakeServo = hardwareMap.get(CRServo.class, "F2");
//        rightIntakeServo = hardwareMap.get(CRServo.class, "F1");

        liftMotor = hardwareMap.get(DcMotorEx.class, "liftMotor");
        hangLeft = hardwareMap.get(Servo.class, "hangLeft");
        hangRight = hardwareMap.get(Servo.class, "hangRight");
        hangLiftLeft = hardwareMap.get(Servo.class, "hangLiftLeft");
        hangLiftRight = hardwareMap.get(Servo.class, "hangLiftRight");
        intakeLeft = hardwareMap.get(Servo.class, "intakeLeft");
        intakeRight = hardwareMap.get(Servo.class, "intakeRight");
        intakeSwingRight = hardwareMap.get(Servo.class, "intakeSwingRight");
        intakeSwingLeft = hardwareMap.get(Servo.class, "intakeSwingLeft");

        droneLauncher = hardwareMap.get(CRServo.class, "droneLauncher");

        //distanceSensor = hardwareMap.get(DistanceSensor.class, "distance_sensor");

        webcam = hardwareMap.get(WebcamName.class, "LogitechC270_8034PI");
        cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

    }
}
