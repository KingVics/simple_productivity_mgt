package common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileDatabaseHelper {
    private static final String FILE_NAME = "database.txt";

    // Reads all lines from the text file safely
    public static List<String> readAllLines() {
        List<String> lines = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists())
            return lines;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Database Read Error: " + e.getMessage());
        }
        return lines;
    }

    // Appends a single row to the file (used during entity creation)
    public static void appendLine(String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(line);
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Database Write Error: " + e.getMessage());
        }
    }

    // Safely overwrites the entire file (used during updates and deletions)
    public static boolean writeAllLines(List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, false))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("Database Write Error: " + e.getMessage());
            return false;
        }
    }
}
