import java.io.*;
import java.util.*;

class Account implements Serializable {
    private String accountNumber;
    private String name;
    private double balance;

    public Account(String accountNumber, String name, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getName() { return name; }
    public double getBalance() { return balance; }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) throws Exception {
        if (balance >= amount) balance -= amount;
        else throw new Exception("Insufficient funds!");
    }

    @Override
    public String toString() {
        return "Account No: " + accountNumber + " | Name: " + name + " | Balance: " + balance;
    }
}

public class BankApp {
    static final String FILE_NAME = "accounts.dat";
    static List<Account> accounts = new ArrayList<>();

    @SuppressWarnings("unchecked")
    static void loadAccounts() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            accounts = (ArrayList<Account>) in.readObject();
        } catch (Exception e) {
            accounts = new ArrayList<>();
        }
    }

    static void saveAccounts() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(accounts);
        } catch (Exception e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }
    }

    static Account findAccount(String accNo) {
        for (Account a : accounts) {
            if (a.getAccountNumber().equals(accNo)) return a;
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadAccounts();

        while (true) {
            System.out.println("\n====== Bank Menu ======");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Display All Accounts");
            System.out.println("5. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Account No: ");
                        String accNo = sc.nextLine();
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Initial Deposit: ");
                        double bal = sc.nextDouble();
                        accounts.add(new Account(accNo, name, bal));
                        saveAccounts();
                        System.out.println("✅ Account Created!");
                        break;

                    case 2:
                        System.out.print("Enter Account No: ");
                        accNo = sc.nextLine();
                        Account acc = findAccount(accNo);
                        if (acc == null) System.out.println("❌ Account Not Found!");
                        else {
                            System.out.print("Enter Amount: ");
                            acc.deposit(sc.nextDouble());
                            saveAccounts();
                            System.out.println("✅ Deposit Successful!");
                        }
                        break;

                    case 3:
                        System.out.print("Enter Account No: ");
                        accNo = sc.nextLine();
                        acc = findAccount(accNo);
                        if (acc == null) System.out.println("❌ Account Not Found!");
                        else {
                            System.out.print("Enter Amount: ");
                            acc.withdraw(sc.nextDouble());
                            saveAccounts();
                            System.out.println("✅ Withdrawal Successful!");
                        }
                        break;

                    case 4:
                        accounts.forEach(System.out::println);
                        break;

                    case 5:
                        saveAccounts();
                        System.out.println("💾 Data Saved. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }
        }
    }
}
