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
        System.out.println(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number");
            scanner.nextLine();
            System.out.println(prompt);
        }
        int i = scanner.nextInt();
        scanner.nextLine();
        return i;
    }
    //för att kunna ändra användare men om inget ändras behåll gamla
    public static String getLineAllowEmpty(String prompt) {
        System.out.print(prompt);
        String in = scanner.nextLine();
        if (in == null) return "";
        return in.trim(); // kan vara tom sträng = behåll gamla värdet
    }

}
