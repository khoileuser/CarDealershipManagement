package core;

import java.util.ArrayList;

public class Car extends Item {
    // Attributes
    private String carID;
    private String make;
    private String model;
    private int year;
    private double mileage;
    private String color;
    private String status;
    private double price;
    private ArrayList<Service> historyServices;
    private String notes;

    public Car (String carID, String make, String model, int year, int mileage, String color,
                String status, int price, String notes) {
        this.carID = carID;
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.color = color;
        this.status = status;
        this.price = price;
        this.notes = notes;
    }

    //Getters and Setters'

    public String getCarID() {
        return carID;
    }

    public void getCarID(String carID) {
        this.carID = carID;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<Service> getHistoryServices() {
        return historyServices;
    }

    public void setHistoryServices(ArrayList<Service> historyServices) {
        this.historyServices = historyServices;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carID='" + carID + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", mileage=" + mileage +
                ", color='" + color + '\'' +
                ", status=" + status +
                ", price=" + price +
                ", historyServices=" + historyServices +
                ", notes='" + notes + '\'' +
                '}';
    }
}


