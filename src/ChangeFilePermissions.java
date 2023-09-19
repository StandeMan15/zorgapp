import java.io.File;

public class ChangeFilePermissions {
    public static void main(String[] args) {
        // Specify the path to the file you want to change permissions for
        String filePath = "src/json/patients.json";
        
        // Create a File object
        File file = new File(filePath);
        
        // Check if the file exists
        if (file.exists()) {
            // Set the file's read permission to true (allow reading)
            boolean success = file.setReadable(true);
            
            if (success) {
                System.out.println("Read permission set successfully.");
            } else {
                System.err.println("Failed to set read permission.");
            }
        } else {
            System.err.println("File does not exist.");
        }
    }
}