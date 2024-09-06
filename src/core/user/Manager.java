package core.user;

public class Manager extends User {

    public Manager(String userID, String fullName, String dateOfBirth, String address, String phoneNumber, String email, String password) throws Exception {
        super(userID, fullName, dateOfBirth, address, phoneNumber, email, password, UserType.MANAGER);
    }

    @Override
    public void viewProfile() {
        System.out.println("Manager Profile: " + fullName);
    }

    // Manager-specific methods
    public void viewAllUsers() {
    }

    public void manageEntityRecords() {
    }
}
