import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public final class Utils {
    private Utils() {
    }

    // Found conflicting information about whether or not to close a System.in Scanner
    // Closing it caused a NoSuchElement exception so the decision was clear

    public static int scanInt(String prompt) {
        int num;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(prompt);
            try {
                num = scanner.nextInt();
                return num;
            } catch (InputMismatchException ignored) {
                System.out.println();
                System.out.println("Invalid input.");
                scanner.nextLine();
            }
        }
    }

    public static int scanBoundedInt(int min, int max, String prompt) {
        int num;
        while (true) {
            num = scanInt(prompt);
            if (min <= num && num <= max) {
                return num;
            }
            System.out.printf("%nNumber must be between %d and %d.%n", min, max);
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

    public static void printNumberedListFrom1(List<?> list) {
        for (int i = 0; i < list.size(); i++) {
            String item = list.get(i).toString();
            int displayNumber = i + 1;
            System.out.printf("[%d] %s %n", displayNumber, item);
        }
    }
}
