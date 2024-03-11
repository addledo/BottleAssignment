import java.util.InputMismatchException;
import java.util.Scanner;

public final class Utils {
    private Utils() {}

    public static int scanInt(String prompt) {
        int num;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(prompt);
            try {
                num = scanner.nextInt();
                break;
            } catch (InputMismatchException ignored) {
                System.out.println("Invalid input.");
                scanner.nextLine();
            }
        }
        return num;
    }

    public static int scanBoundedInt(int min, int max, String prompt) {
        int num;
        //TODO               DOES THIS NEED TO BE INITIALISED?
        while (true) {
            num = scanInt(prompt);
            if (min <= num && num <= max) {
                return num;
            }
            else {
                System.out.printf("Number must be between %d and %d.%n", min, max);
            }
        }
    }

    public static void waitForUser() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }


//    public static String getLimitedLengthString(String prompt) {
//        int limit = 60;
//        Scanner scanner = new Scanner(System.in);
//        String string = scanner.nextLine().trim();
//        while (string.trim().length() > limit) {
//            System.out.println("That value is too long. Enter 60 characters or less.");
//            string = scanner.nextLine();
//        }
//        return string;
//    }

//    public static String getBoundedString(String prompt) {
//        String string = "";
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            System.out.print(prompt);
//            string = scanner.nextLine();
//            if (!string.isBlank()) {
//                break;
//            }
//            System.out.printf("%nField must not be empty. %n");
//        }
//        return string.trim();
//    }

    public static String getBoundedString(String prompt, int limit, boolean shouldAllowBlanks) {
        String string = "";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(prompt);
            string = scanner.nextLine();
            string = string.trim();
            if ((!string.isBlank() || shouldAllowBlanks) && string.length() < limit) {
                break;
            }
            System.out.printf("%nField must not be empty and must be no more than %d characters %n", limit);
        }
        return string;
    }
}
