package oop.project;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        Client ana = new Client("77777777", "Ana Gomez", "ana@email.com");
        Client carlos = new Client("88888888", "Carlos Perez", "carlos@email.pe");
        Client maria = new Client("01234567", "Maria Lopez", "maria@email.com");  // leading zero is fine

        Account a1 = bank.openAccount(ana, 1000.00);
        Account a2 = bank.openAccount(carlos, 500.00);
        Account a3 = bank.openAccount(maria, 50.00);

        System.out.println("=== Initial report ===");
        bank.printReport();

        System.out.println("\n=== Direct deposit ===");
        a3.deposit(25);
        System.out.println(a3);

        System.out.println("\n=== Successful transfer: Ana -> Carlos (200) ===");
        System.out.println("Result: " + bank.transfer(a1.getNumber(), a2.getNumber(), 200));
        bank.printReport();

        System.out.println("\n=== Failed transfer: Maria -> Carlos (200, insufficient funds) ===");
        System.out.println("Result: " + bank.transfer(a3.getNumber(), a2.getNumber(), 200));
        bank.printReport();                       // balances unchanged

        System.out.println("\n=== Unknown account ===");
        System.out.println("findByNumber(999): " + bank.findByNumber(999));
        System.out.println("Transfer to 999: " + bank.transfer(a1.getNumber(), 999, 10));

        System.out.println("\n=== Invalid data is rejected ===");
        try {
            new Client("123", "Bad Client", "bad.email");
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected client: " + e.getMessage());
        }
        try {
            bank.openAccount(ana, -5);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected account: " + e.getMessage());
        }

        System.out.printf("%nTotal balance: %.2f%n", bank.totalBalance());
    }
}
