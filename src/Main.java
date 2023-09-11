import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDate;
import java.time.Period;

public class Main {
    public static void main(String[] args) {
        Patient[] patients = {
                new Patient("John Doe", "1999-01-01", "1234AB", 81, 178),
                new Patient("Tim Timmer", "1976-12-31", "1111AB", 95, 192)
        };

        Scanner scanRole = new Scanner(System.in);
        System.out.println("Wat is uw rol?");
        String role = scanRole.nextLine();

        if (role.equals("huisarts")) {
            Scanner scanPatient = new Scanner(System.in);
            System.out.println("Wat is de naam van uw patient");
            String searchName = scanPatient.nextLine();

            Patient foundPatient = findPatientByName(patients, searchName);

            if (foundPatient != null) {
                System.out.println("Patient found in the array:");
                System.out.println("Full Name: " + foundPatient.getFullName());
                System.out.println("Postal Code: " + foundPatient.getPostalCode());
                System.out.println("Weight: " + foundPatient.getWeight());
                System.out.println("Lengte: " + foundPatient.getLength() + "cm");
                System.out.println("BMI: " + foundPatient.getBMI());
                System.out.println("Birth Date: " + foundPatient.getBirthDate());
                System.out.println("Leeftijd: " + foundPatient.getAge() + " jaar");
            } else {
                System.out.println("Patient not found in the array.");
            }

            scanPatient.close();
        }

        scanRole.close();
    }

    // Method to find a patient by name
    public static Patient findPatientByName(Patient[] patients, String searchName) {
        for (Patient patient : patients) {
            if (patient.getFullName().equalsIgnoreCase(searchName)) {
                return patient;
            }
        }
        return null; // Patient not found
    }
}

class Patient {
    private String fullName;
    private LocalDate birthDate;
    private String postalCode;
    private int weight;
    private int length;

    public Patient(String fullName, String birthDateStr, String postalCode, int weight, int length) {
        this.fullName = fullName;
        this.birthDate = LocalDate.parse(birthDateStr);
        this.postalCode = postalCode;
        this.weight = weight;
        this.length = length;
    }

    public String getFullName() {
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
        return Math.round(bmi * 100.0) / 100.0;
    }

        public int getAge() {
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(birthDate, currentDate);
        return age.getYears();
    }
}