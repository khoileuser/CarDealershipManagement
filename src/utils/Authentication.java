package utils;

import java.util.HashMap;
import java.util.Map;
import core.user.User;
import core.user.UserType;

public class Authentication {
    private final Map<String, String> userCredentials;  // Store username and password
    private final Map<String, UserType> userRoles;  // Store username and their role (e.g., Manager, Employee)

    // Constructor
    public Authentication() {
        userCredentials = new HashMap<>();
        userRoles = new HashMap<>();
    }

    // Add credentials and role
    public void addUser(String username, String password, UserType role) {
        userCredentials.put(username, password);
        userRoles.put(username, role);
    }

    // Authenticate user
    public boolean authenticate(String username, String password) {
        if (userCredentials.containsKey(username)) {
            return userCredentials.get(username).equals(password);
        }
        return false;
    }

    // Get user role after successful login
    public UserType getRole(String username) {
        return userRoles.get(username);
    }

    // Remove user credentials
    public void removeUser(String username) {
        userCredentials.remove(username);
        userRoles.remove(username);
    }

    // Validate if the user exists
    public boolean userExists(String username) {
        return userCredentials.containsKey(username);
    }
}
