package core.user;

import utils.Activity;

import java.io.Serial;
import java.util.ArrayList;

public class Manager extends User {
    @Serial
    private static final long serialVersionUID = 7L;

    protected String username;
    protected String password;
    protected ArrayList<Activity> activityLog;

    public Manager(String fullName, String dateOfBirth, String address, String phoneNumber, String email, String username, String password) throws Exception {
        super(fullName, dateOfBirth, address, phoneNumber, email, UserType.MANAGER);
        this.username = username;
        this.password = password;
        this.activityLog = new ArrayList<>();
    }

    @Override
    public void viewProfile() {
        System.out.println("Manager Profile: " + fullName);
    }

    @Override
    public boolean authenticate(String username, String password) {
        if (username.equals(this.username)) {
            return password.equals(this.password);
        }
        return false;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public ArrayList<Activity> getActivityLog() {
        return activityLog;
    }

    @Override
    public void setActivityLog(ArrayList<Activity> activityLog) {
        this.activityLog = activityLog;
    }

    @Override
    public void addActivity(Activity activity) {
        this.activityLog.add(activity);
    }
}
