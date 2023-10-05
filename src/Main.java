import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.FileReader;
import java.io.FileWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {
    public static void main(String[] args) {

//        Scanner scanRole = new Scanner(System.in);
//        System.out.println("Wat is uw rol?");
//        String role = scanRole.nextLine();

        String filePath = "src/json/patients.json";

        try (FileReader fileReader = new FileReader(filePath)) {
            Object file = JSONValue.parse(fileReader);

            if (file instanceof JSONArray) {
                JSONArray patientList = (JSONArray) file;

                if (patientList.size() > 0) {

                    Scanner scanPatient = new Scanner(System.in);
                    System.out.println("Wat is de naam van uw patient?");
                    String searchName = scanPatient.nextLine();

                    // Search for the patient by name
                    JSONObject foundPatient = findPatientByName(patientList, searchName);

                    if (foundPatient != null) {
                        displayPatientData(foundPatient);

                        System.out.println("Menu opties:");
                        System.out.println("1. Bewerk lengte");
                        System.out.println("2. Bewerk gewicht");
                        System.out.println("3. Voeg medicijnen toe");
                        System.out.println("4. Exit");
                        System.out.print("Voer uw keuze in: ");
                        int choice = scanPatient.nextInt();

                        if (choice == 1) {
                            // Edit Length
                            System.out.print("Nieuwe lengte (in cm): ");
                            int newLength = scanPatient.nextInt();
                            foundPatient.put("length", newLength);
                            System.out.println("Lengte is aangepast.");
                        } else if (choice == 2) {
                            // Edit Weight
                            System.out.print("Nieuw gewicht (in kg): ");
                            double newWeight = scanPatient.nextDouble();
                            foundPatient.put("weight", newWeight);
                            System.out.println("Gewicht is aangepast.");
                        } else if (choice == 3) {
                            // Add Medicijnen
                            scanPatient.nextLine();
                            System.out.print("Voeg medicijnen toe (gescheiden door kommas): ");
                            String inputMedicijnen = scanPatient.nextLine();

                            List<String> newMedicijnen = Arrays.asList(inputMedicijnen.split(","));

                            JSONArray existingMedicijnenArray = (JSONArray) foundPatient.get("medicijnen");

                            if (existingMedicijnenArray == null) {
                                existingMedicijnenArray = new JSONArray();
                            }

                            existingMedicijnenArray.addAll(newMedicijnen);

                            foundPatient.put("medicijnen", existingMedicijnenArray);

                            System.out.println("Medicijnen toegevoegd.");
                        } else {
                            System.out.println("Geen gegevens aangepast.");
                        }

                        savePatientDataToFile(patientList, filePath);

                        displayPatientData(foundPatient);
                    } else {
                        System.out.println(searchName + " is niet bekent in onze lijst");
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

    public static void savePatientDataToFile(JSONArray patientList, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(patientList.toJSONString());
        } catch (IOException e) {
            System.err.println("Error saving data to the file.");
            e.printStackTrace();
        }
    }



    public static void displayPatientData(JSONObject patient) {
        StringBuilder minusString = new StringBuilder();

        for (int i = 0; i < 26; i++) {
            minusString.append("-");
        }

        String firstName = (String) ((JSONObject) patient.get("name")).get("firstName");
        String lastName = (String) ((JSONObject) patient.get("name")).get("lastName");
        String injuries = (String) patient.get("injuries");

        String birthday = (String) patient.get("birthDateStr");
        LocalDate birthdate = LocalDate.parse(birthday);
        DateTimeFormatter europeanDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedBirthday = birthdate.format(europeanDateFormatter);

        double weight = (double) patient.get("weight");
        Long lengthLong = (Long) patient.get("length");
        int length = lengthLong.intValue();

        JSONArray medicijnenArray = (JSONArray) patient.get("medicijnen");
        List<String> medicijnen = new ArrayList<>();

        if (medicijnenArray != null) {
            for (Object medicijnObj : medicijnenArray) {
                medicijnen.add((String) medicijnObj);
            }
        }

        System.out.println(minusString);
        System.out.println("Patient: " + firstName + " " + lastName);
        System.out.println(minusString);
        System.out.println("Blessures: " + injuries);
        System.out.println("Geboortedatum: " + formattedBirthday);
        System.out.println("Lengte: " + length + "cm");
        System.out.println("Gewicht: " + weight + "kg");
        System.out.println("Medicijnen: " + String.join(", ", medicijnen));
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