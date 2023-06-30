import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class FolderLocker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the folder name: ");
        String folderPath = scanner.nextLine();
        
        // Create a new folder
        File folder = new File(folderPath);
        if (!folder.exists()) {
            if (folder.mkdir()) {
                System.out.println("Folder created successfully.");
            } else {
                System.out.println("Failed to create the folder.");
                return;
            }
        } else {
            System.out.println("Folder already exists.");
        }
        
        // Set the password for the folder
        System.out.println("Enter the password for the folder: ");
        String password = scanner.nextLine();
        try {
            Files.write(Paths.get(folderPath + "\\password.txt"), password.getBytes(), StandardOpenOption.CREATE);
            System.out.println("Password set successfully.");
        } catch (IOException e) {
            System.out.println("Failed to set the password.");
            e.printStackTrace();
            return;
        }
        
        // Open the folder
        System.out.println("Enter the password to open the folder: ");
        String enteredPassword = scanner.nextLine();
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(folderPath + "\\password.txt"));
            String savedPassword = new String(fileBytes);
            if (enteredPassword.equals(savedPassword)) {
                Runtime.getRuntime().exec("explorer.exe " + folderPath);
                System.out.println("Folder opened successfully.");
            } else {
                System.out.println("Incorrect password. Folder access denied.");
            }
        } catch (IOException e) {
            System.out.println("Failed to open the folder.");
            e.printStackTrace();
        }
        
        scanner.close();
    }
}
