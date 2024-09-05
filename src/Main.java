import java.io.*;
import java.util.Map;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        welcomeScreen();



    }

    public static void login() {

    }

    public static void loadData() throws IOException, ClassNotFoundException {
        File saveFile = new File("save.data");


    }

    public static void welcomeScreen() {
        System.out.println(
                "COSC2081 GROUP ASSIGNMENT" +
                        "AUTO168 CAR DEALERSHIP MANAGEMENT SYSTEM" +
                        "Instructor: Mr. Minh Vu & Mr. Dung Nguyen" +
                        "Group: Confuse Group" +
                        "s3975162 Le Nguyen Khoi" +
                        "s39 Tran Tuan Anh" +
                        "s39 Nguyen Vu Duy" +
                        "s39 Le Minh Tri"
        );
    }
}