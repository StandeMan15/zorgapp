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
        try {
            FileReader fileReader = new FileReader(filePath);

            int character;
            while ((character = fileReader.read()) != -1) {
                // Convert the character to a char and print it.
                char ch = (char) character;
                System.out.print(ch);
            }

            // Close the FileReader when done to free up system resources.
            fileReader.close();

        } catch (IOException e) {
            System.err.println("File not found or cannot be read.");
            e.printStackTrace();
        }      

       Scanner scanPatient = new Scanner(System.in);
       System.out.println("Wat is de naam van uw patient");
       String searchName = scanPatient.nextLine();

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
}

class Patient {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String postalCode;
    private int weight;
    private int length;
    private String injuries;

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