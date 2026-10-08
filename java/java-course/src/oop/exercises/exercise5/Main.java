package oop.exercises.exercise5;

public class Main {
    public static void main(String[] args) {
        Book book = new Book("Clean Code", 464);   // bug 2: this line did not compile before
        System.out.println(book);                  // bug 3: the title was null before

        book.setPages(500);
        System.out.println("Updated: " + book);

        try {
            book.setPages(-10);                    // bug 4: this used to be accepted
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
    }
}
