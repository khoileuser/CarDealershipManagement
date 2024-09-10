package core.user;

import java.io.Serial;

public abstract class Employee extends User {
    @Serial
    private static final long serialVersionUID = 6L;

    protected String username;
    protected String password;

    public Employee(String fullName, String dateOfBirth, String address, String phoneNumber, String email, UserType userType, String username, String password) throws Exception {
        super(fullName, dateOfBirth, address, phoneNumber, email, userType);
        this.username = username;
        this.password = password;
    }

    @Override
    public void viewProfile() {
        System.out.println("Employee Profile: " + fullName + " - Position: " + userType);
    }

    @Override
    public boolean authenticate(String username, String password) {
        if (username.equals(this.username)) {
            return password.equals(this.password);
        }
        return false;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
