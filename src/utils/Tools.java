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
                int value = Integer.parseInt(userStr);

                if (value < 0) {
                    System.out.println(RED + "❌ Number cannot be negative. Try again." + RESET);
                    continue;
                }

                return value;
            } catch (NumberFormatException e) {
                System.out.println(RED + "❌ Invalid input. Please enter a whole number." + RESET);
            }
        }
    }

    public static double validateDouble(Scanner input, String message) {
        while (true) {
            System.out.print(message + ": ");
            String userStr = input.nextLine().trim().replace(',', '.');

            try {
                double value = Double.parseDouble(userStr);

                if (value < 0) {
                    System.out.println(RED + "❌ Number cannot be negative. Try again." + RESET);
                    continue;
                }

                return value;
            } catch (NumberFormatException e) {
                System.out.println(RED + "❌ Invalid number. Please enter a valid decimal value." + RESET);
            }
        }
    }
}