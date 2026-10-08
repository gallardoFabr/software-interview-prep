package oop.project;

/** A bank that stores up to {@value #CAPACITY} accounts in an array. */
public class Bank {

    private static final int CAPACITY = 50;

    private final Account[] accounts = new Account[CAPACITY];
    private int accountCount = 0;                // the bank's OWN counter

    /**
     * Opens a new account.
     *
     * @throws IllegalStateException    if the bank is full
     * @throws IllegalArgumentException if the client or the initial balance is invalid
     */
    public Account openAccount(Client client, double initialBalance) {
        if (this.accountCount == this.accounts.length) {   // check BEFORE creating, so no number is consumed
            throw new IllegalStateException("The bank is full");
        }
        Account account = new Account(client, initialBalance);
        this.accounts[this.accountCount++] = account;
        return account;
    }

    /** return the account, or {null} if it does not exist */
    public Account findByNumber(int number) {
        for (int i = 0; i < this.accountCount; i++) {  // up to accountCount, never accounts.length
            if (this.accounts[i].getNumber() == number) {
                return this.accounts[i];
            }
        }
        return null;
    }

    /**
     * Transfers money between two accounts. If it fails, no balance changes.
     *
     * @return {@code true} if the transfer was made
     */
    public boolean transfer(int origin, int destination, double amount) {
        Account from = findByNumber(origin);
        Account to = findByNumber(destination);
        if (from == null || to == null || from == to || amount <= 0) {
            return false;
        }
        if (!from.withdraw(amount)) {             // insufficient funds
            return false;
        }
        to.deposit(amount);                       // cannot fail: the amount is positive
        return true;
    }

    public double totalBalance() {
        double total = 0;
        for (int i = 0; i < this.accountCount; i++) {
            total += this.accounts[i].getBalance();
        }
        return total;
    }

    public int getAccountCount() {
        return this.accountCount;
    }

    public void printReport() {
        if (this.accountCount == 0) {
            System.out.println("The bank has no accounts yet.");
            return;
        }
        for (int i = 0; i < this.accountCount; i++) {
            System.out.println(this.accounts[i]);
        }
    }
}
