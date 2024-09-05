package user;

public class Client extends User {
    private Membership membershipType;
    private double totalSpending;

    public Client(String userID, String fullName, String dateOfBirth, String address, String phoneNumber, String email, String password, Membership membershipType, double totalSpending) throws Exception {
        super(userID, fullName, dateOfBirth, address, phoneNumber, email, password, UserType.CLIENT);
        this.membershipType = membershipType;
        this.totalSpending = totalSpending;
    }

    @Override
    public void viewProfile() {
        System.out.println("Client Profile: " + fullName + " - Membership: " + membershipType);
    }

    public void updateMembership() {
    }
}
