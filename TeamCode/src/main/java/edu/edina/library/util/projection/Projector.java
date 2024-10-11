package edu.edina.library.util.projection;

import java.util.ArrayList;
import java.util.Comparator;

public class Projector {
    public final double fovDiag, pixWidth, pixHeight;
    public final double aspect, fovX, fovY, pixDepth;

    public Projector(double fovDiag, double pixWidth, double pixHeight) {
        this.fovDiag = fovDiag;
        this.pixWidth = pixWidth;
        this.pixHeight = pixHeight;

        aspect = pixWidth / pixHeight;

        double z = Math.sqrt(1 + aspect * aspect) / (2 * Math.tan(fovDiag / 2));
        pixDepth = z * pixHeight;

        fovY = 2 * Math.atan(1 / (2 * z));
        fovX = 2 * Math.atan(aspect / (2 * z));
    }

    public Vec[] backProjectSquare(Point[] points) {
        ArrayList<Vec[]> cvecs = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Ray a = rayFromPix(points[(i + 3) % 4]);
            Ray b = rayFromPix(points[i % 4]);
            Ray c = rayFromPix(points[(i + 1) % 4]);
            double[] bz = backProjectB(a, b, c);
            cvecs.add(at(b, bz));
        }

        ArrayList<SquareShape> shapes = new ArrayList<>();
        for (Vec c0 : cvecs.get(0))
            for (Vec c1 : cvecs.get(1))
                for (Vec c2 : cvecs.get(2))
                    for (Vec c3 : cvecs.get(3))
                        shapes.add(new SquareShape(new Vec[] { c0, c1, c2, c3 }));

        shapes.sort(Comparator.comparing((SquareShape c) -> c.score()));

        SquareShape bestShape = shapes.get(0);
        SquareShape finalShape = bestShape.snap();

        return finalShape.getCorners();
    }

    private static Vec[] at(Ray r, double[] x) {
        Vec[] v = new Vec[x.length];
        for (int i = 0; i < x.length; i++)
            v[i] = r.at(x[i]);
        return v;
    }

    private Ray rayFromPix(Point p) {
        double ux = (p.x - pixWidth / 2) / pixDepth;
        double uy = (pixHeight / 2 - p.y) / pixDepth;
        return new Ray(ux, uy);
    }

    private static double[] backProjectB(Ray a, Ray b, Ray c) {
        RootFinding.ScalarFunc f = z -> minDotAdjacentPoints(a, b.at(z), c);

        double maxZ = maxZUnit(a, b, c);

        double[] d = new double[2];
        int n = 0;

        try {
            d[n] = RootFinding.newtonShrink(f, 0.01, 0.1, 1e-8);
            n++;
        } catch (RootNotFound e) {
            System.err.printf("no root found (from the left) for a=%s, b=%s, c=%s\n", a, b, c);
        }

        try {
            d[n] = RootFinding.newtonShrink(f, maxZ, 0.1, -1e-8);
            n++;
        } catch (RootNotFound e) {
            System.err.printf("no root found (from the right) for a=%s, b=%s, c=%s\n", a, b, c);
        }

        if (n == 0)
            return null;
        else if (n == 1)
            return new double[] { d[0] };
        else
            return d;
    }

    private static double minDotAdjacentPoints(Ray a, Vec b, Ray c) {
        Vec[] upa = adjacentPoints(a, b);
        Vec[] upc = adjacentPoints(c, b);

        Vec bestA = null, bestC = null;
        double bestDot2 = Double.MAX_VALUE;
        for (Vec va : upa) {
            for (Vec vc : upc) {
                double dot = va.sub(b).dot(vc.sub(b));
                double dot2 = dot * dot;
                if (dot2 < bestDot2) {
                    bestA = va;
                    bestC = vc;
                    bestDot2 = dot2;
                }
            }
        }

        return bestA.sub(b).dot(bestC.sub(b));
    }

    private static Vec[] adjacentPoints(Ray a, Vec b) {
        double x = a.x,
                y = a.y,
                x0 = b.x,
                y0 = b.y,
                z0 = b.z;

        double A = x * x + y * y + 1;
        double B = -2 * (x0 * x + y0 * y + z0);
        double C = x0 * x0 + y0 * y0 + z0 * z0 - 1;

        double[] z = QuadraticRoots.realRoots(A, B, C);
        return new Vec[] {
                a.at(z[0]),
                a.at(z[1])
        };
    }

    private static double maxZUnit(Ray a, Ray b, Ray c) {
        return Math.min(zUnits(a, b), zUnits(c, b));
    }

    private static double zUnits(Ray d, Ray e) {
        Vec dv = d.at(1).normalize();
        Vec ev = e.at(1).normalize();
        double theta = dv.internalAngle(ev);
        double h = 1 / (2 * Math.sin(theta / 2));
        return h * ev.z;
    }
}
