import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public final class Utils {
    private Utils() {
    }

    public static int scanInt(String prompt) {
        int num;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(prompt);
            try {
                num = scanner.nextInt();
                break;
            } catch (InputMismatchException ignored) {
                System.out.println();
                System.out.println("Invalid input.");
                scanner.nextLine();
            }
        }
        return num;
    }

    public static int scanBoundedInt(int min, int max, String prompt) {
        int num;
        while (true) {
            num = scanInt(prompt);
            if (min <= num && num <= max) {
                return num;
            } else {
                System.out.printf("%nNumber must be between %d and %d.%n", min, max);
            }
            //TODO   More readable with or without else?
        }
    }

    public static void waitForUser() {
        System.out.println("Press enter to return");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }


    public static String scanBoundedString(String prompt, int limit, boolean shouldAllowBlanks) {
        String string;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(prompt);
            string = scanner.nextLine().trim();
            boolean passesBlankCheck = !string.isBlank() || shouldAllowBlanks;
            boolean lengthIsInLimit = string.length() <= limit;
            if (passesBlankCheck && lengthIsInLimit) {
                return string;
            } else if (!lengthIsInLimit) {
                System.out.println("Field must be no more than " + limit + " characters.");
            } else {
                System.out.println("Field must not be empty.");
            }
        }
    }

    public static void printNumberedListFrom1(ArrayList<?> list) {
        for (int i = 0; i < list.size(); i++) {
            String item = list.get(i).toString();
            int displayNumber = i + 1;
            System.out.printf("[%d] %s %n", displayNumber, item);
        }
    }
}
