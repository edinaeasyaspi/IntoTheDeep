package edu.edina.library.util.projection;

public class Vec {
    public final double x, y, z;

    public Vec() {
        this(0, 0, 0);
    }

    public Vec(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec add(Vec v) {
        return new Vec(x + v.x, y + v.y, z + v.z);
    }

    public Vec sub(Vec v) {
        return new Vec(x - v.x, y - v.y, z - v.z);
    }

    public Vec mul(double scale) {
        return new Vec(x * scale, y * scale, z * scale);
    }

    public Vec div(double scale) {
        return new Vec(x / scale, y / scale, z / scale);
    }

    public double norm() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vec normalize() {
        return div(norm());
    }

    public double dot(Vec v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public Vec cross(Vec v) {
        return new Vec(
                y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x);
    }

    public double dist(Vec v) {
        return sub(v).norm();
    }

    public double internalAngle(Vec v) {
        return Math.acos(normalize().dot(v.normalize()));
    }

    @Override
    public String toString() {
        return String.format("%.4f, %.4f, %.4f", x, y, z);
    }

    public String toString(boolean fullPrec) {
        if (fullPrec)
            return String.format("%.16f,%.16f,%.16f", x, y, z);
        else
            return toString();
    }
}
