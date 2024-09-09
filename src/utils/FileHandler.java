package utils;

import core.items.AutoPart;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public static void writeToFile(String filename, List<String> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) { // Append mode
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method for serializing an object to a file (using Java serialization)
    public static void writeObjectsToFile(ArrayList objList, String filePath) throws IOException {
        File checkFile = new File(filePath);
        if (!checkFile.exists()) {
            checkFile.getParentFile().mkdirs();
            checkFile.createNewFile();
        }
        FileOutputStream of = new FileOutputStream(filePath);
        ObjectOutputStream objectOut = new ObjectOutputStream(of);
        for (Object o: objList) {
            objectOut.writeObject(o);
        }
        objectOut.close();
    }

    // Method for deserializing an object from a file
    public static ArrayList<Object> readObjectsFromFile(String filePath) throws IOException {
        File checkFile = new File(filePath);
        if (!checkFile.exists()) {
            System.out.println(filePath + " not found, return empty array");
            return new ArrayList<>();
        }
        Object obj = null;
        ArrayList<Object> objectList = new ArrayList<>();
        FileInputStream fi = new FileInputStream(filePath);
        ObjectInputStream objectIn = new ObjectInputStream(fi);
        while (true) {
            try {
                obj = objectIn.readObject();
                objectList.add((Object) obj);
            } catch (EOFException | ClassNotFoundException e) {
                System.out.println("Finished reading all the objects!!!");
                break;
            }
        }
        return objectList;
    }

    // Overwrite a file (delete old data and replace with new)
    public static void overwriteFile(String filePath, List<String> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
