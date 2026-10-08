package oop.project;

/** A bank account. It never prints: it throws for invalid input and returns a result otherwise. */
public class Account {

    private static int accountsCreated = 0;      // only used to generate unique numbers

    private final int number;
    private final Client client;
    private double balance;

    /** @throws IllegalArgumentException if the client is null or the initial balance is negative */
    public Account(Client client, double initialBalance) {
        if (client == null) {
            throw new IllegalArgumentException("The client is required");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("The initial balance cannot be negative");
        }
        this.client = client;
        this.balance = initialBalance;
        this.number = ++accountsCreated;         // LAST, after validation
    }

    public int getNumber() {
        return number;
    }

    public Client getClient() {
        return client;
    }

    public double getBalance() {
        return balance;
    }

    /** @throws IllegalArgumentException if the amount is not positive */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("The amount must be greater than 0");
        }
        balance += amount;
    }

    /**
     * @return {@code true} if the withdrawal succeeded, {@code false} if there are insufficient funds
     * @throws IllegalArgumentException if the amount is not positive
     */
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("The amount must be greater than 0");
        }
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        return true;
    }

    @Override
    public String toString() {
        return String.format("Account #%d | %s | Balance: %.2f", number, client, balance);
    }
}
