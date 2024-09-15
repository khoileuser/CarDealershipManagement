package utils;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {
    // Method for serializing an object to a file (using Java serialization)
    public static void writeObjectsToFile(ArrayList objList, String filePath) throws IOException {
        File checkFile = new File(filePath);
        if (!checkFile.exists()) { // create file if not exists
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
        if (!checkFile.exists()) { // return empty array if file not found
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
                System.out.println("Finished reading " + filePath);
                break;
            }
        }
        return objectList;
    }
}
