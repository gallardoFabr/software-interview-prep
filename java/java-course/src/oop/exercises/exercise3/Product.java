package oop.exercises.exercise3;

/** A product with a price and a stock that can never become invalid. */
public class Product {

    private static int totalProductsCreated = 0;

    private final String name;
    private final double price;
    private int stock;

    /** @throws IllegalArgumentException if the name is blank, or the price or stock is negative */
    public Product(String name, double price, int stock) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("The name is required");
        }
        if (price < 0) {
            throw new IllegalArgumentException("The price cannot be negative");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("The stock cannot be negative");
        }
        this.name = name;
        this.price = price;
        this.stock = stock;
        totalProductsCreated++;                  // LAST, after validation
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public static int getTotalProductsCreated() {
        return totalProductsCreated;
    }

    /**
     * @return {@code true} if the sale was made, {@code false} if there is not enough stock
     * @throws IllegalArgumentException if the quantity is not positive
     */
    public boolean sell(int quantity) {
        requirePositive(quantity);
        if (quantity > stock) {
            return false;
        }
        stock -= quantity;
        return true;
    }

    /** @throws IllegalArgumentException if the quantity is not positive */
    public void restock(int quantity) {
        requirePositive(quantity);
        stock += quantity;
    }

    public double inventoryValue() {
        return price * stock;
    }

    private static void requirePositive(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("The quantity must be greater than 0");
        }
    }

    @Override
    public String toString() {
        return String.format("Product[name=%s, price=%.2f, stock=%d]", name, price, stock);
    }
}
