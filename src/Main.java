import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import java.io.FileReader;
import java.io.IOException;

import static java.lang.System.err;
import static java.lang.System.out;

import org.json.simple.JSONArray;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/json/patients.json";

        try (FileReader fileReader = new FileReader(filePath)) {
            Object file = JSONValue.parse(fileReader);
            if (file instanceof JSONArray patientList) {
                String userRole = User.user(args);
                out.println("Welkom " + userRole);
                Patient.handlePatientList(patientList, filePath, userRole);
            } else {
                out.println("The file does not contain a JSON array.");
            }
        } catch (IOException e) {
            err.println("File not found or cannot be read.");
            e.printStackTrace();
        }
    }
}
