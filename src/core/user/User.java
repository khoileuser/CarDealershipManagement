package core.user;

import core.Entity;
import utils.Activity;

import java.io.Serial;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.Serializable;

public abstract class User implements Serializable, Entity {
    @Serial
    private static final long serialVersionUID = 4L;

    protected String userID;
    protected String fullName;
    protected final Date dateOfBirth;
    protected String address;
    protected String phoneNumber;
    protected String email;
    protected UserType userType;
    protected boolean status;

    public User(String fullName, String dateOfBirth, String address, String phoneNumber, String email, UserType userType) throws Exception {
        Date dob = null;
        try {
            dob = setDateOfBirth(dateOfBirth);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        this.fullName = fullName;
        this.dateOfBirth = dob;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userType = userType;
        this.status = false;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getStringDateOfBirth() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(dateOfBirth);
    }

    public Date setDateOfBirth(String dateOfBirthInString) throws Exception {
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date dateOfBirth;
            dateOfBirth = df.parse(dateOfBirthInString);
            return dateOfBirth;
        } catch (ParseException e) {
            throw new Exception("Date of birth need to be in format: dd/MM/yyyy (Ex: 27/06/2007)");
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public abstract void viewProfile();

    public abstract boolean authenticate(String username, String password);

    public abstract String getUsername();

    public abstract String getPassword();

    public abstract ArrayList<Activity> getActivityLog();

    public abstract void setActivityLog(ArrayList<Activity> activityLog);

    public abstract void addActivity(Activity activity);

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", userType=" + userType +
                ", status=" + status +
                '}';
    }

    @Override
    public String getSearchString() {
        return userType + " " + fullName;
    }
}