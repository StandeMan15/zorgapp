import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;
import java.time.Period;

public class Main {
    public static void main(String[] args) {

        Scanner scanRole = new Scanner(System.in);
        System.out.println("Wat is uw rol?");
        String role = scanRole.nextLine();

        String filePath = "src/json/patients.json";

        try (FileReader fileReader = new FileReader(filePath)) {
            Object file = JSONValue.parse(fileReader);

            if (file instanceof JSONArray) {
                JSONArray patientList = (JSONArray) file;

                if (patientList.size() > 0) {

                    Scanner scanPatient = new Scanner(System.in);
                    System.out.println("What is the name of your patient?");
                    String searchName = scanPatient.nextLine();

                    // Search for the patient by name
                    JSONObject foundPatient = findPatientByName(patientList, searchName);

                    if (foundPatient != null) {
                        String firstName = (String) ((JSONObject) foundPatient.get("name")).get("firstName");
                        String lastName = (String) ((JSONObject) foundPatient.get("name")).get("lastName");
                        String injuries = (String) foundPatient.get("injuries");
                        String birthday = (String) foundPatient.get("birthDateStr");
                        double weight = (double) foundPatient.get("weight");
                        Long lengthLong = (Long) foundPatient.get("length");
                        int length = lengthLong.intValue();
                        LocalDate birthdate = LocalDate.parse(birthday);


                        System.out.println("Patient: " + firstName + " " + lastName);
                        System.out.println("Blessures: " + injuries);
                        System.out.println("Geboortedatum: " + birthdate);

                        // Calculate the age based on the birthdate
                        int age = getAge(birthdate);
                        System.out.println("Leeftijd: " + age);

                        double bmi = getBMI(weight, length);
                        System.out.println("BMI: " + bmi);
                    } else {
                        System.out.println(searchName + " is niet bekent bij ons");
                    }

                    scanPatient.close();
                } else {
                    System.out.println("The JSON array is empty.");
                }
            } else {
                System.out.println("The file does not contain a JSON array.");
            }
        } catch (IOException e) {
            System.err.println("File not found or cannot be read.");
            e.printStackTrace();
        }
    }

    public static JSONObject findPatientByName(JSONArray patientList, String searchName) {
        for (Object patientObj : patientList) {
            JSONObject patient = (JSONObject) patientObj;
            JSONObject name = (JSONObject) patient.get("name");
            String firstName = (String) name.get("firstName");
            String lastName = (String) name.get("lastName");
            String fullName = firstName + " " + lastName;

            if (fullName.equalsIgnoreCase(searchName)) {
                return patient;
            }
        }
        return null;
    }

    public static int getAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthDate, currentDate);
        return age.getYears();
    }

    public static double getBMI(double weight, int length) {
        double heightInMeters = length / 100.0;
        double bmi = weight / (heightInMeters * heightInMeters);
        return Math.round(bmi * 10.0) / 10.0;
    }
}