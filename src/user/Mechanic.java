package user;

public class Mechanic extends Employee {

    public Mechanic(String userID, String fullName, String dateOfBirth, String address, String phoneNumber, String email, String password) throws Exception {
        super(userID, fullName, dateOfBirth, address, phoneNumber, email, password, "Mechanic", UserType.MECHANIC);
    }

    @Override
    public void performWork() {
        System.out.println("Handling vehicle services...");
    }
}
