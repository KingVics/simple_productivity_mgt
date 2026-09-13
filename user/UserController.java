package user;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class UserController {
    private static final String FILE_NAME = "database.txt";

    public User saveUser(User user) {
        // save new user in database
        try (BufferedWriter wr = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            wr.write(user.fileToString(user));
            wr.newLine();
        } catch (Exception e) {
            System.out.println("Error saving user to file: " + e.getMessage());
        }

        return user;
    }

    public User findByEmail(String email) {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return null;

        try (BufferedReader r = new BufferedReader(new FileReader(file))) {
            String lines;
            while ((lines = r.readLine()) != null) {
                if (lines.startsWith("USER")) {
                    User user = new User().fromFileString(lines);
                    if (user != null && user.getEmail().equalsIgnoreCase(email)) {
                        return user;
                    }
                }

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
