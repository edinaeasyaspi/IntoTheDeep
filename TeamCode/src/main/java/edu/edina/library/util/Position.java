package edu.edina.library.util;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

@SuppressLint("DefaultLocale")
public final class Position {
    public final double x, y, a;

    public Position(double x, double y, double a) {
        this.x = x;
        this.y = y;
        this.a = a;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("x=%.2f, y=%.2f heading=%.2f", x, y, a);
    }

    public double dist(Position p) {
        double x = this.x - p.x;
        double y = this.y - p.y;
        return Math.sqrt(x * x + y * y);
    }

    public Point toRobotRel(Point p) {
        double dx = getDx();
        double dy = getDy();
        double tx = p.x - this.x;
        double ty = p.y - this.y;
        return new Point(
                (tx * dy) - (ty * dx),
                (tx * dx) + (ty * dy));
    }

    public Position addRobotRel(Point p) {
        double dx = getDx();
        double dy = getDy();
        double tx = dy * p.x + dx * p.y;
        double ty = -dx * p.x + dy * p.y;
        return new Position(x + tx, y + ty, a);
    }

    public double getDx() {
        return -Math.sin(Math.toRadians(a));
    }

    public double getDy() {
        return Math.cos(Math.toRadians(a));
    }
}
