package core.user;

import java.io.Serial;

public class Salesperson extends Employee {
    @Serial
    private static final long serialVersionUID = 9L;

    public Salesperson(String fullName, String dateOfBirth, String address, String phoneNumber, String email, String username, String password) throws Exception {
        super(fullName, dateOfBirth, address, phoneNumber, email, UserType.SALESPERSON, username, password);
    }
}
