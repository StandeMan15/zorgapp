import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Scanner;

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
                    System.out.println("Wat is de naam van uw patient");
                    String searchName = scanPatient.nextLine();

                    // Search for the patient by name
                    JSONObject foundPatient = findPatientByName(patientList, searchName);

                    if (foundPatient != null) {
                        String firstName = (String) ((JSONObject) foundPatient.get("name")).get("firstName");
                        String lastName = (String) ((JSONObject) foundPatient.get("name")).get("lastName");
                        String injuries = (String) foundPatient.get("injuries");

                        System.out.println("Patient: " + firstName + " " + lastName);
                        System.out.println("Injuries: " + injuries);
                    } else {
                        System.out.println("Patient with name '" + searchName + "' not found.");
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

        scanRole.close();
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
}

class Patient {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String postalCode;
    private int weight;
    private int length;
    private String medication;

    public Patient(String firstName, String lastName, String birthDateStr, String postalCode, int weight, int length, String medication) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = LocalDate.parse(birthDateStr);
        this.postalCode = postalCode;
        this.weight = weight;
        this.length = length;
        this.medication = medication;
    }

    public String getFullName() {
        String fullName = firstName + " " + lastName;
        return fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public int getWeight() {
        return weight;
    }

    public int getLength() {
        return length;
    }

    public double getBMI() {
        double heightInMeters = (double) length / 100.0;
        double bmi = weight / (heightInMeters * heightInMeters);
        return Math.round(bmi * 10.0) / 10.0;
    }

    public int getAge() {
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthDate, currentDate);
        return age.getYears();
    }

    public String getInjuries() {
        return medication;
    }
}