package utils;

import java.util.Scanner;

public class Tools {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";

    public static void clearConsole() {
        for (int n = 0; n < 20; n++) {
            System.out.println();
        }
    }

    public static void waitForUser(Scanner input) {
        System.out.println("\nPress enter to continue...");
        input.nextLine();
    }

    public static void printToConsole(String text, boolean clear) {
        if (clear) {
            clearConsole();
        }
        System.out.println(text);
    }

    public static void printToConsole(String text) {
        printToConsole(text, false);
    }

    public static void titlePrinter(String title) {
        titlePrinter(title, false);
    }

    public static void titlePrinter(String title, boolean clear) {
        printToConsole("---------- " + title + " ----------", clear);
    }

    public static int validateInt(Scanner input, String message) {
        while (true) {
            System.out.print(message + ": ");
            String userStr = input.nextLine().trim();
            try {
                return Integer.parseInt(userStr);
            } catch (NumberFormatException e) {
                System.out.println(RED + "❌ Invalid input. Please enter a whole number." + RESET);
            }
        }
    }

    public static String validateName(Scanner input, String message) {
        while (true) {
            System.out.print(message + ": ");
            String name = input.nextLine();
            if (name.matches("^[A-Za-zÆØÅæøå\\s]+$")) {
                return name;
            } else {
                Tools.printToConsole("❌ Invalid name – only letters and spaces allowed.");
            }
        }
    }

    public static double validateDouble(Scanner input, String message) {
        while (true) {
            System.out.print(message + ": ");
            String userStr = input.nextLine().trim().replace(',', '.');

            try {
                return Double.parseDouble(userStr);
            } catch (NumberFormatException e) {
                System.out.println(RED + "❌ Invalid number. Please enter a valid decimal value." + RESET);
            }
        }
    }
}