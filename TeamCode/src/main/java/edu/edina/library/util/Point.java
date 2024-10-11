package edu.edina.library.util;

import androidx.annotation.NonNull;

public final class Point {
    public final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("x=%.1f, y=%.1f",x,y);
    }
}
