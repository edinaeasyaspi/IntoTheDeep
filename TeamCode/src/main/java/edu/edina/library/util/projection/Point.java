package edu.edina.library.util.projection;

public class Point {
    public final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(String s) {
        String[] a = s.split(",");
        x = Double.parseDouble(a[0]);
        y = Double.parseDouble(a[1]);
    }

    @Override
    public String toString() {
        return String.format("%.1f,%.1f", x, y);
    }
}
