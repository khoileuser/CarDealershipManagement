package core.user;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public abstract class User {
    protected String userID;
    protected String fullName;
    protected final Date dateOfBirth;
    protected String address;
    protected String phoneNumber;
    protected String email;
    protected String password;
    protected UserType userType;
    protected boolean status;
    protected ArrayList<String> activityLog;

    public User(String userID, String fullName, String dateOfBirth, String address, String phoneNumber, String email, String password, UserType userType) throws Exception {
        Date dob = null;
        try {
            dob = setDateOfBirth(dateOfBirth);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        this.userID = userID;
        this.fullName = fullName;
        this.dateOfBirth = dob;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.status = true;
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

//    only has set password for security
    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isActive() { return status; }

    public void deactivateUser() { this.status = false; }
    public void reactivateUser() { this.status = true; }

    public void viewProfile() {};

    @Override
    public String toString() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        return "User{" +
                "userID='" + userID + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth=" + df.format(dateOfBirth) +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", userType='" + userType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}