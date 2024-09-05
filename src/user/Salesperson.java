package user;

public class Salesperson extends Employee {

    public Salesperson(String userID, String fullName, String dateOfBirth, String address, String phoneNumber, String email, String password) throws Exception {
        super(userID, fullName, dateOfBirth, address, phoneNumber, email, password, "Salesperson", UserType.SALESPERSON);
    }

    @Override
    public void performWork() {
        System.out.println("Handling sales and managing transactions...");
    }
}
