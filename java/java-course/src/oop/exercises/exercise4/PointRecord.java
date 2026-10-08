package oop.exercises.exercise4;

/** The same point as a record: constructor, accessors, equals, hashCode and toString are generated. */
public record PointRecord(double x, double y) {

    /** @throws IllegalArgumentException if the other point is null */
    public double distanceTo(PointRecord other) {
        if (other == null) {
            throw new IllegalArgumentException("The other point cannot be null");
        }
        return Math.hypot(x - other.x, y - other.y);
    }
}
