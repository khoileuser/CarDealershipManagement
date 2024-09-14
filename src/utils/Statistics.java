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

    public static SimpleDateFormat getDateFormat(String dateType) throws ParseException {
        if (dateType.equals("day")) {
            return new SimpleDateFormat("dd/MM/yyyy");
        }  else if (dateType.equals("week")) {
            return new SimpleDateFormat("dd/MM/yyyy");
        } else if (dateType.equals("month")) {
            return new SimpleDateFormat("MM/yyyy");
        } else if (dateType.equals("year")) {
            return new SimpleDateFormat("yyyy");
        } else {
            throw new ParseException("Invalid dateType.", 0);
        }
    }

    public static boolean compareDates(String dateType, Date compareDate) {
        switch (dateType) {
            case "day":
                return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                       cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
            case "week":
                Calendar startOfWeek = (Calendar) cal1.clone();
                startOfWeek.set(Calendar.DAY_OF_WEEK, startOfWeek.getFirstDayOfWeek());
                Calendar endOfWeek = (Calendar) startOfWeek.clone();
                endOfWeek.add(Calendar.DAY_OF_WEEK, 6);
                return compareDate.compareTo(startOfWeek.getTime()) >= 0 &&
                    compareDate.compareTo(endOfWeek.getTime()) <= 0;
            case "month":
                return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                       cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
            default:
                return false;
        }
    }

    public static int countSoldCarsInPeriod(ArrayList<Transaction> transactions, String date, String dateType) throws ParseException {
        SimpleDateFormat df = getDateFormat(dateType);
        cal1.setTime(df.parse(date));
    
        int soldCars = 0;
        for (Transaction transaction : transactions) {
            Date transactionDate = transaction.getTransactionDate();
            cal2.setTime(transactionDate);
            if (compareDates(dateType, transactionDate)) {
                for (Item i : transaction.getItems()) {
                    if (i instanceof Car) {
                        soldCars++;
                    }
                }
            }
        }
        return soldCars;
    }

    public static BigDecimal totalRevenueInPeriod(ArrayList<Service> services, ArrayList<Transaction> transactions, String date, String dateType) throws ParseException {
        SimpleDateFormat df = getDateFormat(dateType);
        cal1.setTime(df.parse(date));

        BigDecimal revenue = BigDecimal.valueOf(0);
        for (Transaction transaction : transactions) {
            Date transactionDate = transaction.getTransactionDate();
            cal2.setTime(transactionDate);
            if (compareDates(dateType, transactionDate)) {
                System.out.println(transaction.getTotalAmount());
                revenue = revenue.add(transaction.getTotalAmount());
            }
        }
        for (Service service : services) {
            Date serviceDate = service.getServiceDate();
            cal2.setTime(serviceDate);
            if (compareDates(dateType, serviceDate)) {
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

    public static void listCarInAPeriod(ArrayList<Transaction> transactions, String date, String dateType) throws ParseException {
        SimpleDateFormat df = getDateFormat(dateType);

        cal1.setTime(df.parse(date));

        if (dateType.equals("week")) {
            System.out.println("\nCars sold in the week starts from " + date + ":");
        } else {
            System.out.println("\nCars sold in " + date + ":");
        }
        int count = 1;
        for (Transaction transaction : transactions) {
            Date transactionDate = transaction.getTransactionDate();
            cal2.setTime(transactionDate);
            if (compareDates(dateType, transactionDate)) {
                for (Item i : transaction.getItems()) {
                    if (i instanceof Car) {
                        System.out.println(count + ". " + i.getSearchString());
                        count += 1;
                    }
                }
            }
        }
    }

    public static void listTransactionInAPeriod(ArrayList<Transaction> transactions, String date, String dateType) throws ParseException {
        SimpleDateFormat df = getDateFormat(dateType);
        cal1.setTime(df.parse(date));

        if (dateType.equals("week")) {
            System.out.println("\nSales Transactions created in the week starts from " + date + ":");
        } else {
            System.out.println("\nSales Transactions created in " + date + ":");
        }
        int count = 1;
        for (Transaction transaction : transactions) {
            Date transactionDate = transaction.getTransactionDate();
            cal2.setTime(transactionDate);
            if (compareDates(dateType, transactionDate)) {
                System.out.println(count + ". " + transaction.getSearchString());
                count += 1;
            }
        }
    }

    public static void listServiceInAPeriod(ArrayList<Service> services, String date, String dateType) throws ParseException {
        SimpleDateFormat df = getDateFormat(dateType);
        cal1.setTime(df.parse(date));

        if (dateType.equals("week")) {
            System.out.println("\nServices done in the week starts from " + date + ":");
        } else {
            System.out.println("\nServices done in " + date + ":");
        }
        int count = 1;
        for (Service service : services) {
            Date serviceDate = service.getServiceDate();
            cal2.setTime(serviceDate);
            if (compareDates(dateType, serviceDate)) {
                System.out.println(count + ". " + service.getSearchString());
                count += 1;
            }
        }
    }

    public static void listPartInAPeriod(ArrayList<Service> services, ArrayList<Transaction> transactions, String date, String dateType) throws ParseException {
        SimpleDateFormat df = getDateFormat(dateType);
        cal1.setTime(df.parse(date));

        if (dateType.equals("week")) {
            System.out.println("\nAuto Parts sold in the week starts from " + date + ":");
        } else {
            System.out.println("\nAuto Parts sold in " + date + ":");
        }
        int count = 1;

        System.out.println("\nAuto Parts sold in transactions:");
        for (Transaction transaction : transactions) {
            Date transactionDate = transaction.getTransactionDate();
            cal2.setTime(transactionDate);
            if (compareDates(dateType, transactionDate)) {
                for (Item i : transaction.getItems()) {
                    if (i instanceof AutoPart) {
                        System.out.println(count + ". " + i.getSearchString());
                        count += 1;
                    }
                }
            }
        }

        System.out.println("\nAuto Parts sold in services:");
        for (Service service : services) {
            Date serviceDate = service.getServiceDate();
            cal2.setTime(serviceDate);
            if (compareDates(dateType, serviceDate)) {
                for (AutoPart i : service.getReplacedParts()) {
                    System.out.println(count + ". " + i.getSearchString());
                    count += 1;
                }
            }
        }
    }

    public static String numParse(BigDecimal num) {
        return String.format("%,.0f", num);
    }
}
