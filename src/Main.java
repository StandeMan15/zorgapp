import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Patient[] patients = {
                new Patient("John", "Doe", "1999-01-01", "1234AB", 81, 178, "encorafenib"),
                new Patient("Tim", "Timmer", "1976-12-31", "1111AB", 95, 192, "nadroparine")
        };

        Scanner scanRole = new Scanner(System.in);
        System.out.println("Wat is uw rol?");
        String role = scanRole.nextLine();

        Scanner scanPatient = new Scanner(System.in);
        System.out.println("Wat is de naam van uw patient");
        String searchName = scanPatient.nextLine();

        Patient foundPatient = findPatientByName(patients, searchName);

        if (foundPatient != null) {

            System.out.println("Naam: " + foundPatient.getFullName());
            System.out.println("Postcode: " + foundPatient.getPostalCode());
            System.out.println("Birth Date: " + foundPatient.getBirthDate());
            System.out.println("Leeftijd: " + foundPatient.getAge() + " jaar");

            if(role.equals("huisarts")) {
                System.out.println("Gewicht: " + foundPatient.getWeight());
                System.out.println("Lengte: " + foundPatient.getLength() + "cm");
                System.out.println("BMI: " + foundPatient.getBMI());
            }

            if(role.equals("fysio")) {
                System.out.println("medicatie" + foundPatient.getmedication());
            }
        } else {
            System.out.println("Deze naam is niet bij ons bekent");
        }

        scanPatient.close();
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
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String postalCode;
    private int weight;
    private int length;
    private String medication;

    public Patient(String firstName,String lastName, String birthDateStr, String postalCode, int weight, int length, String medication) {
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

    public String getmedication() {
        return medication;
    }
}