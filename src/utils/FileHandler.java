package utils;

import bank.Account;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {
    private static final String FILE_PATH = "data/accounts.txt";

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
            while ((line = reader.readLine()) != null){
                String[] parts = line.split(";");
                if (parts.length ==3){
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
}
