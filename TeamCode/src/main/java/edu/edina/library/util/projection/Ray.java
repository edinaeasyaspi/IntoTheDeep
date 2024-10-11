package edu.edina.library.util.projection;

public class Ray {
    public final double x, y;

    public Ray(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec at(double z) {
        return new Vec(x * z, y * z, z);
    }

    @Override
    public String toString() {
        return String.format("Ray:%.4f,%.4f", x, y);
    }
}
