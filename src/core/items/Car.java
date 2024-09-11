package core.items;

import core.Entity;
import core.Service;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Car extends Item implements Entity {
    @Serial
    private static final long serialVersionUID = 3L;

    // Attributes
    private String carID;
    private String make;
    private String model;
    private int year;
    private double mileage;
    private String color;
    private String status;
    private BigDecimal price;
    private ArrayList<Service> servicesHistory;
    private String notes;

    public Car(String make, String model, int year, double mileage, String color,
                String status, BigDecimal price, String notes) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.color = color;
        this.status = status;
        this.price = price;
        this.notes = notes;
        this.servicesHistory = new ArrayList<>();
    }

    //Getters and Setters'
    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
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

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ArrayList<Service> getServicesHistory() {
        return servicesHistory;
    }

    public void setServicesHistory(ArrayList<Service> historyServices) {
        this.servicesHistory = historyServices;
    }

    public void addService(Service service) {
        this.servicesHistory.add(service);
    }

    public void removeService(Service service) {
        this.servicesHistory.remove(service);
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
                ", status='" + status + '\'' +
                ", price=" + price +
                ", servicesHistory=" + servicesHistory +
                ", notes='" + notes + '\'' +
                '}';
    }

    @Override
    public String getSearchString() {
        return make + " " + model + " " + year + " (" + color + ")";
    }
}


