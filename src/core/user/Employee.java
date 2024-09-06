package core.user;

public abstract class Employee extends User {
    protected String jobPosition;

    public Employee(String userID, String fullName, String dateOfBirth, String address, String phoneNumber, String email, String password, String jobPosition, UserType userType) throws Exception {
        super(userID, fullName, dateOfBirth, address, phoneNumber, email, password, userType);
        this.jobPosition = jobPosition;
    }

    @Override
    public void viewProfile() {
        System.out.println("Employee Profile: " + fullName + " - Position: " + jobPosition);
    }

    public abstract void performWork();
}
