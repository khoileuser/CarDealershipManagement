package utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import core.items.AutoPart;
import core.items.Car;
import core.Transaction;
import core.Service;

import core.items.Item;
import core.user.Mechanic;
import core.user.Salesperson;

public class Statistics {

    public static SimpleDateFormat determineRange(String date) throws ParseException {
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

        try {
            dayFormat.parse(date);
            return dayFormat;
        } catch (ParseException e) {
            try {
                monthFormat.parse(date);
                return monthFormat;
            } catch (ParseException d) {
                try {
                    yearFormat.parse(date);
                    return yearFormat;
                } catch (ParseException f) {
                    throw new ParseException("Invalid date.", 0);
                }
            }
        }
    }

    public static int countSoldCarsInPeriod(ArrayList<Transaction> transactions, Date dt) {
        int soldCars = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionDate().compareTo(dt) == 0) {
                for (Item i : transaction.getItems()) {
                    if (i instanceof Car) {
                        soldCars++;
                    }
                }
            }
        }
        return soldCars;
    }

    public static BigDecimal totalRevenueInPeriod(ArrayList<Service> services, ArrayList<Transaction> transactions, Date dt) throws ParseException {
        BigDecimal revenue = BigDecimal.valueOf(0);
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionDate().compareTo(dt) == 0) {
                revenue = revenue.add(transaction.getTotalAmount());
            }
        }
        for (Service service : services) {
            if (service.getServiceDate().compareTo(dt) == 0) {
                revenue = revenue.add(service.getServiceCost());
            }
        }
        return revenue;
    }

    public static BigDecimal revenueServiceByMechanic(ArrayList<Service> services, Mechanic mechanic) {
        BigDecimal serviceRevenue = BigDecimal.valueOf(0);
        for (Service service : services) {
            if (service.getMechanicID().equals(mechanic.getUserID())) {
                serviceRevenue = serviceRevenue.add(service.getServiceCost());
            }
        }
        return serviceRevenue;
    }

    public static BigDecimal revenueCarsBySalesperson(ArrayList<Transaction> transactions, Salesperson salesperson) {
        BigDecimal carRevenue = BigDecimal.valueOf(0);
        for (Transaction transaction : transactions) {
            if (transaction.getSalespersonID().equals(salesperson.getUserID())) {
                for (Item i : transaction.getItems()) {
                    if (i instanceof Car) {
                        carRevenue = carRevenue.add(i.getPrice());
                    }
                }
            }
        }
        return carRevenue;
    }
}
