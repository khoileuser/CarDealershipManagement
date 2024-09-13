package utils;

import java.util.ArrayList;
import core.user.User;

public class Authentication {
    private final String username;  // Store username and password
    private final String password;  // Store username and their role (e.g., Manager, Employee)

    // Constructor
    public Authentication(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Authenticate user
    public User authenticate(ArrayList<User> userList) {
        for (User u : userList) {
            if (u.authenticate(username, password)) {
                return u;
            }
        }
        return null;
    }
}
