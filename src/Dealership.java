import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import core.SalesTransaction;
import core.Service;
import interfaces.*;

import core.items.Car;
import core.items.AutoPart;
import core.user.*;

import operations.*;

import utils.FileHandler;
import utils.Authentication;

public class Dealership {
    private final String name;
    private final CarInterface carInterface;
    private final AutoPartInterface autoPartInterface;
    private final UserInterface userInterface;
    private final SalesTransactionInterface salesTransactionInterface;
    private final ServiceInterface serviceInterface;

    Scanner scanner = new Scanner(System.in);

    public Dealership(String name) throws IOException {
        this.name = name;
        // Initialize the system
        this.carInterface = new CarOperation();
        this.autoPartInterface = new AutoPartOperation();
        this.userInterface = new UserOperation();
        this.salesTransactionInterface = new SalesTransactionOperation();
        this.serviceInterface = new ServiceOperation();

        // Load data from files
        this.carInterface.setCarList(FileHandler.readObjectsFromFile("data/cars.obj"));
        this.autoPartInterface.setAutoPartList(FileHandler.readObjectsFromFile("data/parts.obj"));
        this.userInterface.setUserList(FileHandler.readObjectsFromFile("data/users.obj"));
        this.salesTransactionInterface.setSalesTransactionList(FileHandler.readObjectsFromFile("data/transactions.obj"));
        this.serviceInterface.setServiceList(FileHandler.readObjectsFromFile("data/services.obj"));
    }

    // Main method to start the system
    public void start() throws Exception {

//        saveData();

        System.out.println(userInterface.getAllUsers());
        System.out.println(carInterface.getAllCars());
        System.out.println(autoPartInterface.getAllAutoParts());
        System.out.println(serviceInterface.getAllServices());
        System.out.println(salesTransactionInterface.getAllSalesTransactions());

        System.out.println("Welcome to Auto136 Dealership Management System");
        System.out.println("Please login");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Authentication authentication = new Authentication(username, password);
        User loggedInUser = authentication.authenticate(userInterface.getAllUsers());

        if (loggedInUser != null) {
            System.out.println("Login successful. Welcome, " + loggedInUser.getFullName());
            switch (loggedInUser.getUserType()) {
                case MANAGER:
                    showManagerMenu();
                    break;
                case SALESPERSON:
                    showSalespersonMenu();
                    break;
                case MECHANIC:
                    showMechanicMenu();
                    break;
                default:
                    System.out.println("Invalid role. Exiting.");
            }
        } else {
            System.out.println("Login failed. Exiting the system.");
        }

        scanner.close();

        // Save data before exit
        saveData();
    }

    // Manager Menu
    private void showManagerMenu() {
        int choice;
        do {
            System.out.println("\nManager Menu:");
            System.out.println("1. Car Operations");
            System.out.println("2. Auto Part Operations");
            System.out.println("3. Service Operations");
            System.out.println("4. Sales Transaction Operations");
            System.out.println("5. User Operations");
            System.out.println("6. Perform Statistics");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Please try again.");
                scanner.next(); // consume the invalid token
                choice = -1; // default value that will not exit the loop
                continue;
            }

            switch (choice) {
                case 1:
                    showCarMenu();
                    break;
                case 2:
                    showAutoPartMenu();
                    break;
                case 3:
                    showServiceMenu();
                    break;
                case 4:
                    showSalesTransactionMenu();
                    break;
                case 5:
                    showUserMenu();
                    break;
                case 6:
                    showManagerStatisticMenu();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 0);
    }

    // Salesperson Menu
    private void showSalespersonMenu() {
        int choice;
        do {
            System.out.println("\nSalesperson Menu:");
            System.out.println("1. Car Operations");
            System.out.println("2. Perform Statistics");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Please try again.");
                scanner.next(); // consume the invalid token
                choice = -1; // default value that will not exit the loop
                continue;
            }

            switch (choice) {
                case 1:
                    showCarMenu();
                    break;
                case 2:
                    showEmployeeStatisticMenu();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 0);
    }

    // Mechanic Menu
    private void showMechanicMenu() {
        int choice;
        do {
            System.out.println("\nMechanic Menu:");
            System.out.println("1. Service Operations");
            System.out.println("2. Perform Statistics");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Please try again.");
                scanner.next(); // consume the invalid token
                choice = -1; // default value that will not exit the loop
                continue;
            }

            switch (choice) {
                case 1:
                    showServiceMenu();
                    break;
                case 2:
                    showManagerStatisticMenu();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 0);
    }

    private void showCarMenu() {
        int choice;
        do {
            System.out.println("\nCar Operations Menu:");
            System.out.println("1. Add a car");
            System.out.println("2. Select a car");
            System.out.println("3. Search for car");
            System.out.println("4. View all cars");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Please try again.");
                scanner.next(); // consume the invalid token
                choice = -1; // default value that will not exit the loop
                continue;
            }

            switch (choice) {
                case 1:
                    addCar();
                    break;
                case 2:
                    selectCarMenu();
                    break;
                case 3:
                    searchCarMenu();
                    break;
                case 4:
                    System.out.println("\nAll cars:");
                    List<Car> cars = carInterface.getAllCars();
                    int count = 1;
                    for (Car c : cars) {
                        System.out.println(count + ". " + c.getMake() + " " + c.getModel() + " " + c.getYear());
                        count += 1;
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }  while (choice != 0);
    }

    private void addCar() {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // consume any leftover newline character
        }

        System.out.print("Enter make: ");
        String make = getNextLine();

        System.out.print("Enter model: ");
        String model = getNextLine();

        System.out.print("Enter year: ");
        int year = getNextInt();

        System.out.print("Enter mileage: ");
        double mileage = getNextDouble();

        System.out.print("Enter color: ");
        String color = getNextLine();

        System.out.print("Enter price: ");
        BigDecimal price = getNextBigDecimal();

        System.out.println("1. Available");
        System.out.println("2. Sold");
        System.out.print("Choose status: ");
        String status; int choice;
        choice = getNextInt();
        scanner.nextLine();
        status = switch (choice) {
            case 1 -> "available";
            case 2 -> "sold";
            default -> {
                System.out.println("Invalid, set default to available");
                yield "available";
            }
        };

        System.out.print("Enter notes: ");
        String notes = scanner.nextLine();

        Car car = new Car(make, model, year, mileage, color, status, price, notes);
        carInterface.addCar(car);
    }

    private void selectCarMenu() {
        ArrayList<Car> cars = carInterface.getAllCars();
        listAndSelectCar(cars, "All cars:");
    }

    private String updateCarOperations(Car car, int carNumber) {
        int choice;
        do {
            String carString = carNumber + ". " + car.getMake() + " " + car.getModel() + " " + car.getYear();
            System.out.println("\n" + carString + "'s information");
            System.out.println("CarID: " + car.getCarID());
            System.out.println("Color: " + car.getColor());
            System.out.println("Mileage: " + car.getMileage());
            System.out.println("Price: " + car.getPrice());
            System.out.println("Status: " + car.getStatus());
            System.out.println("Notes: " + car.getNotes());
            if (!car.getServicesHistory().isEmpty()) {
                System.out.println("Services history: ");
                int serviceCount = 1;
                for (Service s : car.getServicesHistory()) {
                    System.out.println(serviceCount + ". " + s.getServiceType() + " | " + s.getNotes());
                    serviceCount += 1;
                }
            } else {
                System.out.println("Services history: Empty");
            }

            boolean sold;
            if (car.getStatus().equals("sold")) {
                sold = true;
            } else {
                sold = false;
            }
            System.out.println("\nUpdate Car Operations Menu:");
            if (sold) {
                System.out.println("1. Change car status to available");
            } else {
                System.out.println("1. Change car status to sold");
            }
            System.out.println("2. Update car");
            System.out.println("3. Remove car");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Please try again.");
                scanner.next(); // consume the invalid token
                choice = -1; // default value that will not exit the loop
                continue;
            }

            switch (choice) {
                case 1:
                    if (sold) {
                        car.setStatus("available");
                    } else {
                        car.setStatus("sold");
                    }
                    carInterface.updateCar(car);
                    break;
                case 2:
                    car = updateCarMenu(car);
                    break;
                case 3:
                    String choose;
                    do {
                        System.out.print("Are you sure you want to remove " + carString + " ? (Y/N) > ");
                        choose = scanner.next();
                        if (choose.toLowerCase().startsWith("y")) {
                            carInterface.removeCar(car);
                            break; // exit the loop after removing the car
                        } else if (!choose.toLowerCase().startsWith("n")) {
                            System.out.println("Invalid option. Please try again.");
                        }
                    } while (!choose.toLowerCase().startsWith("n"));
                    return "remove";
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again. in default");
                    break;
            }
        } while (choice != 0);
        return "ok";
    }

    private Car updateCarMenu(Car car) {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // consume any leftover newline character
        }

        System.out.println("\nCurrent make: " + car.getMake());
        System.out.print("Enter new make (enter to leave the same): ");
        String make = getNextLineEmpty(car.getMake());

        System.out.println("\nCurrent model: " + car.getModel());
        System.out.print("Enter new model (enter to leave the same): ");
        String model = getNextLineEmpty(car.getModel());

        System.out.println("\nCurrent year: " + car.getYear());
        System.out.print("Enter new year (enter to leave the same): ");
        int year = getNextIntEmpty(car.getYear());

        System.out.println("\nCurrent mileage: " + car.getMileage());
        System.out.print("Enter new mileage (enter to leave the same): ");
        double mileage = getNextDoubleEmpty(car.getMileage());

        System.out.println("\nCurrent color: " + car.getColor());
        System.out.print("Enter new color (enter to leave the same): ");
        String color = getNextLineEmpty(car.getColor());

        System.out.println("\nCurrent price: " + car.getPrice());
        System.out.print("Enter new price (enter to leave the same): ");
        BigDecimal price = getNextBigDecimalEmpty(car.getPrice());

        System.out.println("\nCurrent status: " + car.getStatus());
        System.out.println("1. Available");
        System.out.println("2. Sold");
        System.out.println("0. Same as current");
        System.out.print("Choose new status: ");
        String status; int choice;
        choice = getNextInt();
        scanner.nextLine();
        status = switch (choice) {
            case 1 -> "available";
            case 2 -> "sold";
            case 0 -> car.getStatus();
            default -> {
                System.out.println("Invalid, set default to available");
                yield "available";
            }
        };

        System.out.println("\nCurrent notes: " + car.getNotes());
        System.out.print("Enter new notes (enter to leave the same): ");
        String notes = getNextLineEmpty(car.getNotes());

        if (!car.getServicesHistory().isEmpty()) {
            System.out.println("\nCurrent services history: ");
            int serviceCount = 1;
            for (Service s : car.getServicesHistory()) {
                System.out.println(serviceCount + ". " + s.getServiceType() + " | " + s.getNotes());
                serviceCount += 1;
            }
        } else {
            System.out.println("\nCurrent services history: Empty");
        }


        Car updatedCar = new Car(make, model, year, mileage, color, status, price, notes);
        updatedCar.setCarID(car.getCarID());
        carInterface.updateCar(updatedCar);
        return updatedCar;
    }

    private void searchCarMenu() {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // consume any leftover newline character
        }

        System.out.print("Enter search input: ");
        String searchInput = scanner.nextLine();

        boolean matchContain = false;
        Car match = null;
        int minDistance = Integer.MAX_VALUE;
        ArrayList<Car> highSim = new ArrayList<>();

        for (Car c : carInterface.getAllCars()) {
            String carString = c.getMake() + " " + c.getModel() + " " + c.getYear();
            if (carString.toLowerCase().contains(searchInput)) {
                match = c;
                matchContain = true;
                break;
            }
            int distance = levenshteinDistance(searchInput, carString);
            if (distance <= 20) {
                highSim.add(c);
            }
            if (distance < minDistance) {
                minDistance = distance;
                match = c;
            }
        }

        if (matchContain) {
            updateCarOperations(match, 1);
        } else {
            if (!highSim.isEmpty()) {
                listAndSelectCar(highSim, "Relevant search:");
            } else {
                System.out.println(searchInput + " is might not in the system.");
            }
        }
    }

    public void listAndSelectCar(ArrayList<Car> cars, String message) {
        int choice;
        do {
            System.out.println("\n" + message);
            int count = 1;
            for (Car c : cars) {
                System.out.println(count + ". " + c.getMake() + " " + c.getModel() + " " + c.getYear());
                count += 1;
            }
            System.out.println("0. Back");
            System.out.print("Select car: ");

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Please try again.");
                scanner.next(); // consume the invalid token
                choice = -1; // default value that will not exit the loop
                continue;
            }

            if (choice > 0 && choice <= cars.size()) {
                Car selectedCar = cars.get(choice - 1);
                String result = updateCarOperations(selectedCar, choice);
                if (result.equals("remove")) {
                    return;
                }
            } else if (choice == 0) {} else {
                System.out.println("Invalid option. Please try again.");
            }
        }  while (choice != 0);
    }

    public int levenshteinDistance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
                        a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

    private void showAutoPartMenu() {
        int choice;
        do {
            System.out.println("\nAuto Part Operations Menu:");
            System.out.println("1. Add an auto part");
            System.out.println("2. Select an auto part");
            System.out.println("3. Search for auto part");
            System.out.println("4. View all auto parts");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Please try again.");
                scanner.next(); // consume the invalid token
                choice = -1; // default value that will not exit the loop
                continue;
            }

            switch (choice) {
                case 1:
                    addPart();
                    break;
                case 2:
                    System.out.println("List all auto part then select auto part");
                    break;
                case 3:
                    System.out.println("Search for auto part");
                    break;
                case 4:
                    List<AutoPart> parts = autoPartInterface.getAllAutoParts();
                    for (AutoPart p : parts) {
                        System.out.println(p);
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }  while (choice != 0);
    }

    private void addPart() {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // consume any leftover newline character
        }

        System.out.print("Enter part name: ");
        String partName = scanner.nextLine();

        System.out.print("Enter manufacturer: ");
        String manufacturer = scanner.nextLine();

        System.out.print("Enter part: ");
        int year = getNextInt();

        System.out.print("Enter mileage: ");
        double mileage = getNextDouble();

        System.out.print("Enter color: ");
        String color = scanner.nextLine();

        System.out.print("Enter price: ");
        BigDecimal price = getNextBigDecimal();

        System.out.println("1. Available");
        System.out.println("2. Sold");
        System.out.print("Choose status: ");
        String status; int choice;
        choice = getNextInt();
        scanner.nextLine();
        status = switch (choice) {
            case 1 -> "available";
            case 2 -> "sold";
            default -> {
                System.out.println("Invalid, set default to available");
                yield "available";
            }
        };

        System.out.print("Enter notes: ");
        String notes = scanner.nextLine();

//        AutoPart autoPart = new AutoPart(make, model, year, mileage, color, status, price, notes);
//        autoPartInterface.addAutoPart(autoPart);
    }

    private void showServiceMenu() {
        int choice;
        do {
            System.out.println("\nService Operations Menu:");
            System.out.println("1. Add a service");
            System.out.println("2. Select a service");
            System.out.println("3. Search for service");
            System.out.println("4. View all services");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Please try again.");
                scanner.next(); // consume the invalid token
                choice = -1; // default value that will not exit the loop
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("add service");
                    break;
                case 2:
                    System.out.println("List all services then select service");
                    break;
                case 3:
                    List<Service> services = serviceInterface.getAllServices();
                    for (Service s : services) {
                        System.out.println(s);
                    }
                    break;
                case 4:
                    System.out.println("View all services");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }  while (choice != 0);
    }

    private void showSalesTransactionMenu() {
        int choice;
        do {
            System.out.println("\nSales Transaction Operations Menu:");
            System.out.println("1. Add a sales transaction");
            System.out.println("2. Select a sales transaction");
            System.out.println("3. Search for sales transaction");
            System.out.println("4. View all sales transactions");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Please try again.");
                scanner.next(); // consume the invalid token
                choice = -1; // default value that will not exit the loop
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("add sales transaction");
                    break;
                case 2:
                    System.out.println("List all sales transactions then select sales transaction");
                    break;
                case 3:
                    System.out.println("Search for sales transaction");
                    break;
                case 4:
                    List<SalesTransaction> transactions = salesTransactionInterface.getAllSalesTransactions();
                    for (SalesTransaction t : transactions) {
                        System.out.println(t);
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }  while (choice != 0);
    }

    private void showUserMenu() {
        int choice;
        do {
            System.out.println("\nUser Operations Menu:");
            System.out.println("1. Add a user");
            System.out.println("2. Select a user");
            System.out.println("3. Search for user");
            System.out.println("4. View all users");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Please try again.");
                scanner.next(); // consume the invalid token
                choice = -1; // default value that will not exit the loop
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("add user");
                    break;
                case 2:
                    System.out.println("List all users then select user");
                    break;
                case 3:
                    System.out.println("Search for user");
                    break;
                case 4:
                    System.out.println("View all users");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }  while (choice != 0);
    }

    public void showManagerStatisticMenu() {
        int choice;
        do {
            System.out.println("\nStatistic Menu:");
            System.out.println("1. Number of cars sold");
            System.out.println("2. Revenue");
            System.out.println("3. Revenue of services done by a mechanic");
            System.out.println("4. Revenue of cars sold by a salesperson");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Please try again.");
                scanner.next(); // consume the invalid token
                choice = -1; // default value that will not exit the loop
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("");
                    break;
                case 2:
                    System.out.println("");
                    break;
                case 3:
                    System.out.println("");
                    break;
                case 4:
                    System.out.println("");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }  while (choice != 0);
    }

    public void showEmployeeStatisticMenu() {
        int choice;
        do {
            System.out.println("\nStatistic Menu:");
            System.out.println("1. Revenue");
            System.out.println("2. Car/services done");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Please try again.");
                scanner.next(); // consume the invalid token
                choice = -1; // default value that will not exit the loop
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("");
                    break;
                case 2:
                    System.out.println("");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }  while (choice != 0);
    }

    private void performStatistics() {
        // Implement statistics methods like revenue calculation, etc.
    }

    private String getNextLine() {
        while (true) {
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.print("Field cannot be empty. Please try again: ");
                continue;
            }
            return input;
        }
    }

    private int getNextInt() {
        while (true) {
            String stringInput = scanner.nextLine();
            if (stringInput.isEmpty()) {
                System.out.print("Field cannot be empty. Please try again: ");
                continue;
            }
            try {
                return Integer.parseInt(stringInput);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter an integer: ");
            }
        }
    }

    private double getNextDouble() {
        while (true) {
            String stringInput = scanner.nextLine();
            if (stringInput.isEmpty()) {
                System.out.print("Field cannot be empty. Please try again: ");
                continue;
            }
            try {
                return Double.parseDouble(stringInput);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a double: ");
            }
        }
    }

    private BigDecimal getNextBigDecimal() {
        while (true) {
            String stringInput = scanner.nextLine();
            if (stringInput.isEmpty()) {
                System.out.print("Field cannot be empty. Please try again: ");
                continue;
            }
            try {
                return BigDecimal.valueOf(Double.parseDouble(stringInput));
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a decimal: ");
            }
        }
    }

    private String getNextLineEmpty(String defaultValue) {
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            return defaultValue;
        }
        return input;
    }

    private int getNextIntEmpty(int defaultValue) {
        while (true) {
            String stringInput = scanner.nextLine();
            if (stringInput.isEmpty()) {
                return defaultValue;
            } else {
                try {
                    return Integer.parseInt(stringInput);
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Please enter a valid integer or leave it empty: ");
                }
            }
        }
    }

    private double getNextDoubleEmpty(double defaultValue) {
        while (true) {
            String stringInput = scanner.nextLine();
            if (stringInput.isEmpty()) {
                return defaultValue;
            } else {
                try {
                    return Double.parseDouble(stringInput);
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Please enter a valid double or leave it empty: ");
                }
            }
        }
    }

    private BigDecimal getNextBigDecimalEmpty(BigDecimal defaultValue) {
        while (true) {
            String stringInput = scanner.nextLine();
            if (stringInput.isEmpty()) {
                return defaultValue;
            } else {
                try {
                    return BigDecimal.valueOf(Double.parseDouble(stringInput));
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Please enter a valid decimal or leave it empty: ");
                }
            }
        }
    }

    private void welcomeScreen() {
        System.out.println("COSC2081 GROUP ASSIGNMENT\n" +
                name + " CAR DEALERSHIP MANAGEMENT SYSTEM\n" +
                "Instructor: Mr. Minh Vu & Mr. Dung Nguyen\n" +
                "Group: Confuse Group\n" +
                "s3975162 Le Nguyen Khoi\n" +
                "s39 Tran Tuan Anh\n" +
                "s39 Nguyen Vu Duy\n" +
                "s39 Le Minh Tri" +
                "\nUsers: " + userInterface.getAllUsers().size() +
                "\nCars: " + carInterface.getAllCars().size() +
                "\nAuto Parts: " + autoPartInterface.getAllAutoParts().size() +
                "\nServices: " + serviceInterface.getAllServices().size() +
                "\nSales Transactions: " + salesTransactionInterface.getAllSalesTransactions().size()
        );
    }

    // Save data to files before exit
    private void saveData() throws IOException {
        FileHandler.writeObjectsToFile(carInterface.getAllCars(), "data/cars.obj");
        FileHandler.writeObjectsToFile(autoPartInterface.getAllAutoParts(), "data/parts.obj");
        FileHandler.writeObjectsToFile(userInterface.getAllUsers(), "data/users.obj");
        FileHandler.writeObjectsToFile(salesTransactionInterface.getAllSalesTransactions(), "data/transactions.obj");
        FileHandler.writeObjectsToFile(serviceInterface.getAllServices(), "data/services.obj");
        System.out.println("Data saved successfully.");
    }
}
