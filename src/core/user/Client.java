package core.user;
import core.Activity;
import core.Membership;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Client extends User {
    @Serial
    private static final long serialVersionUID = 5L;

    private Membership membershipType;
    private BigDecimal totalSpending;

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

    public Membership getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(Membership membershipType) {
        this.membershipType = membershipType;
    }

    public void updateMembership() {
        this.membershipType = Membership.determineMembership(this.totalSpending);
    }

    public void setTotalSpending(BigDecimal totalSpending) {
        this.totalSpending = totalSpending;
    }

    public BigDecimal getTotalSpending() {
        return totalSpending;
    }

    public void addTotalSpending(BigDecimal spending) {
        this.totalSpending = this.totalSpending.add(spending);
        updateMembership();
    }

    @Override
    public String getUsername() {
        return "Client doesn't have login credentials";
    }

    @Override
    public String getPassword() {
        return "Client doesn't have login credentials";
    }

    @Override
    public ArrayList<Activity> getActivityLog() {
        return null;
    }

    @Override
    public void setActivityLog(ArrayList<Activity> activityLog) {
        System.out.println("Client doesn't have activity log.");
    }

    @Override
    public void addActivity(Activity activity) {
        System.out.println("Client doesn't have activity log.");
    }
}
