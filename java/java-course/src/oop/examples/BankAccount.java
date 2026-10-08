package oop.examples;

/**
 * A simple bank account used to illustrate encapsulation, constructor
 * chaining, validation, {@code static} and {@code final}.
 */
public class BankAccount {

    private static int totalAccounts = 0;     // shared by all accounts

    private final String owner;               // never changes after creation
    private double balance;

    /** Creates an account with a zero balance. */
    public BankAccount(String owner) {
        this(owner, 0);                       // constructor chaining
    }

    /**
     * Creates an account with an initial balance.
     *
     * @throws IllegalArgumentException if the owner is blank or the balance is negative
     */
    public BankAccount(String owner, double initialBalance) {
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("The owner is required");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("The initial balance cannot be negative");
        }
        this.owner = owner;
        this.balance = initialBalance;
        totalAccounts++;                      // incremented LAST, after validation
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public static int getTotalAccounts() {
        return totalAccounts;
    }

    /** @throws IllegalArgumentException if the amount is not positive */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
        balance += amount;
    }

    /**
     * @return {@code true} if the withdrawal succeeded, {@code false} if there are insufficient funds
     * @throws IllegalArgumentException if the amount is not positive
     */
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
        if (amount > balance) {
            return false;                     // expected outcome, not a bug
        }
        balance -= amount;
        return true;
    }

    @Override
    public String toString() {
        return String.format("BankAccount[%s, balance=%.2f]", owner, balance);
    }
}
