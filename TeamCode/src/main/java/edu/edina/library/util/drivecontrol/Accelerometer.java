package edu.edina.library.util.drivecontrol;

import com.qualcomm.robotcore.util.ElapsedTime;

public class Accelerometer {
    private final BilinearFuncFitter fitter;

    private final ElapsedTime elapsedTime;

    public Accelerometer(int numSamples) {
        fitter = new BilinearFuncFitter(numSamples);
        elapsedTime = new ElapsedTime();
    }

    private double accel;

    public double getAccel() {
        return accel;
    }

    public void sample(double degrees) {
        double t = elapsedTime.seconds();
        fitter.sample(t * t, t, degrees);
        BilinearFunc fit = fitter.fit(false);
        if (fit != null) {
            accel = 2 * fit.beta0;
        }
    }
}
