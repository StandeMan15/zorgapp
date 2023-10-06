import java.util.Scanner;

public class User {
    public static void main(String[] args) {
        System.out.println("Menu opties:");
        System.out.println("1. Huisarts");
        System.out.println("2. Apotheker");
        System.out.println("3. Psygoloog");
        System.out.println("4. Exit");

        Scanner scanUser = new Scanner(System.in);
        System.out.println("Voer uw keuze in: ");
        int choice = scanUser.nextInt();

        switch (choice) {
            case 1:
                System.out.println("U bent een Huisarts");
                break;
            case 2:
                System.out.println("U bent een Apotheker");
                break;
            case 3:
                System.out.println("U bent een Psygoloog");
                break;
            default:
                System.out.println("Systeem word gesloten ivm (ongeldige) keuze");
                scanUser.close();
                break;
        }
        Scanner sc = new Scanner(System.in);


    }
}