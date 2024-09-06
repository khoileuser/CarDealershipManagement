package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import core.Car;
import core.SalesTransaction;
import core.Service;

import core.user.Mechanic;
import core.user.Salesperson;

public class Statistics {
    public static int countCars(ArrayList<Car> cars, ArrayList<SalesTransaction> transactions, String date, SimpleDateFormat df) throws ParseException {
        int soldCars = 0;
        Date dt = df.parse(date);
        for (Car car : cars) {
            if ("sold".equalsIgnoreCase(car.getStatus())) {
                for (SalesTransaction transaction : transactions) {
                    if (transaction.getItems().contains(car)) {
                        if (transaction.getTransactionDate().compareTo(dt) == 0) {
                            soldCars++;
                        }
                    }
                }
            }
        }
        return soldCars;
    }

    public static int countSoldCars(ArrayList<Car> cars, ArrayList<SalesTransaction> transactions, String range, String date) throws ParseException {
        if (range.equals("day")) {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            return countCars(cars, transactions, date, df);
        }
        else if (range.equals("month")) {
            SimpleDateFormat df = new SimpleDateFormat("MM/yyyy");
            return countCars(cars, transactions, date, df);
        }
        else if (range.equals("year")) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            return countCars(cars, transactions, date, df);
        }
        return 0;
    }

    public static int countSoldCars(ArrayList<Car> cars, ArrayList<SalesTransaction> transactions, Salesperson salesperson) throws ParseException {
        int soldCars = 0;
        for (Car car : cars) {
            if ("sold".equalsIgnoreCase(car.getStatus())) {
                for (SalesTransaction transaction : transactions) {
                    if (transaction.getItems().contains(car)) {
                        if (transaction.getSalespersonID().equals(salesperson.getUserID())) {
                            soldCars++;
                        }
                    }
                }
            }
        }
        return soldCars;
    }

    public static double calculateRevenue(ArrayList<SalesTransaction> transactions, ArrayList<Service> services) {
        double totalRevenue = 0;
        for (SalesTransaction transaction : transactions) {
            totalRevenue += transaction.getTotalAmount();
        }
        return totalRevenue;
    }

    public static double calculateServiceRevenue(ArrayList<Service> services, Mechanic mechanic) {
        double serviceRevenue = 0;
        for (Service service : services) {
            if (service.getMechanicID().equals(mechanic.getUserID())) {
                serviceRevenue += service.getServiceCost();
            }
        }
        return serviceRevenue;
    }

    public static double calculateServiceRevenue(ArrayList<Service> services) {
        double serviceRevenue = 0;
        for (Service service : services) {
            serviceRevenue += service.getServiceCost();
        }
        return serviceRevenue;
    }
}
