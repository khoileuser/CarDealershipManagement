package core.user;
import utils.Membership;

import java.io.Serial;
import java.math.BigDecimal;

public class Client extends User {
    @Serial
    private static final long serialVersionUID = 5L;

    private final Membership membershipType;
    private final BigDecimal totalSpending;

    public Client(String fullName, String dateOfBirth, String address, String phoneNumber, String email) throws Exception {
        super(fullName, dateOfBirth, address, phoneNumber, email, UserType.CLIENT);
        this.membershipType = Membership.NONE;
        this.totalSpending = BigDecimal.valueOf(0);
    }

    @Override
    public void viewProfile() {
        System.out.println("Client Profile: " + fullName + " - Membership: " + membershipType);
    }

    @Override
    public boolean authenticate(String username, String password) {
        return false;
    }

    public void updateMembership() {
//        this.membershipType = Membership.determineMembership(this.totalSpending);
    }

    public void addTotalSpending() {

    }

    public void editTotalSpending() {

    }

    public BigDecimal getTotalSpending() {
        return totalSpending;
    }

    @Override
    public String getUsername() {
        return "Client doesn't have login credentials";
    }

    @Override
    public String getPassword() {
        return "Client doesn't have login credentials";
    }
}
