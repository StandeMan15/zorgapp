import java.util.Scanner;
import java.util.InputMismatchException;
public class User {
    public static void main(String[] args) {
        System.out.println("Menu opties:");
        System.out.println("1. Huisarts");
        System.out.println("2. Apotheker");
        System.out.println("3. Psygoloog");

        Scanner scanUser = new Scanner(System.in);
        System.out.println("Voer uw keuze in: ");


        try {
            int choice = scanUser.nextInt();
            StringBuilder userRole = new StringBuilder();

            switch (choice) {
                case 1 -> userRole.append("Huisarts");
                case 2 -> userRole.append("Apotheker");
                case 3 -> userRole.append("Psygoloog");
                default -> {
                    System.out.println("Systeem word gesloten ivm (ongeldige) keuze");
                    scanUser.close();
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Dit is geen geldige waarde");
        }
    }
}