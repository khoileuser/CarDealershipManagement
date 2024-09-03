import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class User {
    private final String userID;
    private String fullName;
    private final Date dateOfBirth;
    private String address;
    private String phoneNumber;
    private String email;
    private String userType;
    private String status;

    public User(String fullName, String dateOfBirth, String phoneNumber, String address, String email, String userType, String status) throws Exception {
        Date dob = null;
        try {
            dob = setDateOfBirth(dateOfBirth);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        this.userID = setUserID();
        this.fullName = fullName;
        this.dateOfBirth = dob;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.userType = userType;
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    private String setUserID() {
        int number = (int) (Math.random() * 10000);
        return "u-" + number;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
