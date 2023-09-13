import java.util.Scanner;
import java.time.LocalDate;
import java.time.Period;
import com.google.gson.Gson;  
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        List<Patient> patients = readPatientsFromJson("patients.json");

        Scanner scanRole = new Scanner(System.in);
        System.out.println("Wat is uw rol?");
        String role = scanRole.nextLine();

        if (role.equals("huisarts")) {
            Scanner scanPatient = new Scanner(System.in);
            System.out.println("Wat is de naam van uw patient");
            String searchName = scanPatient.nextLine();

            Patient foundPatient = findPatientByName(patients, searchName);

        if (foundPatient != null) {

            System.out.println("Naam: " + foundPatient.getFullName());
            System.out.println("Postcode: " + foundPatient.getPostalCode());
            System.out.println("Birth Date: " + foundPatient.getBirthDate());
            System.out.println("Leeftijd: " + foundPatient.getAge() + " jaar");

            if (role.equals("huisarts")) {
                System.out.println("Gewicht: " + foundPatient.getWeight());
                System.out.println("Lengte: " + foundPatient.getLength() + "cm");
                System.out.println("BMI: " + foundPatient.getBMI());
            }

            if (role.equals("fysio")) {
                System.out.println(foundPatient.getInjuries());
            }
        } else {
            System.out.println("Deze naam is niet bij ons bekend");
        }

        scanRole.close();
    }

    public static Patient findPatientByName(List<Patient> patients, String searchName) {
        for (Patient patient : patients) {
            if (patient.getFullName().equalsIgnoreCase(searchName)) {
                return patient;
            }
        }
        return null;
    }

    public static List<Patient> readPatientsFromJson(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<List<Patient>>(){}.getType();
            return new Gson().fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class Patient {
    private String fullName;
    private LocalDate birthDate;
    private String postalCode;
    private int weight;

    public Patient(String firstName, String lastName, String birthDateStr, String postalCode, int weight, int length, String injuries) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = LocalDate.parse(birthDateStr);
        this.postalCode = postalCode;
        this.weight = weight;
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
        return injuries;
    }
}