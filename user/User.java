package user;

import java.util.UUID;

public class User {
    private String name;
    private String email;
    private String phone;
    private String Id;

    // public User(String name, String email, String phone) {
    // this.name = name;
    // this.email = email;
    // this.phone = phone;
    // this.Id = UUID.randomUUID().toString();
    // }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return Id;
    }

    public void setId() {
        Id = UUID.randomUUID().toString();
    }

    public void setUserId(String Id) {
        this.Id = Id;
    }

    // helper to save user to database.txt
    protected String fileToString(User user) {
        return "USER:" + Id + "," + name + "," + email + "," + phone;
    }

    // helper to deserialization user from database.txt
    public User fromFileString(String line) {
        String content = line.substring(5);
        String[] parts = content.split(",");

        if (parts.length < 4)
            return null;
        User user = new User();
        user.setUserId(parts[0]);
        user.setName(parts[1]);
        user.setEmail(parts[2]);
        user.setPhone(parts[3]);

        return user;
    }

}
