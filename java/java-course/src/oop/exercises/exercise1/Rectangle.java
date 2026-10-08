package oop.exercises.exercise1;

/** An immutable rectangle whose sides are always greater than 0. */
public final class Rectangle {

    private final double width;
    private final double height;

    /** @throws IllegalArgumentException if any side is not greater than 0 */
    public Rectangle(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("The width and the height must be greater than 0");
        }
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double area() {
        return width * height;
    }

    public double perimeter() {
        return 2 * (width + height);
    }

    /** Answers the question; printing the answer is the caller's job. */
    public boolean isSquare() {
        return Double.compare(width, height) == 0;
    }

    @Override
    public String toString() {
        return String.format("Rectangle[width=%.2f, height=%.2f]", width, height);
    }
}
