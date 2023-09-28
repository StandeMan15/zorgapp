import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import java.io.FileReader;
import java.io.IOException;

public class test {
    public static void main(String[] args) {
        String filePath = "src/json/patients.json";

        try (FileReader fileReader = new FileReader(filePath)) {
            Object file = JSONValue.parse(fileReader);

            if (file instanceof JSONArray patientlist) {

                System.out.println(patientlist);

                 String birthday = (String) patientlist.get("injuries");
//                if (birthday != null) {
//                    System.out.println("Birthdate: " + birthday);
//                } else {
//                    System.out.println("Key 'birthDateStr' not found in the JSON object.");
//                }
            } else {
                System.out.println("The file does not contain a JSON object.");
            }
        } catch (
                IOException e) {
            System.err.println("File not found or cannot be read.");
            e.printStackTrace();
        }

    }
}
