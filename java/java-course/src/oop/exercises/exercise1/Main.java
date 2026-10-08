package oop.exercises.exercise1;

public class Main {
    public static void main(String[] args) {
        Rectangle a = new Rectangle(2, 3);
        Rectangle b = new Rectangle(5, 5);

        for (Rectangle r : new Rectangle[]{a, b}) {
            System.out.println(r);
            System.out.println("Area: " + r.area());
            System.out.println("Perimeter: " + r.perimeter());
            System.out.println("Is a square? " + (r.isSquare() ? "Yes" : "No"));
            System.out.println();
        }

        try {
            new Rectangle(0, 4);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
    }
}
