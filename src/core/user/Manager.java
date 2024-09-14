package core.user;

import core.Activity;

import java.io.Serial;
import java.util.ArrayList;

public class Manager extends User {

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

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
