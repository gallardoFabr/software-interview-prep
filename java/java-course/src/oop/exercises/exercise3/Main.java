package oop.exercises.exercise3;

public class Main {
    public static void main(String[] args) {
        Product phone = new Product("phone", 150.50, 2);
        System.out.println(phone);
        System.out.println("Total products created: " + Product.getTotalProductsCreated());

        Product tablet = new Product("tablet", 50.70, 4);
        System.out.println(tablet);
        System.out.println("Total products created: " + Product.getTotalProductsCreated());

        System.out.println("Sold 3 phones? " + (phone.sell(3) ? "Yes" : "No (not enough stock)"));
        System.out.println("Sold 1 phone? " + (phone.sell(1) ? "Yes" : "No"));
        phone.restock(10);
        System.out.printf("Inventory value of phones: %.2f%n", phone.inventoryValue());

        try {
            phone.sell(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
        try {
            new Product("", 10, 1);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
        System.out.println("Total products created: " + Product.getTotalProductsCreated()); // still 2
    }
}
