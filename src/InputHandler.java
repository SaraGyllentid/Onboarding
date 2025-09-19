import java.util.Scanner;

public class InputHandler {
    private static Scanner scanner = new Scanner(System.in);

    public static String getNonEmptyString(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine();
            if (input != null) {
                String trimmed = input.trim();
                if (!trimmed.isEmpty()) {
                    return trimmed;
                }
            }
            System.out.println("Input is empty. Please try again.");
        }
    }

    public static int getInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            if (line == null || line.trim().isEmpty()) {
                System.out.println("Please enter a number.");
                continue;
            }
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    //för att kunna ändra användare men om inget ändras behåll gamla
    public static String getLineAllowEmpty(String prompt) {
        System.out.print(prompt);
        String in = scanner.nextLine();
        if (in == null) return "";
        return in.trim(); // kan vara tom sträng = behåll gamla värdet
    }
// --- Validation ---

    public static String getValidName(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input != null) {
                String trimmed = input.trim();
                // Tillåt a–z, A–Z, åäöÅÄÖ, mellanslag och bindestreck
                if (!trimmed.isEmpty() && trimmed.matches("[a-zA-ZåäöÅÄÖ\\s-]+")) {
                    return trimmed;
                }
            }
            System.out.println("Invalid name. Only letters, spaces and hyphens are allowed.");
        }
    }


    public static String getValidEmail(String prompt) {
        String regex = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input != null) {
                String trimmed = input.trim();
                if (trimmed.matches(regex)) {
                    return trimmed;
                }
            }
            System.out.println("Invalid email. Format must be text@text.text with no spaces.");
        }
    }

}
