package core.user;

import java.io.Serial;

public class Mechanic extends Employee {
    @Serial
    private static final long serialVersionUID = 8L;

    // Constructor
    public Mechanic(String fullName, String dateOfBirth, String address, String phoneNumber, String email, String username, String password) throws Exception {
        super(fullName, dateOfBirth, address, phoneNumber, email, UserType.MECHANIC, username, password);
    }
}
