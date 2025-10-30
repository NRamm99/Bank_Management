package bank;

import utils.FileHandler;
import utils.Tools;

import java.util.ArrayList;

public class Bank {
    private ArrayList<Account> accounts;
    private int nextAccountId = 1001;

    public Bank() {
        accounts = FileHandler.loadAccounts();

        var transactionMap = FileHandler.loadTransactions();
        for (Account acc : accounts) {
            ArrayList<String> history = transactionMap.get(acc.getAccountId());
            if (history != null) {
                acc.setTransactions(history);
            }
        }
    }

    public void saveData() {
        FileHandler.saveAccounts(accounts);
        FileHandler.saveTransactions(accounts);
    }

    public void createAccount(String name, double initialDeposit) {
        if (initialDeposit < 0) {
            Tools.printToConsole("❌ Initial deposit cannot be negative.");
            return;
        }

        Account newAccount = new Account(nextAccountId++, name, initialDeposit);
        accounts.add(newAccount);
        Tools.printToConsole("🏦 Account created successfully! ID: " + (nextAccountId - 1));
        saveData();
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void displayAllAccounts() {
        if (accounts.isEmpty()) {
            Tools.printToConsole("⚠️  No accounts found.");
            return;
        }

        Tools.printToConsole("==================== ACCOUNTS ====================");
        for (Account acc : accounts) {
            System.out.println(acc);
        }
        Tools.printToConsole("=================================================");
    }

    public Account findAccountById(int id) {
        for (Account acc : accounts) {
            if (acc.getAccountId() == id) return acc;
        }
        return null;
    }

    public void deposit(int id, double amount) {
        Account acc = findAccountById(id);
        if (acc != null) acc.deposit(amount);
        else Tools.printToConsole("❌ Account not found.");
    }

    public void withdraw(int id, double amount) {
        Account acc = findAccountById(id);
        if (acc != null) acc.withdraw(amount);
        else Tools.printToConsole("❌ Account not found.");
    }


    public void transfer(int fromId, int toId, double amount) {
        Account fromAcc = findAccountById(fromId);
        Account toAcc = findAccountById(toId);

        if (fromAcc == null || toAcc == null) {
            System.out.println("❌ One or both accounts not found.");
            return;
        }

        if (amount <= 0) {
            System.out.println("❌ Invalid transfer amount.");
            return;
        }

        if (fromAcc.getBalance() < amount) {
            System.out.println("❌ Insufficient funds in sender’s account.");
            return;
        }

        fromAcc.withdraw(amount);
        toAcc.deposit(amount);

        fromAcc.addTransaction(String.format("Transferred $%.2f to %s (ID %d)", amount, toAcc.getHolderName(), toAcc.getAccountId()));
        toAcc.addTransaction(String.format("Received $%.2f from %s (ID %d)", amount, fromAcc.getHolderName(), fromAcc.getAccountId()));

        System.out.printf("💸 Transfer successful! $%.2f sent from %s → %s%n", amount, fromAcc.getHolderName(), toAcc.getHolderName());
    }

    public void applyMonthlyInterest(double ratePercent) {
        for (Account account : accounts) {
            double interest = account.getBalance() * (ratePercent / 100);
            account.deposit(interest);
            account.addTransaction(String.format("Interest of %.2f%% applied (+$%.2f)", ratePercent, interest));
        }
        Tools.printToConsole("🏦 Interest applied to all accounts!");
    }
}
