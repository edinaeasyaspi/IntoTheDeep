package edu.edina.library.util.drivecontrol;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.VoltageSensor;

@SuppressLint("DefaultLocale")
public class PiMotor {
    private final DcMotor motor;
    private final VoltageSensor vs;
    private final MotorConfig motorConf;
    private final double accelTorqueFrac;
    private final Speedometer speedo;
    private double initPos;
    private double torqueFrac;
    private static final double coastToStopTol = 0;
    private boolean driving;
    private double torqueLimit;

    public PiMotor(DcMotor motor, MotorConfig motorConf, VoltageSensor vs, double accelTf) {
        this.motor = motor;
        this.vs = vs;
        this.motorConf = motorConf;
        this.accelTorqueFrac = accelTf;
        speedo = new Speedometer(8);
    }

    public double getDeg() {
        return motorConf.toDeg(motor.getCurrentPosition() - initPos);
    }

    public double getDegSpeed() {
        return speedo.getSpeed();
    }

    public void setDriving(boolean d, double torqueLimit) {
        driving = d;
        this.torqueLimit = torqueLimit;
    }

    public void resetDeg() {
        speedo.clearSamples();
        initPos = motor.getCurrentPosition();
    }

    public void sample() {
        speedo.sample(getDeg());
    }

    public void run(double currBotSpeed, double move, double dir, double correction) {
        if (move != 0) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT); // is this needed

            if (!driving) {
                if (currBotSpeed < coastToStopTol) {
                    torqueFrac = 0;
                    motor.setPower(0);
                    return;
                } else {
                    torqueFrac = -accelTorqueFrac;
                }
            } else {
                torqueFrac = accelTorqueFrac;
            }

            torqueFrac *= move * dir * torqueLimit;
            currBotSpeed *= move;

            // limit speedFrac to +/- 1
            double volt = (torqueFrac + currBotSpeed / motorConf.topSpeed + correction) * motorConf.nominalVolt;
            motor.setPower(volt / vs.getVoltage());
        } else {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // is this needed
            motor.setPower(0);
        }
    }

    public void shutdown() {
        motor.setPower(0);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("spd=%.2f, trq=%.2f, pwr=%.2f",
                getDegSpeed(), torqueFrac, motor.getPower());
    }
}
