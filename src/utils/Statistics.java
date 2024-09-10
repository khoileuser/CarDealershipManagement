package utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import core.items.AutoPart;
import core.items.Car;
import core.Transaction;
import core.Service;

import core.items.Item;
import core.user.Mechanic;
import core.user.Salesperson;

public class Statistics {
    static Calendar cal1 = Calendar.getInstance();
    static Calendar cal2 = Calendar.getInstance();

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

    public static Map<String, Boolean> determineComparisonFlags(String date) throws ParseException {
        Map<String, Boolean> comparisonFlags = new HashMap<>();
        comparisonFlags.put("compareYear", false);
        comparisonFlags.put("compareMonth", false);
        comparisonFlags.put("compareDay", false);
    
        SimpleDateFormat df = determineRange(date);
    
        switch (df.toPattern()) {
            case "dd/MM/yyyy":
                comparisonFlags.put("compareYear", true);
                comparisonFlags.put("compareMonth", true);
                comparisonFlags.put("compareDay", true);
                break;
            case "MM/yyyy":
                comparisonFlags.put("compareYear", true);
                comparisonFlags.put("compareMonth", true);
                break;
            case "yyyy":
                comparisonFlags.put("compareYear", true);
                break;
        }
    
        return comparisonFlags;
    }

    public static boolean compareDates(Calendar cal1, Calendar cal2, String date) throws ParseException {
        Map<String, Boolean> comparisonFlags = determineComparisonFlags(date);

        boolean compareYear = comparisonFlags.get("compareYear");
        boolean compareMonth = comparisonFlags.get("compareMonth");
        boolean compareDay = comparisonFlags.get("compareDay");
    
        return (!compareYear || cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) &&
               (!compareMonth || cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) &&
               (!compareDay || cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH));
    }

    public static int countSoldCarsInPeriod(ArrayList<Transaction> transactions, String date) throws ParseException {
        SimpleDateFormat df = determineRange(date);
        cal1.setTime(df.parse(date));
    
        int soldCars = 0;
        for (Transaction transaction : transactions) {
            cal2.setTime(transaction.getTransactionDate());
            if (compareDates(cal1, cal2, date)) {
                for (Item i : transaction.getItems()) {
                    if (i instanceof Car) {
                        soldCars++;
                    }
                }
            }
        }
        return soldCars;
    }

    public static BigDecimal totalRevenueInPeriod(ArrayList<Service> services, ArrayList<Transaction> transactions, String date) throws ParseException {
        SimpleDateFormat df = determineRange(date);
        cal1.setTime(df.parse(date));

        BigDecimal revenue = BigDecimal.valueOf(0);
        for (Transaction transaction : transactions) {
            cal2.setTime(transaction.getTransactionDate());
            if (compareDates(cal1, cal2, date)) {
                revenue = revenue.add(transaction.getTotalAmount());
            }
        }
        for (Service service : services) {
            cal2.setTime(service.getServiceDate());
            if (compareDates(cal1, cal2, date)) {
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

    public static void listCarInAPeriod(ArrayList<Transaction> transactions, String date) throws ParseException {
        SimpleDateFormat df = determineRange(date);
        cal1.setTime(df.parse(date));

        System.out.println("\nCars sold in " + date + ":");
        int count = 1;
        for (Transaction transaction : transactions) {
            cal2.setTime(transaction.getTransactionDate());
            if (compareDates(cal1, cal2, date)) {
                for (Item i : transaction.getItems()) {
                    if (i instanceof Car) {
                        System.out.println(count + ". " + i.getSearchString());
                        count += 1;
                    }
                }
            }
        }
    }

    public static void listTransactionInAPeriod(ArrayList<Transaction> transactions, String date) throws ParseException {
        SimpleDateFormat df = determineRange(date);
        cal1.setTime(df.parse(date));

        System.out.println("\nSales Transactions created in " + date + ":");
        int count = 1;
        for (Transaction transaction : transactions) {
            cal2.setTime(transaction.getTransactionDate());
            if (compareDates(cal1, cal2, date)) {
                System.out.println(count + ". " + transaction.getSearchString());
                count += 1;
            }
        }
    }

    public static void listServiceInAPeriod(ArrayList<Service> services, String date) throws ParseException {
        SimpleDateFormat df = determineRange(date);
        cal1.setTime(df.parse(date));

        System.out.println("\nServices done in " + date + ":");
        int count = 1;
        for (Service service : services) {
            cal2.setTime(service.getServiceDate());
            if (compareDates(cal1, cal2, date)) {
                System.out.println(count + ". " + service.getSearchString());
                count += 1;
            }
        }
    }

    public static void listPartInAPeriod(ArrayList<Transaction> transactions, String date) throws ParseException {
        SimpleDateFormat df = determineRange(date);
        cal1.setTime(df.parse(date));

        System.out.println("\nAuto Parts sold in " + date + ":");
        int count = 1;
        for (Transaction transaction : transactions) {
            cal2.setTime(transaction.getTransactionDate());
            if (compareDates(cal1, cal2, date)) {
                for (Item i : transaction.getItems()) {
                    if (i instanceof AutoPart) {
                        System.out.println(count + ". " + i.getSearchString());
                        count += 1;
                    }
                }
            }
        }
    }
}
