package oop.exercises.exercise2;

public class Main {
    public static void main(String[] args) {
        Student student1 = new Student("101", "Ana Gomez");
        student1.registerGrade(0, 11.0);
        student1.registerGrade(1, 14.5);
        student1.registerGrade(2, 9.2);
        student1.registerGrade(3, 12.0);
        print(student1);

        System.out.println("----------------------------------");

        Student student2 = new Student("102", "Carlos Perez", new double[]{8.0, 10.0, 11.0, 9.5});
        print(student2);

        System.out.println("----------------------------------");

        try {
            student1.registerGrade(0, 25);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected grade: " + e.getMessage());
        }
        try {
            student1.registerGrade(7, 10);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected index: " + e.getMessage());
        }
        try {
            new Student("103", "Luis Rojas", null);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected array: " + e.getMessage());
        }
    }

    private static void print(Student s) {
        System.out.println("Student: " + s.getName());
        System.out.printf("Average: %.2f%n", s.average());
        System.out.println("Approved? " + (s.isApproved() ? "Yes" : "No"));
        System.out.println("Best grade: " + s.bestGrade());
    }
}
