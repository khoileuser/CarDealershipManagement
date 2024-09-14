package core.user;

import java.io.Serial;

public class Mechanic extends Employee {

    public Mechanic(String fullName, String dateOfBirth, String address, String phoneNumber, String email, String username, String password) throws Exception {
        super(fullName, dateOfBirth, address, phoneNumber, email, UserType.MECHANIC, username, password);
    }
}
