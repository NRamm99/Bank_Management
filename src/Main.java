import bank.Account;
import bank.Bank;
import utils.Tools;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private final static Scanner input = new Scanner(System.in);
    private final static Bank bank = new Bank();

    public static void main(String[] args) {
        while (true) {
            Tools.printToConsole("""
                    🏦 BANK MENU
                    1... Create account
                    2... Deposit
                    3... Withdraw
                    4... View all accounts
                    5... Transfer between accounts
                    6... View transaction history
                    7... Apply monthly interest
                    0... Exit
                    """, true);

            int choice = Tools.validateInt(input, "Enter choice");

            switch (choice) {
                case 1 -> promptCreateAccount();
                case 2 -> promptDeposit();
                case 3 -> promptWithdraw();
                case 4 -> {
                    bank.displayAllAccounts();
                    Tools.waitForUser(input);
                }
                case 5 -> promptTransfer();
                case 6 -> promptViewTransactions();
                case 7 -> promptInterest();
                case 0 -> {
                    Tools.printToConsole("👋 Goodbye!");
                    return;
                }
                default -> Tools.printToConsole("❌ Invalid choice.");
            }
        }
    }

    private static void promptInterest() {
        double ratePercent = Tools.validateDouble(input, "Enter monthly interest rate (%)");
        bank.applyMonthlyInterest(ratePercent);
    }

    private static void promptTransfer() {
        int fromId = Tools.validateInt(input, "Enter sender account ID");
        int toId = Tools.validateInt(input, "Enter reciever ID");
        double amount = Tools.validateDouble(input, "Enter amount to transfer");
        bank.transfer(fromId, toId, amount);
        Tools.waitForUser(input);

    }

    private static void promptViewTransactions() {
        int id = Tools.validateInt(input, "Enter account ID");
        Account acc = bank.findAccountById(id);
        if (acc != null) acc.showTransactions();
        else Tools.printToConsole("❌ Account not found.");
        Tools.waitForUser(input);
    }

    private static void promptCreateAccount() {
        Tools.clearConsole();
        String name = Tools.validateName(input, "Enter account holder name");
        double initialDeposit = Tools.validateDouble(input, "Enter initial deposit");
        bank.createAccount(name, initialDeposit);
        Tools.waitForUser(input);
    }

    private static void promptDeposit() {
        int id = Tools.validateInt(input, "Enter account ID");
        double amount = Tools.validateDouble(input, "Enter amount to deposit");
        bank.deposit(id, amount);
        Tools.waitForUser(input);
    }

    private static void promptWithdraw() {
        int id = Tools.validateInt(input, "Enter account ID");
        double amount = Tools.validateDouble(input, "Enter amount to withdraw");
        bank.withdraw(id, amount);
        Tools.waitForUser(input);
    }
}