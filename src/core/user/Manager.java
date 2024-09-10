package core.user;

import java.io.Serial;

public class Manager extends User {
    @Serial
    private static final long serialVersionUID = 7L;

    protected String username;
    protected String password;

    public Manager(String fullName, String dateOfBirth, String address, String phoneNumber, String email, String username, String password) throws Exception {
        super(fullName, dateOfBirth, address, phoneNumber, email, UserType.MANAGER);
        this.username = username;
        this.password = password;
    }

    @Override
    public void viewProfile() {
        System.out.println("Manager Profile: " + fullName);
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
