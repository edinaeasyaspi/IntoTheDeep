package edu.edina.library.util.projection;

public class RootFinding {
    public interface ScalarFunc {
        double Eval(double x);
    }

    private static final double XTOL = 1e-6;
    private static final int niter = 200;

    public static double newtonShrink(ScalarFunc f, double x0, double a, double dx) throws RootNotFound {
        for (int i = 0; i < niter; i++) {
            double f0 = f.Eval(x0);
            double df0 = (f.Eval(x0 + dx) - f0) / dx;
            double x1 = x0 - a * f0 / df0;
            if (Math.abs(x0 - x1) < XTOL)
                return x1;
            else
                x0 = x1;
        }

        throw new RootNotFound();
    }
}