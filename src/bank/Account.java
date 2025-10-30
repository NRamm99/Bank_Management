package bank;

import utils.Tools;

import java.util.ArrayList;

public class Account {
    private final int accountId;
    private String holderName;
    private double balance;
    private ArrayList<String> transactions = new ArrayList<>();

    public Account(int id, String name, double initialDeposit) {
        this.accountId = id;
        this.holderName = name;
        this.balance = initialDeposit;

        transactions.add(String.format("Account opened with $%.2f", initialDeposit));

    }

    public int getAccountId() {
        return accountId;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setHolderName(String newName) {
        holderName = newName;
    }

    public boolean rename(String newName) {
        if (newName == null || newName.trim().isEmpty()) return false;
        holderName = newName.trim();
        return true;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.printf("💰 Deposited $%.2f into %s %n", amount, holderName);
            transactions.add(String.format("Deposited $%.2f (new balance: $%.2f)", amount, balance));
        } else System.out.println("❌ Deposit amount must be positive.");
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Invalid amount.");
        } else if (amount > balance) {
            System.out.println("❌ Insufficient funds.");
        } else {
            balance -= amount;
            System.out.printf("💸 Withdrew $%.2f from %s %n", amount, holderName);
            transactions.add(String.format("Withdrew $%.2f (new balance: $%.2f)", amount, balance));
        }
    }

    public void showTransactions() {
        Tools.clearConsole();
        Tools.printToConsole("📜 Transaction History for " + holderName + ":");
        for (String transaction : transactions) {
            Tools.printToConsole(" • " + transaction);
        }
        Tools.printToConsole("");
    }

    public void addTransaction(String entry) {
        transactions.add(entry);
    }

    public ArrayList<String> getTransactions(){
        return transactions;
    }

    public void setTransactions(ArrayList<String> transactions){
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return String.format("ID: %-5d | Name: %-20s | Balance: $%.2f",
                accountId, holderName, balance);
    }
}
