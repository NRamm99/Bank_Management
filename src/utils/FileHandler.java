package utils;

import bank.Account;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FileHandler {
    private static final String FILE_PATH = "data/accounts.txt";
    private static final String TRANSACTION_FILE = "data/transactions.txt";

    public static void saveAccounts(ArrayList<Account> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Account acc : accounts) {
                writer.write(acc.getAccountId() + ";" +
                        acc.getHolderName() + ";" +
                        acc.getBalance());
                writer.newLine();
            }
            Tools.printToConsole("💾 Accounts saved successfully!");
        } catch (IOException e) {
            Tools.printToConsole("❌ Error saving accounts: " + e.getMessage());
        }
    }

    public static ArrayList<Account> loadAccounts() {
        ArrayList<Account> loaded = new ArrayList<>();

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            Tools.printToConsole("⚠️ No save file found. Starting fresh.");
            return loaded;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    loaded.add(new Account(id, name, balance));
                }
            }
        } catch (IOException e) {
            Tools.printToConsole("❌ Error loading accounts: " + e.getMessage());
        }
        return loaded;
    }

    public static void saveTransactions(ArrayList<Account> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTION_FILE))) {
            for (Account acc : accounts) {
                writer.write("[" + acc.getAccountId() + "]");
                writer.newLine();
                for (String transaction : acc.getTransactions()) {
                    writer.write(transaction);
                    writer.newLine();
                }
                writer.newLine();
            }
            Tools.printToConsole("🧾 Transactions saved successfully!");
        } catch (IOException e) {
            Tools.printToConsole("❌ Error saving transactions: " + e.getMessage());
        }
    }

    public static HashMap<Integer, ArrayList<String>> loadTransactions() {
        HashMap<Integer, ArrayList<String>> transactionMap = new HashMap<>();

        File file = new File(TRANSACTION_FILE);
        if (!file.exists()) {
            Tools.printToConsole("⚠️ No transaction file found, starting fresh.");
            return transactionMap;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTION_FILE))) {
            String line;
            int currentId = -1;
            ArrayList<String> currentList = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[") && line.endsWith("]")) {
                    if (currentId != -1 && currentList != null)
                        transactionMap.put(currentId, currentList);

                    currentId = Integer.parseInt(line.substring(1, line.length() - 1));
                    currentList = new ArrayList<>();
                } else if (!line.isEmpty() && currentList != null) {
                    currentList.add(line);
                }
            }
            if (currentId != -1 && currentList != null) {
                transactionMap.put(currentId, currentList);
            }
        } catch (IOException e) {
            Tools.printToConsole("❌ Error loading transactions: " + e.getMessage());
        }
        return transactionMap;
    }

}
