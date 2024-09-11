package utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Activity {
    private String ActivityID;
    private final List<String> logs = new ArrayList<>();

    public void logActivity(String userID, String activity) {
        String logEntry = LocalDateTime.now() + " - User: " + userID + " - " + activity;
        logs.add(logEntry);
        System.out.println(logEntry);  // Optionally write to console
    }

    public List<String> getLogs() {
        return logs;
    }

    public void saveLogsToFile(String filename) {
        FileHandler.writeToFile(filename, logs);
    }
}
