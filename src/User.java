import java.util.Scanner;
import java.util.InputMismatchException;

import static java.lang.System.*;

public class User {
    public static String user(String[] args) {
        out.println("Menu opties:");
        out.println("1. Huisarts");
        out.println("2. Apotheker");
        out.println("3. Psygoloog");

        Scanner scanUser = new Scanner(in);
        out.println("Voer uw keuze in: ");

        String userRole = "";

        try {
            int choice = scanUser.nextInt();

            switch (choice) {
                case 1 -> userRole = "Huisarts";
                case 2 -> userRole = "Apotheker";
                case 3 -> userRole = "Psygoloog";
                default -> out.println("Systeem wordt gesloten ivm (ongeldige) keuze");
            }
        } catch (InputMismatchException e) {
            out.println("Dit is geen geldige waarde");
        } finally {
            scanUser.close();
        }

        return userRole;
    }
}
