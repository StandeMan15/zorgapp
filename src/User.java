import java.util.Scanner;
import java.util.InputMismatchException;
import org.json.simple.JSONArray;

import static java.lang.System.*;

public class User {

    protected JSONArray patientList;

    public User(JSONArray patientList) {
        this.patientList = patientList;
    }

    public void savePatientDataToFile(String filePath) {
        // Implementation for saving patient data
    }

    public void handlePatientList(String filePath) {
        // Common method to handle patient data
        // Implement access control based on user type
    }

    public static String user(String[] args) {
        out.println("Menu opties:");
        out.println("1. Huisarts");
        out.println("2. Apotheker");
        out.println("3. Psygoloog");
        out.println("4. Fysiotherapeut");

        Scanner scanUser = new Scanner(in);
        out.println("Voer uw keuze in: ");

        String userRole = "";

        try {
            int choice = scanUser.nextInt();

            switch (choice) {
                case 1 -> userRole = "Huisarts";
                case 2 -> userRole = "Apotheker";
                case 3 -> userRole = "Psygoloog";
                case 4 -> userRole = "Fysiotherapeut";
                default -> out.println("Systeem wordt gesloten ivm (ongeldige) keuze");
            }
        } catch (InputMismatchException e) {
            out.println("Dit is geen geldige waarde");
        }
        //scanUser.close();

        return userRole;
    }
}
