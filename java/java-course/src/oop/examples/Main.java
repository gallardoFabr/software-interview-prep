package oop.examples;

public class Main {
    public static void main(String[] args) {
        BankAccount a = new BankAccount("Ana", 500);
        BankAccount b = new BankAccount("Luis");

        a.deposit(200);
        boolean ok = a.withdraw(1000);

        System.out.println(a);                                          // BankAccount[Ana, balance=700.00]
        System.out.println("Withdrawal succeeded: " + ok);              // false
        System.out.println(b);
        System.out.println("Total: " + BankAccount.getTotalAccounts()); // 2

        // a.balance = 999999;   // compile error: balance is private
    }
}
