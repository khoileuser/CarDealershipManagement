import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.Function;
import java.util.*;

import core.*;
import core.items.*;
import core.user.*;
import interfaces.*;
import operations.*;

import utils.FileHandler;
import utils.Authentication;

public class Dealership {
    private final String name;
    private final CarInterface carInterface;
    private final AutoPartInterface autoPartInterface;
    private final UserInterface userInterface;
    private final TransactionInterface transactionInterface;
    private final ServiceInterface serviceInterface;

    Scanner scanner = new Scanner(System.in);

    public Dealership(String name) throws IOException {
        this.name = name;
        // Initialize the system
        this.carInterface = new CarOperation();
        this.autoPartInterface = new AutoPartOperation();
        this.userInterface = new UserOperation();
        this.transactionInterface = new TransactionOperation();
        this.serviceInterface = new ServiceOperation();

        // Load data from files
        this.carInterface.setCarList(FileHandler.readObjectsFromFile("data/cars.obj"));
        this.autoPartInterface.setAutoPartList(FileHandler.readObjectsFromFile("data/parts.obj"));
        this.userInterface.setUserList(FileHandler.readObjectsFromFile("data/users.obj"));
        this.transactionInterface.setTransactionList(FileHandler.readObjectsFromFile("data/transactions.obj"));
        this.serviceInterface.setServiceList(FileHandler.readObjectsFromFile("data/services.obj"));
    }

    // Main method to start the system
    public void start() throws Exception {

//        saveData();

        System.out.println(userInterface.getAllUsers());
        System.out.println(carInterface.getAllCars());
        System.out.println(autoPartInterface.getAllAutoParts());
        System.out.println(serviceInterface.getAllServices());
        System.out.println(transactionInterface.getAllTransactions());

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
                    showTransactionMenu();
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
        ArrayList<Car> cars;
        do {
            cars = carInterface.getAllCars();
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
                    listAndSelect(cars, "All cars:", this::updateEntityOperations);
                    break;
                case 3:
                    searchMenu(cars, this::updateEntityOperations);
                    break;
                case 4:
                    System.out.println("\nAll cars:");
                    int count = 1;
                    for (Car c : cars) {
                        System.out.println(count + ". " + c.getSearchString());
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

    private String updateCarOperations(Car car) {
        int choice;
        do {
            String carString = "Car: " + car.getMake() + " " + car.getModel() + " " + car.getYear();
            System.out.println("\n" + carString + "'s information");
            System.out.println("Car ID: " + car.getCarID());
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
            sold = car.getStatus().equals("sold");
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
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (choice != 0);
        return "ok";
    }

    private Car updateCarMenu(Car car) {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // consume any leftover newline character
        }

        String make = car.getMake();
        System.out.println("\nCurrent make: " + make);
        System.out.print("Enter new make (enter to leave the same): ");
        make = getNextLineEmpty(make);

        String model = car.getModel();
        System.out.println("\nCurrent model: " + model);
        System.out.print("Enter new model (enter to leave the same): ");
        model = getNextLineEmpty(model);

        int year = car.getYear();
        System.out.println("\nCurrent year: " + year);
        System.out.print("Enter new year (enter to leave the same): ");
        year = getNextIntEmpty(year);

        double mileage = car.getMileage();
        System.out.println("\nCurrent mileage: " + mileage);
        System.out.print("Enter new mileage (enter to leave the same): ");
        mileage = getNextDoubleEmpty(mileage);

        String color = car.getColor();
        System.out.println("\nCurrent color: " + color);
        System.out.print("Enter new color (enter to leave the same): ");
        color = getNextLineEmpty(color);

        BigDecimal price = car.getPrice();
        System.out.println("\nCurrent price: " + price);
        System.out.print("Enter new price (enter to leave the same): ");
        price = getNextBigDecimalEmpty(price);

        String status = car.getStatus();
        System.out.println("\nCurrent status: " + status);
        System.out.println("1. Available");
        System.out.println("2. Sold");
        System.out.println("0. Same as current");
        System.out.print("Choose new status: ");
        int choice;
        choice = getNextInt();
        scanner.nextLine();
        status = switch (choice) {
            case 1 -> "available";
            case 2 -> "sold";
            case 0 -> status;
            default -> {
                System.out.println("Invalid, set default to available");
                yield "available";
            }
        };

        String notes = car.getNotes();
        System.out.println("\nCurrent notes: " + notes);
        System.out.print("Enter new notes (enter to leave the same): ");
        notes = getNextLineEmpty(notes);

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

    private void showAutoPartMenu() {
        int choice;
        ArrayList<AutoPart> autoParts;
        do {
            autoParts = autoPartInterface.getAllAutoParts();
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
                    listAndSelect(autoParts, "All auto parts:", this::updateEntityOperations);
                    break;
                case 3:
                    searchMenu(autoParts, this::updateEntityOperations);
                    break;
                case 4:
                    System.out.println("\nAll auto parts:");
                    int count = 1;
                    for (AutoPart p : autoParts) {
                        System.out.println(count + ". " + p.getSearchString());
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

    private void addPart() {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // consume any leftover newline character
        }

        System.out.print("Enter part name: ");
        String partName = getNextLine();

        System.out.print("Enter manufacturer: ");
        String manufacturer = getNextLine();

        System.out.print("Enter part number: ");
        String partNumber = getNextLine();

        System.out.print("Enter condition: ");
        String condition = getNextLine();

        System.out.print("Enter warranty: ");
        String warranty = getNextLine();

        System.out.print("Enter cost: ");
        BigDecimal cost = getNextBigDecimal();

        System.out.print("Enter notes: ");
        String notes = scanner.nextLine();

        AutoPart autoPart = new AutoPart(partName, manufacturer, partNumber, condition, warranty, cost, notes);
        autoPartInterface.addAutoPart(autoPart);
    }

    private String updatePartOperations(AutoPart part) {
        int choice;
        do {
            String partString =  "Part: " + part.getManufacturer() + " " + part.getPartName() + " (" + part.getPartNumber() + ")";
            System.out.println("\n" + partString + "'s information");
            System.out.println("Part ID: " + part.getPartID());
            System.out.println("Part Name: " + part.getPartName());
            System.out.println("Manufacturer: " + part.getManufacturer());
            System.out.println("Part Number: " + part.getPartNumber());
            System.out.println("Condition: " + part.getCondition());
            System.out.println("Warranty: " + part.getWarranty());
            System.out.println("Cost: " + part.getCost());
            System.out.println("Notes: " + part.getNotes());

            System.out.println("\nUpdate Auto Part Operations Menu:");
            System.out.println("1. Update auto part");
            System.out.println("2. Remove auto part");
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
                    part = updatePartMenu(part);
                    break;
                case 2:
                    String choose;
                    do {
                        System.out.print("Are you sure you want to remove " + partString + " ? (Y/N) > ");
                        choose = scanner.next();
                        if (choose.toLowerCase().startsWith("y")) {
                            autoPartInterface.removeAutoPart(part);
                            break;
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

    private AutoPart updatePartMenu(AutoPart part) {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // consume any leftover newline character
        }

        String partName = part.getPartName();
        System.out.println("\nCurrent part name: " + partName);
        System.out.print("Enter new part name (enter to leave the same): ");
        partName = getNextLineEmpty(partName);

        String manufacturer = part.getManufacturer();
        System.out.println("\nCurrent manufacturer: " + manufacturer);
        System.out.print("Enter new manufacturer (enter to leave the same): ");
        manufacturer = getNextLineEmpty(manufacturer);

        String partNumber = part.getPartNumber();
        System.out.println("\nCurrent part number: " + partNumber);
        System.out.print("Enter new part number (enter to leave the same): ");
        partNumber = getNextLineEmpty(partNumber);

        String condition = part.getCondition();
        System.out.println("\nCurrent condition: " + condition);
        System.out.print("Enter new condition (enter to leave the same): ");
        condition = getNextLineEmpty(condition);

        String warranty = part.getWarranty();
        System.out.println("\nCurrent warranty: " + warranty);
        System.out.print("Enter new warranty (enter to leave the same): ");
        warranty = getNextLineEmpty(warranty);

        BigDecimal cost = part.getCost();
        System.out.println("\nCurrent cost: " + cost);
        System.out.print("Enter new cost (enter to leave the same): ");
        cost = getNextBigDecimalEmpty(cost);

        String notes = part.getNotes();
        System.out.println("\nCurrent notes: " + notes);
        System.out.print("Enter new notes (enter to leave the same): ");
        notes = getNextLineEmpty(notes);

        AutoPart updatedPart = new AutoPart(partName, manufacturer, partNumber, condition, warranty, cost, notes);
        updatedPart.setPartID(part.getPartID());
        autoPartInterface.updateAutoPart(updatedPart);
        return updatedPart;
    }

    private void showServiceMenu() {
        int choice;
        ArrayList<Service> services;
        do {
            services = serviceInterface.getAllServices();
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
                    addService();
                    break;
                case 2:
                    listAndSelect(services, "All services:", this::updateEntityOperations);
                    break;
                case 3:
                    searchMenu(services, this::updateEntityOperations);
                    break;
                case 4:
                    System.out.println("\nAll services:");
                    int count = 1;
                    for (Service s : services) {
                        System.out.println(count + ". " + s.getSearchString());
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

    private void addService() {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // consume any leftover newline character
        }

        ArrayList<Mechanic> mechanics = userInterface.getAllMechanics();
        Mechanic mechanic = (Mechanic) selectChoiceOrSearchString(mechanics, "Mechanic");

        ArrayList<Client> clients = userInterface.getAllClients();
        Client client = (Client) selectChoiceOrSearchString(clients, "Client");

        System.out.print("Enter service type: ");
        String serviceType = getNextLine();

        System.out.print("Enter service cost: ");
        BigDecimal serviceCost = getNextBigDecimal();

        System.out.print("Enter notes: ");
        String notes = scanner.nextLine();

//        add replaced parts here

        Service service = new Service(mechanic.getUserID(), client.getUserID(), serviceType, serviceCost, notes);
        serviceInterface.addService(service);
    }

    private String updateServiceOperations(Service service) {
        int choice;
        do {
            String serviceString =  "Service: " + service.getServiceType();
            System.out.println("\n" + serviceString + "'s information");
            System.out.println("Service ID: " + service.getServiceID());
            System.out.println("Client ID: " + service.getClientID());
            System.out.println("Mechanic ID: " + service.getMechanicID());
            System.out.println("Service Type: " + service.getServiceType());
            System.out.println("Replaced Parts: " + service.getStringParts());
            System.out.println("Service Cost: " + service.getServiceCost());
            System.out.println("Notes: " + service.getNotes());

            System.out.println("\nUpdate Service Operations Menu:");
            System.out.println("1. Update service");
            System.out.println("2. Remove service");
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
                    service = updateServiceMenu(service);
                    break;
                case 2:
                    String choose;
                    do {
                        System.out.print("Are you sure you want to remove " + serviceString + " ? (Y/N) > ");
                        choose = scanner.next();
                        if (choose.toLowerCase().startsWith("y")) {
                            serviceInterface.removeService(service);
                            carInterface.removeServiceFromCars(service);
                            break;
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

    private Service updateServiceMenu(Service service) {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // consume any leftover newline character
        }

        String serviceType = service.getServiceType();
        System.out.println("\nCurrent service type: " + serviceType);
        System.out.print("Enter new service type (enter to leave the same): ");
        serviceType = getNextLineEmpty(serviceType);

        BigDecimal serviceCost = service.getServiceCost();
        System.out.println("\nCurrent service cost: " + serviceCost);
        System.out.print("Enter new service cost (enter to leave the same): ");
        serviceCost = getNextBigDecimalEmpty(serviceCost);

        String notes = service.getNotes();
        System.out.println("\nCurrent notes: " + notes);
        System.out.print("Enter new notes (enter to leave the same): ");
        notes = getNextLineEmpty(notes);

//        add / remove replaced parts

        Service updatedService = new Service(service.getClientID(), service.getMechanicID(), serviceType, serviceCost, notes);
        updatedService.setServiceID(service.getServiceID());
        serviceInterface.updateService(updatedService);
        return updatedService;
    }

    private void showTransactionMenu() {
        int choice;
        ArrayList<Transaction> transactions;
        do {
            transactions = transactionInterface.getAllTransactions();
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
                    addTransaction();
                    break;
                case 2:
                    listAndSelect(transactions, "All sales transactions:", this::updateEntityOperations);
                    break;
                case 3:
                    searchMenu(transactions, this::updateEntityOperations);
                    break;
                case 4:
                    System.out.println("\nAll sales transactions:");
                    int count = 1;
                    for (Transaction t : transactions) {
                        System.out.println(count + ". " + t.getSearchString());
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

    private void addTransaction() {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // consume any leftover newline character
        }

        ArrayList<Salesperson> salespersons = userInterface.getAllSalespersons();
        Salesperson salesperson = (Salesperson) selectChoiceOrSearchString(salespersons, "Salesperson");

        ArrayList<Client> clients = userInterface.getAllClients();
        Client client = (Client) selectChoiceOrSearchString(clients, "Client");

        System.out.print("Enter notes: ");
        String notes = scanner.nextLine();

//        add items here

        Transaction transaction = new Transaction(client.getUserID(), salesperson.getUserID(), notes);
        transactionInterface.addTransaction(transaction);
    }

    private String updateTransactionOperations(Transaction transaction) {
        int choice;
        do {
            String transactionString =  "Sales Transaction at " + transaction.getStringTransactionDate();
            System.out.println("\n" + transactionString + "'s information");
            System.out.println("Transaction ID: " + transaction.getTransactionID());
            System.out.println("Client ID: " + transaction.getClientID());
            System.out.println("Mechanic ID: " + transaction.getSalespersonID());
            System.out.println("Items: " + transaction.getStringItems());
            System.out.println("Discount Percentage: " + transaction.getDiscountPercentage());
            System.out.println("Total Amount: " + transaction.getTotalAmount());
            System.out.println("Notes: " + transaction.getNotes());

            System.out.println("\nUpdate Sales Transaction Operations Menu:");
            System.out.println("1. Update sales transaction");
            System.out.println("2. Remove sales transaction");
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
                    transaction = updateTransactionMenu(transaction);
                    break;
                case 2:
                    String choose;
                    do {
                        System.out.print("Are you sure you want to remove " + transactionString + " ? (Y/N) > ");
                        choose = scanner.next();
                        if (choose.toLowerCase().startsWith("y")) {
                            transactionInterface.removeTransaction(transaction);
                            break;
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

    private Transaction updateTransactionMenu(Transaction transaction) {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // consume any leftover newline character
        }

//        edit items here

        String notes = transaction.getNotes();
        System.out.println("\nCurrent notes: " + notes);
        System.out.print("Enter new notes (enter to leave the same): ");
        notes = getNextLineEmpty(notes);

        Transaction updatedTransaction = new Transaction(transaction.getClientID(), transaction.getSalespersonID(), notes);
        updatedTransaction.setTransactionID(transaction.getTransactionID());
        transactionInterface.updateTransaction(updatedTransaction);
        return updatedTransaction;
    }

    private void showUserMenu() {
        int choice;
        ArrayList<User> users;
        do {
            users = userInterface.getAllUsers();
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
                    addUser();
                    break;
                case 2:
                    listAndSelect(users, "All users:", this::updateEntityOperations);
                    break;
                case 3:
                    searchMenu(users, this::updateEntityOperations);
                    break;
                case 4:
                    System.out.println("\nAll users:");
                    int count = 1;
                    for (User u : users) {
                        System.out.println(count + ". " + u.getSearchString());
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

    private void addUser() {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // consume any leftover newline character
        }

        System.out.print("Enter full name: ");
        String fullName = getNextLine();

        System.out.print("Enter date of birth: ");
        String dateOfBirth;
        while (true) {
            try {
                dateOfBirth = getNextLine();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                df.parse(dateOfBirth);
                break;
            } catch (ParseException e) {
                System.out.println("Date of birth need to be in format: dd/MM/yyyy (Ex: 27/06/2007)");
            }
        }

        System.out.print("Enter address: ");
        String address = getNextLine();

        System.out.print("Enter phone number: ");
        String phoneNumber = getNextLine();

        System.out.print("Enter email: ");
        String email = getNextLine();

        UserType userType = null;
        int choice;
        do {
            System.out.println("\nSelect user type:");
            System.out.println("1. Salesperson");
            System.out.println("2. Mechanic");
            System.out.println("3. Client");
            System.out.println("0. Same as current");
            System.out.print("Choose user type: ");
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
                    userType = UserType.SALESPERSON;
                    break;
                case 2:
                    userType = UserType.MECHANIC;
                    break;
                case 3:
                    userType = UserType.CLIENT;
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (choice != 0);

        String username = "";
        String password = "";
        if (userType == UserType.SALESPERSON || userType == UserType.MECHANIC) {
            System.out.println("You chose " + userType + ", please enter username and password");

            System.out.print("\nEnter new username: ");
            username = getNextLine();

            System.out.print("\nEnter new password: ");
            password = getNextLine();
        }

        User user = null;
        try {
            assert userType != null;
            user = switch (userType) {
                case SALESPERSON -> new Salesperson(fullName, dateOfBirth, address, phoneNumber, email, username, password);
                case MECHANIC -> new Mechanic(fullName, dateOfBirth, address, phoneNumber, email, username, password);
                case CLIENT -> new Client(fullName, dateOfBirth, address, phoneNumber, email);
                default -> null;
            };
        } catch (Exception _) { }

        userInterface.addUser(user);
    }

    private String updateUserOperations(User user) {
        int choice;
        do {
            String userString =  "User: " + user.getFullName();
            System.out.println("\n" + userString + "'s information");
            System.out.println("User ID: " + user.getUserID());
            System.out.println("Full Name: " + user.getFullName());
            System.out.println("Date of Birth: " + user.getStringDateOfBirth());
            System.out.println("Address: " + user.getAddress());
            System.out.println("Phone Number: " + user.getPhoneNumber());
            System.out.println("Email: " + user.getEmail());
            System.out.println("User Type: " + user.getUserType());
            System.out.println("Status: " + user.isActive());
            System.out.println("Activity Log: " + user.getActivityLog());

            System.out.println("\nUpdate User Operations Menu:");
            System.out.println("1. Update user");
            System.out.println("2. Remove user");
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
                    user = updateUserMenu(user);
                    break;
                case 2:
                    String choose;
                    do {
                        System.out.print("Are you sure you want to remove " + userString + " ? (Y/N) > ");
                        choose = scanner.next();
                        if (choose.toLowerCase().startsWith("y")) {
                            userInterface.removeUser(user);
                            break;
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

    private User updateUserMenu(User user) {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // consume any leftover newline character
        }

        String fullName = user.getFullName();
        System.out.println("\nCurrent full name: " + fullName);
        System.out.print("Enter new full name (enter to leave the same): ");
        fullName = getNextLineEmpty(fullName);

        String dateOfBirth = user.getStringDateOfBirth();
        System.out.println("\nCurrent date of birth: " + dateOfBirth);
        System.out.print("Enter new date of birth (enter to leave the same): ");
        while (true) {
            try {
                dateOfBirth = getNextLineEmpty(dateOfBirth);
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                df.parse(dateOfBirth);
                break;
            } catch (ParseException e) {
                System.out.println("Date of birth need to be in format: dd/MM/yyyy (Ex: 27/06/2007)");
            }
        }

        String address = user.getAddress();
        System.out.println("\nCurrent address: " + address);
        System.out.print("Enter new address (enter to leave the same): ");
        address = getNextLineEmpty(address);

        String phoneNumber = user.getPhoneNumber();
        System.out.println("\nCurrent phone number: " + phoneNumber);
        System.out.print("Enter new phone number (enter to leave the same): ");
        phoneNumber = getNextLineEmpty(phoneNumber);

        String email = user.getEmail();
        System.out.println("\nCurrent email: " + email);
        System.out.print("Enter new email (enter to leave the same): ");
        email = getNextLineEmpty(email);

        UserType currentUserType = user.getUserType();
        UserType userType = user.getUserType();
        int choice;
        do {
            System.out.println("\nCurrent  user type: " + userType);
            System.out.println("1. Salesperson");
            System.out.println("2. Mechanic");
            System.out.println("3. Client");
            System.out.println("0. Same as current");
            System.out.print("Choose new user type: ");
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
                    userType = UserType.SALESPERSON;
                    break;
                case 2:
                    userType = UserType.MECHANIC;
                    break;
                case 3:
                    userType = UserType.CLIENT;
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (choice != 0);

        String username = user.getUsername();
        String password = user.getPassword();
        if (currentUserType == UserType.CLIENT) {
            if (userType == UserType.SALESPERSON || userType == UserType.MECHANIC) {
                System.out.println("You chose " + userType + ", please enter username and password");

                System.out.print("\nEnter new username: ");
                username = getNextLine();

                System.out.print("\nEnter new password: ");
                password = getNextLine();
            }
        }

        User updatedUser = null;
        try {
            updatedUser = switch (userType) {
                case SALESPERSON -> new Salesperson(fullName, dateOfBirth, address, phoneNumber, email, username, password);
                case MECHANIC -> new Mechanic(fullName, dateOfBirth, address, phoneNumber, email, username, password);
                case CLIENT -> new Client(fullName, dateOfBirth, address, phoneNumber, email);
                default -> null;
            };
        } catch (Exception _) { }

        assert updatedUser != null;
        updatedUser.setUserID(user.getUserID());
        userInterface.updateUser(updatedUser);
        return updatedUser;
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

    public String updateEntityOperations(Entity entity) {
        if (entity instanceof AutoPart) {
            return updatePartOperations((AutoPart) entity);
        } else if (entity instanceof Car) {
            return updateCarOperations((Car) entity);
        } else if (entity instanceof User) {
            return updateUserOperations((User) entity);
        } else if (entity instanceof Transaction) {
            return updateTransactionOperations((Transaction) entity);
        } else if (entity instanceof Service) {
            return updateServiceOperations((Service) entity);
        }
        return "ok";
    }

    private void searchMenu(ArrayList<? extends Entity> items, Function<Entity, String> updateOperations) {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // consume any leftover newline character
        }

        System.out.print("Enter search input: ");
        String searchInput = scanner.nextLine();

        ArrayList<Entity> highSim = new ArrayList<>();
        ArrayList<Entity> partMatch = new ArrayList<>();

        for (Entity e : items) {
            String itemString = e.getSearchString();
            if (itemString.toLowerCase().contains(searchInput)) {
                partMatch.add(e);
            }
            int distance = levenshteinDistance(searchInput, itemString);
            if (distance <= 20) {
                highSim.add(e);
            }
        }

        if (!partMatch.isEmpty()) {
            if (partMatch.size() == 1) {
                updateOperations.apply(partMatch.getFirst());
            } else {
                listAndSelect(partMatch, "Matched search:", updateOperations);
            }
        } else {
            if (!highSim.isEmpty()) {
                listAndSelect(highSim, "Relevant search:", updateOperations);
            } else {
                System.out.println(searchInput + " is might not in the system.");
            }
        }
    }

    public void listAndSelect(ArrayList<? extends Entity> items, String message, Function<Entity, String> updateOperations) {
        int choice;
        do {
            System.out.println("\n" + message);
            int count = 1;
            for (Entity e : items) {
                System.out.println(count + ". " + e.getSearchString());
                count += 1;
            }
            System.out.println("0. Back");
            System.out.print("Select choice: ");

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Please try again.");
                scanner.next(); // consume the invalid token
                choice = -1; // default value that will not exit the loop
                continue;
            }

            if (choice > 0 && choice <= items.size()) {
                String result = updateOperations.apply(items.get(choice - 1));
                if (result.equals("remove")) {
                    return;
                }
            } else if (choice == 0) {} else {
                System.out.println("Invalid option. Please try again.");
            }
        }  while (choice != 0);
    }

    private Entity searchMenuReturn(String searchInput, ArrayList<? extends Entity> items) {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // consume any leftover newline character
        }

        ArrayList<Entity> highSim = new ArrayList<>();
        ArrayList<Entity> partMatch = new ArrayList<>();

        for (Entity e : items) {
            String itemString = e.getSearchString();
            if (itemString.toLowerCase().contains(searchInput)) {
                partMatch.add(e);
            }
            int distance = levenshteinDistance(searchInput, itemString);
            if (distance <= 20) {
                highSim.add(e);
            }
        }

        if (!partMatch.isEmpty()) {
            if (partMatch.size() == 1) {
                return partMatch.getFirst();
            } else {
                return listAndSelectReturn(partMatch, "Matched search:");
            }
        } else {
            if (!highSim.isEmpty()) {
                return listAndSelectReturn(highSim, "Relevant search:");
            } else {
                System.out.println(searchInput + " is might not in the system.");
                return null;
            }
        }
    }

    public Entity selectChoiceOrSearchString(ArrayList<? extends Entity> items, String entity) {
        String choiceString;
        int choice;
        int count = 1;
        boolean searchName;
        do {
            System.out.println("\nSelect " + entity + ":");
            for (Entity e : items) {
                System.out.println(count + ". " + e.getSearchString());
                count += 1;
            }
            System.out.println("0. Back");
            System.out.print("Enter " + entity + " (choice or search): ");
            choiceString = scanner.nextLine();
            try {
                choice = Integer.parseInt(choiceString);
                searchName = false;
            } catch (NumberFormatException e) {
                searchName = true;
                choice = -1;
            }

            if (searchName) {
                return searchMenuReturn(choiceString, items);
            } else {
                if (choice > 0 && choice <= items.size()) {
                    return items.get(choice - 1);
                } else if (choice == 0) {
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            }
        }  while (choice != 0);
        return null;
    }

    public Entity listAndSelectReturn(ArrayList<? extends Entity> items, String message) {
        int choice;
        do {
            System.out.println("\n" + message);
            int count = 1;
            for (Entity e : items) {
                System.out.println(count + ". " + e.getSearchString());
                count += 1;
            }
            System.out.println("0. Back");
            System.out.print("Select choice: ");

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Please try again.");
                scanner.next(); // consume the invalid token
                choice = -1; // default value that will not exit the loop
                continue;
            }

            if (choice > 0 && choice <= items.size()) {
                return items.get(choice - 1);
            } else if (choice == 0) {} else {
                System.out.println("Invalid option. Please try again.");
            }
        }  while (choice != 0);
        return null;
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
                "\nSales Transactions: " + transactionInterface.getAllTransactions().size()
        );
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

    // Save data to files before exit
    private void saveData() throws IOException {
        FileHandler.writeObjectsToFile(carInterface.getAllCars(), "data/cars.obj");
        FileHandler.writeObjectsToFile(autoPartInterface.getAllAutoParts(), "data/parts.obj");
        FileHandler.writeObjectsToFile(userInterface.getAllUsers(), "data/users.obj");
        FileHandler.writeObjectsToFile(transactionInterface.getAllTransactions(), "data/transactions.obj");
        FileHandler.writeObjectsToFile(serviceInterface.getAllServices(), "data/services.obj");
        System.out.println("Data saved successfully.");
    }
}