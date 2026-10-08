package oop.exercises.exercise4;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Traditional class ===");
        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 2);
        Point p3 = new Point(4, 6);

        System.out.println("p1.equals(p2) -> " + p1.equals(p2));        // true
        System.out.println("p1 == p2      -> " + (p1 == p2));           // false
        System.out.println("Same hash code? " + (p1.hashCode() == p2.hashCode()));
        System.out.println("Distance p1 to p3: " + p1.distanceTo(p3));  // 5.0
        System.out.println("toString: " + p1);

        System.out.println("\n=== Record ===");
        PointRecord r1 = new PointRecord(1, 2);
        PointRecord r2 = new PointRecord(1, 2);
        PointRecord r3 = new PointRecord(4, 6);

        System.out.println("r1.equals(r2) -> " + r1.equals(r2));        // true
        System.out.println("r1 == r2      -> " + (r1 == r2));           // false
        System.out.println("Distance r1 to r3: " + r1.distanceTo(r3));  // 5.0
        System.out.println("toString: " + r1);
    }
}
