package src;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Utils {
    private static final Scanner scanner = new Scanner(System.in);


    public static String getChoiceFromChoicesLoop(String prompt, String[] choices) {
        int choicesLen = choices.length;

        while (true) {
            System.out.println(prompt);

            for (int i = 0; i < choicesLen; i++) {
                String choice = choices[i];
                System.out.printf("[%d] %s\n", i + 1, choice);
            }

            System.out.print("Enter index of choice: ");

            try {
                int chosenIndex = scanner.nextInt();
                System.out.println();
                return choices[chosenIndex - 1];
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Input is out of range. Only enter the displayed numbers.\n");
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Input not recognized. Input must be a number.\n");
                scanner.nextLine();
            }
        }
    }

    public static String getStringInput(String prompt) {
        String input = "";

        do {
            try {
                System.out.print(prompt);
                scanner.nextLine();
                input = scanner.nextLine();
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input.\n");
                scanner.nextLine();
            }
        } while (input.isBlank());
            
        return input;
    }

    public static float getFloatInput(String prompt, boolean doNegative) {
        float input = 0.f;

        do {
            try {
                System.out.printf(prompt);
                input = scanner.nextFloat();
                if (!doNegative && input < 0) {
                    throw new InputMismatchException();
                }
            }
            catch (InputMismatchException e) {
                System.out.println("\nInvalid input.\n");
                scanner.next();
            }
        } while (input == 0.f);

        return input;
    }

    public static int getIntInput(String prompt, boolean doNegative) {
        int input = 0;

        do {
            try {
                System.out.printf(prompt);
                input = scanner.nextInt();
                scanner.nextLine();
                if (!doNegative && input < 0) {
                    throw new InputMismatchException();
                }
            }
            catch (InputMismatchException e) {
                System.out.println("\nInvalid input.\n");
                scanner.next();
            }
        } while (input == 0);

        return input;
    }
}
