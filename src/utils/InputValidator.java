package utils;

public class InputValidator {

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{10}");
    }

    public static boolean isValidPrice(double price) {
        return price >= 0;
    }

    // Add more validation methods as needed
}
