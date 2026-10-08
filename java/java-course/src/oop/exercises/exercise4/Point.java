package oop.exercises.exercise4;

import java.util.Objects;

/** The traditional way to write an immutable class: final class, final fields, no setters. */
public final class Point {

    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /** @throws IllegalArgumentException if the other point is null */
    public double distanceTo(Point other) {
        if (other == null) {
            throw new IllegalArgumentException("The other point cannot be null");
        }
        return Math.hypot(x - other.x, y - other.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point other)) return false;
        return Double.compare(x, other.x) == 0
                && Double.compare(y, other.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("Point{x=%.2f, y=%.2f}", x, y);
    }
}
