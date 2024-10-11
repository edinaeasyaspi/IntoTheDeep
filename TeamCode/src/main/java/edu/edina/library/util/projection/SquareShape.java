package edu.edina.library.util.projection;

import java.util.Arrays;
import java.util.Comparator;

public class SquareShape {
    private final Vec[] corners;

    public SquareShape(Vec[] corners) {
        if (corners.length != 4)
            throw new RuntimeException("expected 4 corners");

        this.corners = sortCorners(corners);

        for (Vec v : corners)
            System.err.printf("    %s\n", v);
        System.err.printf("score = %f\n", score());
    }

    public double score() {
        Vec a = corners[0], b = corners[1], c = corners[2], d = corners[3];
        Vec p = b.add(d).sub(a);

        double diag = Math.sqrt(2);
        return distErr(p, c, 0) +
                distErr(a, b, 1) +
                distErr(b, c, 1) +
                distErr(c, d, 1) +
                distErr(d, a, 1) +
                distErr(a, c, diag) +
                distErr(b, d, diag);
    }

    public Vec[] getCorners() {
        return corners.clone();
    }

    public SquareShape snap() {
        Vec ctr = center();
        Vec[] corners = new Vec[4];
        for (int i = 0; i < 4; i++)
            corners[i] = this.corners[i].sub(ctr);

        for (int step = 0; step < 5; step++) {
            Vec[] next = new Vec[] { new Vec(), new Vec(), new Vec(), new Vec() };

            for (int i0 = 0; i0 < 4; i0++) {
                int i1 = (i0 + 1) % 4;
                int i2 = (i0 + 2) % 4;
                int i3 = (i0 + 3) % 4;

                Vec a = corners[i0];
                Vec b = corners[i1];
                Vec c = corners[i2];
                Vec d = corners[i3];

                Vec u = a.normalize();
                Vec v = d.sub(c).cross(b.sub(c)).cross(a).normalize();

                next[i0] = next[i0].add(u);
                next[i1] = next[i1].add(v);
                next[i2] = next[i2].sub(u);
                next[i3] = next[i3].sub(v);
            }

            for (int i = 0; i < 4; i++)
                corners[i] = next[i].mul(Math.sqrt(0.5) / 4);
        }

        for (int i = 0; i < 4; i++)
            corners[i] = corners[i].add(ctr);

        return new SquareShape(corners);
    }

    private Vec center() {
        double x = 0, y = 0, z = 0;
        for (int i = 0; i < 4; i++) {
            x += corners[i].x;
            y += corners[i].y;
            z += corners[i].z;
        }

        return new Vec(x / 4, y / 4, z / 4);
    }

    private static double distErr(Vec a, Vec b, double d) {
        return Math.abs(a.dist(b) - d);
    }

    private static Vec[] sortCorners(Vec[] corners) {
        corners = corners.clone();

        Arrays.sort(corners,
                Comparator.comparing((Vec c) -> c.x));

        Vec c0 = corners[0];
        Arrays.sort(corners, 1, 4,
                Comparator.comparing((Vec c) -> Math.atan2(c.z - c0.z, c.x - c0.x)));

        return corners;
    }
}
