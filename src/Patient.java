import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.*;

public class Patient {
    public static void handlePatientList(JSONArray patientList, String filePath, String userRole) {
        if (!patientList.isEmpty()) {

            out.println("Wat is de naam van uw patiënt?");
            Scanner scanPatient = new Scanner(System.in);
            String searchName = scanPatient.nextLine();
            // Search for the patient by name
            JSONObject foundPatient = findPatientByName(patientList, searchName);

            if (foundPatient != null) {

                displayPatientData(foundPatient);
                out.println("Menu opties:");
                out.println("1. Bewerk lengte");
                out.println("2. Bewerk gewicht");

                if (userRole.equals("Huisarts")) {
                    //laat zien van bmi grafiek
                    //constulten bijhouden
                } else if (userRole.equals("Apotheker")) {
                    out.println("3. Voeg medicijnen toe");
                    out.println("4. Bewerk medicijn dosages");
                } else if (userRole.equals("Psygoloog")) {
                    // lijst patienten
                } else if (userRole.equals("Fysiotherapeut")) {
                    registerLonginhoud(foundPatient);
                } else {
                    out.println("Access denied. Invalid user role or insufficient permissions.");
                }

                out.println("5. Exit");
                out.print("Voer uw keuze in: ");
                int choice = scanPatient.nextInt();

                if (choice == 1) {
                    // Edit Length
                    out.print("Nieuwe lengte (in cm): ");
                    int newLength = scanPatient.nextInt();
                    foundPatient.put("length", newLength);
                    out.println("Lengte is aangepast.");
                } else if (choice == 2) {
                    // Edit Weight
                    out.print("Nieuw gewicht (in kg): ");
                    double newWeight = scanPatient.nextDouble();
                    foundPatient.put("weight", newWeight);
                    out.println("Gewicht is aangepast.");
                } else if (choice == 3) {
                    // Add medicine
                    scanPatient.nextLine();
                    out.print("Voeg medicijnen toe (gescheiden door komma's): ");
                    String inputMedicine = scanPatient.nextLine();
                    out.print("Voeg medicijn dosages toe (gescheiden door komma's): ");
                    String inputDosages = scanPatient.nextLine();
                    List<String> newMedicine = Arrays.asList(inputMedicine.split(","));
                    List<String> newDosages = Arrays.asList(inputDosages.split(","));
                    JSONArray existingMedicineArray = (JSONArray) foundPatient.get("medicine");
                    JSONArray existingDosagesArray = (JSONArray) foundPatient.get("dosages");

                    if (existingMedicineArray == null) {
                        existingMedicineArray = new JSONArray();
                    }
                    if (existingDosagesArray == null) {
                        existingDosagesArray = new JSONArray();
                    }
                    existingMedicineArray.addAll(newMedicine);
                    existingDosagesArray.addAll(newDosages);
                    foundPatient.put("medicine", existingMedicineArray);
                    foundPatient.put("dosages", existingDosagesArray);
                    out.println("Medicijn(en) en dosages toegevoegd.");
                } else if (choice == 4) {
                    // Edit Medicine Dosages
                    JSONArray existingMedicineArray = (JSONArray) foundPatient.get("medicine");
                    JSONArray existingDosagesArray = (JSONArray) foundPatient.get("dosages");

                    if (existingMedicineArray != null && existingDosagesArray != null) {
                        out.println("Huidige medicijnen en dosages:");
                        for (int i = 0; i < existingMedicineArray.size(); i++) {
                            String medicine = (String) existingMedicineArray.get(i);
                            String dosage = (String) existingDosagesArray.get(i);
                            out.println((i + 1) + ". " + medicine + " - Dosage: " + dosage);
                        }
                        out.print("Voer het nummer in van het medicijn dat u wilt bewerken: ");
                        int medChoice = scanPatient.nextInt();
                        if (medChoice >= 1 && medChoice <= existingMedicineArray.size()) {
                            out.print("Voer de nieuwe dosage in: ");
                            String newDosage = scanPatient.next();
                            existingDosagesArray.set(medChoice - 1, newDosage);
                            foundPatient.put("dosages", existingDosagesArray);
                            out.println("Dosage is aangepast.");
                        } else {
                            out.println("Ongeldige keuze.");
                        }
                    } else {
                        out.println("Geen medicijnen met dosages gevonden.");
                    }
                } else {
                    out.println("Geen gegevens aangepast.");
                }
                savePatientDataToFile(patientList, filePath);
                displayPatientData(foundPatient);
            } else {
                out.println(searchName + " is niet bekend in onze lijst");
            }
            scanPatient.close();
        } else {
            out.println("The JSON array is empty.");
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
            err.println("Error saving data to the file.");
            e.printStackTrace();
        }
    }

    public static void registerLonginhoud(JSONObject patient) {
        Scanner scanner = new Scanner(System.in);
        out.print("Voer de longinhoud (in liters) in voor " + patient.get("name") + ": ");
        double longinhoud = scanner.nextDouble();
        patient.put("longinhoud", longinhoud);
        out.println("Longinhoud is geregistreerd.");

    }

    public static void saveLonginhoud(JSONObject patient, double longinhoud) {
        patient.put("longinhoud", longinhoud);
    }

    public static void displayPatientData(JSONObject patient) {
        StringBuilder minusString = new StringBuilder();

        minusString.append("-".repeat(26));

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

        JSONArray medicineArray = (JSONArray) patient.get("medicine");
        List<String> medicine = new ArrayList<>();

        if (medicineArray != null) {
            for (Object medicijnObj : medicineArray) {
                medicine.add((String) medicijnObj);
            }
        }

        out.println(minusString);
        out.println("Patient: " + firstName + " " + lastName);
        out.println(minusString);
        out.println("Blessures: " + injuries);
        out.println("Geboortedatum: " + formattedBirthday);
        out.println("Lengte: " + length + "cm");
        out.println("Gewicht: " + weight + "kg");
        out.println("BMI " + getBMI(weight, length) + " kg/m² ");
        out.println("Leeftijd: " + getAge(birthdate));
        out.println("Medicijnen: " + String.join(",", medicine));
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
