import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import java.io.IOException;
import java.text.ParseException;

import core.*;
import core.items.*;
import core.user.*;
import interfaces.*;
import operations.*;
import utils.*;

public class Dealership {
    // Attributes
    private final String name;
    private final CarInterface carInterface;
    private final AutoPartInterface autoPartInterface;
    private final UserInterface userInterface;
    private final TransactionInterface transactionInterface;
    private final ServiceInterface serviceInterface;
    User loggedInUser;

    // Scanner for user input
    Scanner scanner = new Scanner(System.in);

    // Constructor
    public Dealership(String name) {
        // Initialize the system
        this.name = name;
        this.carInterface = new CarOperation();
        this.autoPartInterface = new AutoPartOperation();
        this.userInterface = new UserOperation();
        this.transactionInterface = new TransactionOperation();
        this.serviceInterface = new ServiceOperation();

        // Load data from files
        loadData();
    }

    // Main method to start the system
    public void start() {
        // Display welcome message
        System.out.println("\nCOSC2081 GROUP ASSIGNMENT\n" +
                name + " CAR DEALERSHIP MANAGEMENT SYSTEM\n" +
                "Instructor: Mr. Minh Vu & Mr. Dung Nguyen\n" +
                "Group: Confuse Group\n" +
                "s3975162 Le Nguyen Khoi\n" +
                "S3974799 Tran Tuan Anh\n" +
                "s3986546 Nguyen Vu Duy\n" +
                "s3924585 Le Chanh Tri\n\n" +
                name + " currently has" +
                "\nCars: " + carInterface.getAllCars().size() +
                "\nAuto Parts: " + autoPartInterface.getAllAutoParts().size() +
                "\nSales Transactions: " + transactionInterface.getAllTransactions().size() +
                "\nServices: " + serviceInterface.getAllServices().size() +
                "\nUsers: " + userInterface.getAllUsers().size()
        );

        // Login
        System.out.println("\nPlease login");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Authentication authentication = new Authentication(username, password);
        loggedInUser = authentication.authenticate(userInterface.getAllUsers());

        // Show menu based on user role
        if (loggedInUser != null) {
            System.out.println("\nLogin successful. Welcome, " + loggedInUser.getFullName());
            loggedInUser.setStatus(true);
            System.out.println("Role: " + loggedInUser.getUserType());
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
            System.out.println("\nLogin failed. Exiting the system.");
        }

        // Logout
        if (loggedInUser != null) {
            loggedInUser.setStatus(false);
            saveData();
        }
        scanner.close();
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
            choice = getChoice();

            switch (choice) {
                case 1:
                    showCarMenu();
                    break;
                case 2:
                    showAutoPartMenu();
                    break;
                case 3:
                    showServiceMenu(false);
                    break;
                case 4:
                    showTransactionMenu(false);
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
            System.out.println("2. Auto Part Operations");
            System.out.println("3. Sales Transaction Operations");
            System.out.println("4. Perform Statistics");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            choice = getChoice();

            switch (choice) {
                case 1:
                    showCarMenu();
                    break;
                case 2:
                    showAutoPartMenu();
                    break;
                case 3:
                    showTransactionMenu(true);
                    break;
                case 4:
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
            System.out.println("1. Car Operations");
            System.out.println("2. Auto Part Operations");
            System.out.println("3. Service Operations");
            System.out.println("4. Perform Statistics");
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            choice = getChoice();

            switch (choice) {
                case 1:
                    showCarMenu();
                    break;
                case 2:
                    showAutoPartMenu();
                    break;
                case 3:
                    showServiceMenu(true);
                    break;
                case 4:
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

    // show car operations menu
    private void showCarMenu() {
        int choice;
        ArrayList<Car> cars;
        do {
            cars = carInterface.getAllCars();
            System.out.println("\nCar Operations Menu:");
            System.out.println("1. Add a car");
            System.out.println("2. View and select a car");
            System.out.println("3. Search for car");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            choice = getChoice();

            switch (choice) {
                case 1:
                    addCar();
                    break;
                case 2:
                    listAndSelect(cars, "All cars:", this::updateEntityOperations);
                    break;
                case 3:
                    searchMenu(cars, "\nEnter search input (ID (c-)/string): ", this::updateEntityOperations);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }  while (choice != 0);
    }

    // add new car to the system
    private void addCar() {
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

        System.out.print("Enter notes: ");
        String notes = scanner.nextLine();

        // Add car to the system
        Car car = new Car(make, model, year, mileage, color, price, notes);
        carInterface.addCar(car);

        // Add activity to the user
        Activity activity = new Activity("add", car, null);
        loggedInUser.addActivity(activity);
        saveData();
    }

    // list car details and show update car operations
    private String updateCarOperations(Car car) {
        int choice;
        do {
            String carString = "Car: " + car.getMake() + " " + car.getModel() + " " + car.getYear(); // car string for display
            System.out.println("\n" + carString + "'s information");
            System.out.println("Car ID: " + car.getCarID());
            System.out.println("Color: " + car.getColor());
            System.out.println("Mileage: " + car.getMileage());
            System.out.println("Price: " + Statistics.numParse(car.getPrice()));
            System.out.println("Status: " + car.getStatus());
            System.out.println("Notes: " + car.getNotes());

            // Display services history, empty if no services
            ArrayList<Service> services = car.getServicesHistory();
            if (!services.isEmpty()) {
                System.out.println("Services History: ");
                for (Service s : services) {
                    System.out.println("- " + s.getSearchString());
                }
            } else {
                System.out.println("Services History: Empty");
            }

            System.out.println("\nUpdate Car Operations Menu:");
            System.out.println("1. Update car");
            System.out.println("2. Remove car");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            choice = getChoice();

            switch (choice) {
                case 1:
                    car = updateCarMenu(car);
                    break;
                case 2:
                    // Confirm before removing the car
                    String choose;
                    do {
                        System.out.print("Are you sure you want to remove " + carString + " ? (Y/N) > ");
                        choose = scanner.next();
                        if (choose.toLowerCase().startsWith("y")) {
                            // Remove the car from the system
                            carInterface.removeCar(car);

                            // Add activity to the user
                            Activity activity = new Activity("delete", car, null);
                            loggedInUser.addActivity(activity);
                            saveData();
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

    // update car details
    private Car updateCarMenu(Car car) {
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

        String notes = car.getNotes();
        System.out.println("\nCurrent notes: " + notes);
        System.out.print("Enter new notes (enter to leave the same): ");
        notes = getNextLineEmpty(notes);

        // Update the car in the system
        Car updatedCar = new Car(make, model, year, mileage, color, price, notes);
        updatedCar.setCarID(car.getCarID());
        updatedCar.setStatus(car.getStatus());
        updatedCar.setServicesHistory(car.getServicesHistory());
        carInterface.updateCar(updatedCar);

        // Add activity to the user
        Activity activity = new Activity("update", car, updatedCar);
        loggedInUser.addActivity(activity);
        saveData();
        return updatedCar;
    }

    // show auto part operations menu
    private void showAutoPartMenu() {
        int choice;
        ArrayList<AutoPart> autoParts;
        do {
            autoParts = autoPartInterface.getAllAutoParts();
            System.out.println("\nAuto Part Operations Menu:");
            System.out.println("1. Add an auto part");
            System.out.println("2. View and select an auto part");
            System.out.println("3. Search for auto part");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            choice = getChoice();

            switch (choice) {
                case 1:
                    addPart();
                    break;
                case 2:
                    listAndSelect(autoParts, "All auto parts:", this::updateEntityOperations);
                    break;
                case 3:
                    searchMenu(autoParts, "\nEnter search input (ID (p-)/string): ", this::updateEntityOperations);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }  while (choice != 0);
    }

    // add new auto part to the system
    private void addPart() {
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

        // Add auto part to the system
        AutoPart autoPart = new AutoPart(partName, manufacturer, partNumber, condition, warranty, cost, notes);
        autoPartInterface.addAutoPart(autoPart);

        // Add activity to the user
        Activity activity = new Activity("add", autoPart, null);
        loggedInUser.addActivity(activity);
        saveData();
    }

    // list auto part details and show update auto part operations
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
            System.out.println("Cost: " + Statistics.numParse(part.getCost()));
            System.out.println("Notes: " + part.getNotes());

            System.out.println("\nUpdate Auto Part Operations Menu:");
            System.out.println("1. Update auto part");
            System.out.println("2. Remove auto part");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            choice = getChoice();

            switch (choice) {
                case 1:
                    part = updatePartMenu(part);
                    break;
                case 2:
                    // Confirm before removing the auto part
                    String choose;
                    do {
                        System.out.print("Are you sure you want to remove " + partString + " ? (Y/N) > ");
                        choose = scanner.next();
                        if (choose.toLowerCase().startsWith("y")) {
                            // Remove the auto part from the system
                            autoPartInterface.removeAutoPart(part);

                            // Add activity to the user
                            Activity activity = new Activity("delete", part, null);
                            loggedInUser.addActivity(activity);
                            saveData();
                            break; // exit the loop after removing the auto part
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

    // update auto part details
    private AutoPart updatePartMenu(AutoPart part) {
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

        // Update the auto part in the system
        AutoPart updatedPart = new AutoPart(partName, manufacturer, partNumber, condition, warranty, cost, notes);
        updatedPart.setPartID(part.getPartID());
        autoPartInterface.updateAutoPart(updatedPart);

        // Add activity to the user
        Activity activity = new Activity("update", part, updatedPart);
        loggedInUser.addActivity(activity);
        saveData();
        return updatedPart;
    }

    // show service operations menu
    private void showServiceMenu(boolean isMechanic) {
        int choice;
        ArrayList<Service> services;
        do {
            services = serviceInterface.getAllServices();
            System.out.println("\nService Operations Menu:");
            System.out.println("1. Add a service");
            if (!isMechanic) {
                System.out.println("2. View and select a service");
                System.out.println("3. Search for service");
            } else {
                System.out.println("2. View and select service");
            }
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Please try again.");
                scanner.next(); // consume the invalid token
                choice = -1; // default value that will not exit the loop
                continue;
            }

            if (!isMechanic) {
                switch (choice) {
                    case 1:
                        addService(false);
                        break;
                    case 2:
                        listAndSelect(services, "All services:", this::updateEntityOperations);
                        break;
                    case 3:
                        searchMenu(services, "\nEnter search input (ID (s-)/string): ", this::updateEntityOperations);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } else {
                switch (choice) {
                    case 1:
                        addService(true);
                        break;
                    case 2:
                        listAndSelect(services, "All services:", this::viewOnlyEntityOperations);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }
        }  while (choice != 0);
    }

    // select client for transaction or service
    private Client selectClient(boolean back) {
        int choice;
        ArrayList<Client> clients;
        while (true) {
            clients = userInterface.getAllClients();
            System.out.println("\n1. Add new client");
            System.out.println("2. Choose from existing client");
            if (back) {
                System.out.println("0. Leave the same");
            }
            System.out.print("Enter choice: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume left over
            } catch (InputMismatchException e) {
                System.out.println("Invalid option. Please try again.");
                scanner.next(); // consume the invalid token
                continue;
            }

            switch (choice) {
                case 1:
                    return addClient();
                case 2:
                    return (Client) selectChoiceOrSearch(clients, "Client", back);
                case 0:
                    if (back) {
                        return null;
                    }
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // get clients who own car(s)
    private ArrayList<Client> getClientsOwnCar(ArrayList<Client> clients) {
        // get all client IDs who own car(s)
        ArrayList<String> clientIDs = new ArrayList<>();
        for (Transaction t : transactionInterface.getAllTransactions()) {
            for (Item i : t.getItems()) {
                if (i instanceof Car) { // only add client ID if the item is a car
                    if (!clientIDs.contains(t.getClientID())) { // avoid duplicate client IDs
                        clientIDs.add(t.getClientID());
                    }
                    break;
                }
            }
        }

        // get clients as objects who own car(s)
        ArrayList<Client> clientsOwnCar = new ArrayList<>();
        for (String clientID : clientIDs) {
            for (Client c : clients) {
                if (c.getUserID().equals(clientID)) {
                    clientsOwnCar.add(c);
                }
            }
        }
        return clientsOwnCar;
    }

    // add new client to the system
    private Client addClient() {
        System.out.print("\nEnter full name: ");
        String fullName = getNextLine();

        System.out.print("Enter date of birth: ");
        // validate date of birth format
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

        try {
            // Add client to the system
            Client client = new Client(fullName, dateOfBirth, address, phoneNumber, email);
            userInterface.addUser(client);

            // Add activity to the user
            Activity activity = new Activity("add", client, null);
            loggedInUser.addActivity(activity);
            saveData();
            return client;
        } catch (Exception e) {
            System.out.println("An error has occurred while adding client: " + e.getMessage());
            return null;
        }
    }

    // add new service to the system
    private void addService(boolean isMechanic) {
        Mechanic mechanic;
        // if the user is a mechanic, use the logged-in user as the mechanic
        do {
            if (isMechanic) {
                mechanic = (Mechanic) loggedInUser;
            } else {
                ArrayList<Mechanic> mechanics = userInterface.getAllMechanics();
                mechanic = (Mechanic) selectChoiceOrSearch(mechanics, "Mechanic", false);
            }
        } while (mechanic == null);

        Client client;
        do {
            ArrayList<Client> clients = userInterface.getAllClients();
            client = (Client) selectChoiceOrSearch(getClientsOwnCar(clients), "Client", false);
        } while (client == null);

        Car car = selectCar(client, false);

        System.out.print("Enter service type: ");
        String serviceType = getNextLine();

        ArrayList<AutoPart> parts = autoPartInterface.getAllAutoParts();
        ArrayList<Entity> entities = addChoiceOrSearch(parts, "Auto Parts", null); // get list of entities
        // get replaced parts from the entities
        ArrayList<AutoPart> replacedParts = new ArrayList<>();
        for (Entity e : entities) {
            if (e instanceof AutoPart) { // only add auto part if it is an auto part
                replacedParts.add((AutoPart) e);
            }
        }

        System.out.print("Enter service cost: ");
        BigDecimal serviceCost = getNextBigDecimal();

        System.out.print("Enter notes: ");
        String notes = scanner.nextLine();

        // declare service
        Service service = new Service(client.getUserID(), mechanic.getUserID(), car.getCarID(), serviceType, serviceCost, notes);
        service.setReplacedParts(replacedParts);

        // apply discount and set new service cost
        service.setDiscountPercentage(client.getMembershipType());

        // add service to the system
        serviceInterface.addService(service);

        // add client total spending after apply discount (if exists)
        client.addTotalSpending(service.getServiceCost());

        // add service to the car
        car.addService(service);

        // add activity to the user
        Activity activity = new Activity("add", service, null);
        loggedInUser.addActivity(activity);
        saveData();
    }

    // show service details and update service operations
    private String updateServiceOperations(Service service, boolean viewOnly) {
        int choice;
        do {
            String serviceString = "Service: " + service.getServiceType();
            System.out.println("\n" + serviceString + "'s information");
            System.out.println("Service ID: " + service.getServiceID());
            System.out.println("Client ID: " + service.getClientID());
            System.out.println("Mechanic ID: " + service.getMechanicID());
            System.out.println("Car ID: " + service.getCarID());
            System.out.println("Service Type: " + service.getServiceType());

            // Display replaced parts, empty if no replaced parts
            ArrayList<AutoPart> parts = service.getReplacedParts();
            if (!parts.isEmpty()) {
                System.out.println("Replaced Parts: ");
                for (AutoPart p : parts) {
                    System.out.println("- " + p.getSearchString());
                }
            } else {
                System.out.println("Replaced Parts: Empty");
            }

            System.out.println("Service Cost: " + Statistics.numParse(service.getServiceCost()));
            System.out.println("Notes: " + service.getNotes());

            // if mechanic, skip the update and remove options
            if (viewOnly) {
                break;
            }

            System.out.println("\nUpdate Service Operations Menu:");
            System.out.println("1. Update service");
            System.out.println("2. Remove service");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            choice = getChoice();

            switch (choice) {
                case 1:
                    service = updateServiceMenu(service);
                    break;
                case 2:
                    // Confirm before removing the service
                    String choose;
                    do {
                        System.out.print("Are you sure you want to remove " + serviceString + " ? (Y/N) > ");
                        choose = scanner.next();
                        if (choose.toLowerCase().startsWith("y")) {
                            // Remove the service from the system
                            serviceInterface.removeService(service);

                            // Remove the service from the car
                            carInterface.removeService(service);

                            // Add activity to the user
                            Activity activity = new Activity("delete", service, null);
                            loggedInUser.addActivity(activity);
                            saveData();
                            break; // exit the loop after removing the service
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

    // update service details
    private Service updateServiceMenu(Service service) {
        String mechanicID = service.getMechanicID();
        ArrayList<Mechanic> mechanics = userInterface.getAllMechanics();
        Mechanic mechanic = (Mechanic) selectChoiceOrSearch(mechanics, "Mechanic", true);
        if (mechanic != null) {
            mechanicID = mechanic.getUserID();
        }

        String clientID = service.getClientID();
        ArrayList<Client> clients = userInterface.getAllClients();
        Client client = (Client) selectChoiceOrSearch(getClientsOwnCar(clients), "Client", true);
        if (client != null) {
            clientID = client.getUserID();
        } else {
            for (Client c : clients) {
                if (c.getUserID().equals(clientID)) {
                    client = c;
                    break;
                }
            }
        }

        String ogCarID = service.getCarID();
        String carID = service.getCarID();
        boolean changed = false;
        Car car = selectCar(client, true);
        if (car != null) {
            changed = true;
            carID = car.getCarID();
        }

        String serviceType = service.getServiceType();
        System.out.println("\nCurrent service type: " + serviceType);
        System.out.print("Enter new service type (enter to leave the same): ");
        serviceType = getNextLineEmpty(serviceType);

        BigDecimal serviceCost = service.getServiceCost();
        System.out.println("\nCurrent service cost: " + serviceCost);
        System.out.print("Enter new service cost (enter to leave the same): ");
        serviceCost = getNextBigDecimalEmpty(serviceCost);

        System.out.println("\nUpdate replaced part(s):");
        ArrayList<AutoPart> parts = autoPartInterface.getAllAutoParts();
        ArrayList<Entity> entities = addChoiceOrSearch(parts, "Auto Parts", service.getReplacedParts()); // get list of entities

        // get replaced parts from the entities
        ArrayList<AutoPart> replacedParts = new ArrayList<>();
        if (!entities.isEmpty()) {
            for (Entity e : entities) {
                if (e instanceof AutoPart) {
                    replacedParts.add((AutoPart) e);
                }
            }
        } else {
            replacedParts = service.getReplacedParts();
        }

        String notes = service.getNotes();
        System.out.println("\nCurrent notes: " + notes);
        System.out.print("Enter new notes (enter to leave the same): ");
        notes = getNextLineEmpty(notes);

        // update the service in the system
        Service updatedService = new Service(clientID, mechanicID, carID, serviceType, serviceCost, notes);
        updatedService.setServiceID(service.getServiceID());
        updatedService.setReplacedParts(replacedParts);
        serviceInterface.updateService(updatedService);

        // update the service in the car if the car has changed
        if (changed) {
            for (Car c : carInterface.getAllCars()) {
                if (c.getCarID().equals(ogCarID)) {
                    // remove the service from the old car
                    ArrayList<Service> newServicesHistory = new ArrayList<>();
                    for (Service s : c.getServicesHistory()) {
                        if (!s.getServiceID().equals(service.getServiceID())) {
                            newServicesHistory.add(s);
                        }
                    }
                    c.setServicesHistory(newServicesHistory);
                }
            }
            // add the service to the new car
            car.addService(service);
        }

        // add activity to the user
        Activity activity = new Activity("update", service, updatedService);
        loggedInUser.addActivity(activity);
        saveData();
        return updatedService;
    }

    // select car for transaction or service
    private Car selectCar(Client client, boolean back) {
        ArrayList<Car> cars = carInterface.getAllCars();
        ArrayList<Car> newCars = new ArrayList<>();
        ArrayList<Transaction> transactions = transactionInterface.getAllTransactions();

        // get cars that belong to the client
        for (Transaction t : transactions) {
            if (t.getClientID().equals(client.getUserID())) {
                ArrayList<Item> items = t.getItems();
                for (Item i : items) {
                    if (i instanceof Car) {
                        for (Car c : cars) {
                            if (c.getCarID().equals(((Car) i).getCarID())) {
                                newCars.add(c);
                                break;
                            }
                        }
                    }
                }
            }
        }
        cars = newCars;
        return (Car) selectChoiceOrSearch(cars, "Car", back);
    }

    // show transaction operations menu
    private void showTransactionMenu(boolean isSalesperson) {
        int choice;
        ArrayList<Transaction> transactions;
        do {
            transactions = transactionInterface.getAllTransactions();
            System.out.println("\nSales Transaction Operations Menu:");
            System.out.println("1. Add a sales transaction");
            if (!isSalesperson) {
                System.out.println("2. View and select a sales transaction");
                System.out.println("3. Search for sales transaction");
            } else {
                System.out.println("2. View and select a sales transaction");
            }
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            choice = getChoice();

            if (!isSalesperson) {
                switch (choice) {
                    case 1:
                        addTransaction(false);
                        break;
                    case 2:
                        listAndSelect(transactions, "All sales transactions:", this::updateEntityOperations);
                        break;
                    case 3:
                        searchMenu(transactions, "\nEnter search input (ID (t-)/string): ", this::updateEntityOperations);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } else {
                switch (choice) {
                    case 1:
                        addTransaction(true);
                        break;
                    case 2:
                        listAndSelect(transactions, "All sales transactions:", this::viewOnlyEntityOperations);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            }
        }  while (choice != 0);
    }

    // add new transaction to the system
    private void addTransaction(boolean isSalesperson) {
        // if the user is a salesperson, use the logged-in user as the salesperson
        Salesperson salesperson;
        do {
            if (isSalesperson) {
                salesperson = (Salesperson) loggedInUser;
            } else {
                ArrayList<Salesperson> salespersons = userInterface.getAllSalespersons();
                salesperson = (Salesperson) selectChoiceOrSearch(salespersons, "Salesperson", false);
            }
        } while (salesperson == null);

        Client client;
        do {
            client = selectClient(false);
        } while (client == null);

        ArrayList<AutoPart> parts = autoPartInterface.getAllAutoParts();
        ArrayList<Car> availableCars = carInterface.getAvailableCars();
        ArrayList<Item> items = new ArrayList<>();
        ArrayList<Entity> entities;

        // add auto parts to the items
        entities = addChoiceOrSearch(parts, "Auto Parts", null);
        for (Entity e : entities) {
            if (e instanceof AutoPart) {
                items.add((AutoPart) e);
            }
        }

        // add available cars to the items
        entities = addChoiceOrSearch(availableCars, "Cars", null);
        for (Entity e : entities) {
            if (e instanceof Car) {
                items.add((Car) e);
            }
        }

        System.out.print("\nEnter total amount: ");
        BigDecimal totalAmount = getNextBigDecimal();

        System.out.print("Enter notes: ");
        String notes = scanner.nextLine();

        // declare transaction
        Transaction transaction = new Transaction(client.getUserID(), salesperson.getUserID(), totalAmount, notes);
        transaction.setItems(items);

        // apply discount and set new total amount
        transaction.setDiscountPercentage(client.getMembershipType());

        // add client total spending after apply discount (if exists)
        client.addTotalSpending(transaction.getTotalAmount());

        // add transaction and set all cars in items to sold
        transactionInterface.addTransaction(transaction);

        // set all cars in items to sold
        Activity activity = new Activity("add", transaction, null);
        loggedInUser.addActivity(activity);
        saveData();
    }

    // show transaction details and update transaction operations
    private String updateTransactionOperations(Transaction transaction, boolean viewOnly) {
        int choice;
        do {
            String transactionString =  "Sales Transaction at " + transaction.getStringTransactionDate();
            System.out.println("\n" + transactionString + "'s information");
            System.out.println("Transaction ID: " + transaction.getTransactionID());
            System.out.println("Client ID: " + transaction.getClientID());
            System.out.println("Mechanic ID: " + transaction.getSalespersonID());

            // Display items, separate cars and auto parts, empty if no items
            ArrayList<Item> items = transaction.getItems();
            if (!items.isEmpty()) {
                ArrayList<Car> cars = new ArrayList<>();
                ArrayList<AutoPart> parts = new ArrayList<>();
                for (Item i : items) {
                    if (i instanceof Car) {
                        cars.add((Car) i);
                    } else if (i instanceof AutoPart) {
                        parts.add((AutoPart) i);
                    }
                }

                if (!cars.isEmpty()) {
                    System.out.println("Cars: ");
                    for (Car c : cars) {
                        System.out.println("- " + c.getSearchString());
                    }
                }
                if (!parts.isEmpty()) {
                    System.out.println("Auto Parts: ");
                    for (AutoPart p : parts) {
                        System.out.println("- " + p.getSearchString());
                    }
                }
            } else {
                System.out.println("Items: Empty");
            }

            System.out.println("Discount Percentage: " + transaction.getDiscountPercentage());
            System.out.println("Total Amount: " + Statistics.numParse(transaction.getTotalAmount()));
            System.out.println("Notes: " + transaction.getNotes());

            // if salesperson, skip the update and remove options
            if (viewOnly) {
                break;
            }

            System.out.println("\nUpdate Sales Transaction Operations Menu:");
            System.out.println("1. Update sales transaction");
            System.out.println("2. Remove sales transaction");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            choice = getChoice();

            switch (choice) {
                case 1:
                    transaction = updateTransactionMenu(transaction);
                    break;
                case 2:
                    // Confirm before removing the transaction
                    String choose;
                    do {
                        System.out.print("Are you sure you want to remove " + transactionString + " ? (Y/N) > ");
                        choose = scanner.next();
                        if (choose.toLowerCase().startsWith("y")) {
                            // if car(s) in the transaction, set the car(s)'s status to available
                            for (Item e : transaction.getItems()) {
                                if (e instanceof Car) {
                                    for (Car c : carInterface.getAllCars()) {
                                        if (c.getCarID().equals(((Car) e).getCarID())) {
                                            c.setStatus("available");
                                            break;
                                        }
                                    }
                                }
                            }

                            // Remove the transaction from the system
                            transactionInterface.removeTransaction(transaction);

                            // Add activity to the user
                            Activity activity = new Activity("delete", transaction, null);
                            loggedInUser.addActivity(activity);
                            saveData();
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

    // update transaction details
    private Transaction updateTransactionMenu(Transaction transaction) {
        String salespersonID = transaction.getSalespersonID();
        ArrayList<Salesperson> salespersons = userInterface.getAllSalespersons();
        Salesperson salesperson = (Salesperson) selectChoiceOrSearch(salespersons, "Salesperson", true);
        if (salesperson != null) {
            salespersonID = salesperson.getUserID();
        }

        String clientID = transaction.getClientID();
        Client client = selectClient(true);
        if (client != null) {
            clientID = client.getUserID();
        }

        BigDecimal totalAmount = transaction.getTotalAmount();
        System.out.println("\nCurrent total amount: " + totalAmount);
        System.out.print("Enter new service cost (enter to leave the same): ");
        totalAmount = getNextBigDecimalEmpty(totalAmount);

        System.out.println("\nUpdate item(s):");
        ArrayList<AutoPart> parts = autoPartInterface.getAllAutoParts();
        ArrayList<Car> availableCars = carInterface.getAvailableCars();
        ArrayList<Item> items = new ArrayList<>(Stream.concat(parts.stream(), availableCars.stream()).toList());
        ArrayList<Item> transactionItems = transaction.getItems();
        ArrayList<Entity> entities = addChoiceOrSearch(items, "Item", transactionItems);

        String notes = transaction.getNotes();
        System.out.println("\nCurrent notes: " + notes);
        System.out.print("Enter new notes (enter to leave the same): ");
        notes = getNextLineEmpty(notes);

        // set all cars in items to sold
        ArrayList<Item> newItems = new ArrayList<>();
        if (!entities.isEmpty()) {
            for (Item i : transactionItems) {
                if (i instanceof Car) {
                    ((Car) i).setStatus("available");
                }
            }

            for (Entity e : entities) {
                if (e instanceof AutoPart) {
                    newItems.add((AutoPart) e);
                } else if (e instanceof Car) {
                    newItems.add((Car) e);
                }
            }
        } else {
            newItems = transaction.getItems();
        }

        // update the transaction in the system
        Transaction updatedTransaction = new Transaction(clientID, salespersonID, totalAmount, notes);
        updatedTransaction.setTransactionID(transaction.getTransactionID());
        updatedTransaction.setItems(newItems);

        // update transaction and set all cars in items to sold
        transactionInterface.updateTransaction(updatedTransaction);

        // set all cars in items to sold
        Activity activity = new Activity("update", transaction, updatedTransaction);
        loggedInUser.addActivity(activity);
        saveData();
        return updatedTransaction;
    }

    // show user operations menu
    private void showUserMenu() {
        int choice;
        ArrayList<User> users;
        do {
            users = userInterface.getAllUsers();
            System.out.println("\nUser Operations Menu:");
            System.out.println("1. View and select a user");
            System.out.println("2. Search for user");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            choice = getChoice();

            switch (choice) {
                case 1:
                    listAndSelect(users, "All users:", this::updateEntityOperations);
                    break;
                case 2:
                    searchMenu(users, "\nEnter search input (ID (u-)/string): ", this::updateEntityOperations);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }  while (choice != 0);
    }

    // show user details and update user operations
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
            System.out.println("Status: " + user.isStatus());

            if (user.getUserType() != UserType.CLIENT) { // if the user is not a client, show activity log
                ArrayList<Activity> activityLog = user.getActivityLog();
                if (!activityLog.isEmpty()) {
                    System.out.println("Activity Log: ");
                    for (Activity a : activityLog) {
                        // display activity log based on the operation
                        String date = a.getStringActivityDate();
                        String operation = a.getOperation();
                        String name = a.getEntity().getClass().getSimpleName();
                        String entity = a.getEntity().getSearchString();
                        switch (operation) {
                            case "add":
                                if (name.equals("Transaction") || name.equals("Service")) {
                                    System.out.println("Add " + name + " at " + entity);
                                } else {
                                    System.out.println("Add " + name + " at " + date + " | " + entity);
                                }
                                break;
                            case "update":
                                if (name.equals("Transaction") || name.equals("Service")) {
                                    System.out.println("Update " + name + " at " + a.getUpdatedEntity().getSearchString() + " from " + entity);
                                } else {
                                    System.out.println("Update " + name + " at " + date + " | " + a.getUpdatedEntity().getSearchString() + " from " + entity);
                                }
                                break;
                            case "delete":
                                if (name.equals("Transaction") || name.equals("Service")) {
                                    System.out.println("Delete " + name + " at " + entity);
                                } else {
                                    System.out.println("Delete " + name + " at " + date + " | " + entity);
                                }
                                break;
                        }
                    }
                } else {
                    System.out.println("Activity Log: Empty");
                }
            } else { // if the user is a client, show total spending and membership type
                System.out.println("Total spending: " + Statistics.numParse(((Client) user).getTotalSpending()));
                System.out.println("Membership: " + ((Client) user).getMembershipType());
            }

            System.out.println("\nUpdate User Operations Menu:");
            System.out.println("1. Update user");
            if (user.getUserType() != UserType.MANAGER) {
                System.out.println("2. Remove user");
            }
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            choice = getChoice();

            switch (choice) {
                case 1:
                    user = updateUserMenu(user);
                    break;
                case 2:
                    // Confirm before removing the user
                    String choose;
                    do {
                        System.out.print("Are you sure you want to remove " + userString + " ? (Y/N) > ");
                        choose = scanner.next();
                        if (choose.toLowerCase().startsWith("y")) {
                            // Remove the user from the system
                            userInterface.removeUser(user);

                            // Add activity to the user
                            Activity activity = new Activity("delete", user, null);
                            loggedInUser.addActivity(activity);
                            saveData();
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

    // update user details
    private User updateUserMenu(User user) {
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
        boolean done = false;
        do {
            if (done) {
                break;
            }

            System.out.println("\nCurrent  user type: " + userType);
            System.out.println("1. Salesperson");
            System.out.println("2. Mechanic");
            System.out.println("3. Client");
            System.out.println("0. Same as current");
            System.out.print("Choose new user type: ");
            choice = getChoice();

            switch (choice) {
                case 1:
                    userType = UserType.SALESPERSON;
                    done = true;
                    break;
                case 2:
                    userType = UserType.MECHANIC;
                    done = true;
                    break;
                case 3:
                    userType = UserType.CLIENT;
                    done = true;
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
        // if the previous user type is client and now changes to employee, ask for username and password
        if (currentUserType == UserType.CLIENT) {
            if (userType == UserType.SALESPERSON || userType == UserType.MECHANIC) {
                System.out.println("You chose " + userType + ", please enter username and password");

                System.out.print("\nEnter new username: ");
                username = getNextLine();

                System.out.print("\nEnter new password: ");
                password = getNextLine();
            }
        }

        // update user in the system
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

        // add activity to the user
        Activity activity = new Activity("update", user, updatedUser);
        loggedInUser.addActivity(activity);
        saveData();
        return updatedUser;
    }

    // show manager statistic menu
    private void showManagerStatisticMenu() {
        int choice;
        ArrayList<Service> services;
        ArrayList<Transaction> transactions;
        do {
            services = serviceInterface.getAllServices();
            transactions = transactionInterface.getAllTransactions();
            System.out.println("\nStatistic Menu:");
            System.out.println("1. Number of cars sold in a period of time");
            System.out.println("2. Total revenue in a period of time");
            System.out.println("3. Revenue of services done by a mechanic");
            System.out.println("4. Revenue of cars sold by a salesperson");
            System.out.println("5. List cars sold in a period of time");
            System.out.println("6. List transaction in a period of time");
            System.out.println("7. List services done in a period of time");
            System.out.println("8. List auto parts sold in a period of time");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            choice = getChoice();

            switch (choice) {
                case 1:
                    // count sold cars in a period of time
                    processMenu("Count sold cars in a period of time:", null, transactions, (_, transactionsList, input, dateType) -> {
                        int soldCars = Statistics.countSoldCarsInPeriod(transactionsList, input, dateType);
                        if (dateType.equals("week")) {
                            System.out.println("\nThere are " + soldCars + " cars sold in the week starts from " + input + ".");
                        } else {
                            System.out.println("\nThere are " + soldCars + " cars sold in " + input + ".");
                        }
                    });
                    break;
                case 2:
                    // calculate total revenue in a period of time
                    processMenu("Total revenue in a period of time:", services, transactions, (servicesList, transactionsList, input, dateType) -> {
                        BigDecimal revenue = Statistics.totalRevenueInPeriod(servicesList, transactionsList, input, dateType);
                        if (dateType.equals("week")) {
                            System.out.println("\n" + name + " has a revenue of " + Statistics.numParse(revenue) + " VND in the week starts from " + input + ".");
                        } else {
                            System.out.println("\n" + name + " has a revenue of " + Statistics.numParse(revenue) + " VND in " + input + ".");
                        }
                    });
                    break;
                case 3:
                    revenueServiceByMechanicMenu(services);
                    break;
                case 4:
                    revenueCarSoldBySalespersonMenu(transactions);
                    break;
                case 5:
                    listCarsMenu(transactions);
                    break;
                case 6:
                    listTransactionsMenu(transactions);
                    break;
                case 7:
                    listServicesMenu(services);
                    break;
                case 8:
                    listPartsMenu(services, transactions);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }  while (choice != 0);
    }

    // show mechanic statistic menu
    private void revenueServiceByMechanicMenu(ArrayList<Service> services) {
        ArrayList<Mechanic> mechanics = userInterface.getAllMechanics();
        Mechanic mechanic = (Mechanic) selectChoiceOrSearch(mechanics, "Mechanic", false);
        BigDecimal revenue = Statistics.revenueServiceByMechanic(services, mechanic);
        System.out.println("\nMechanic " + mechanic.getFullName() + " has a services done revenue of " + Statistics.numParse(revenue) + " VND.");
    }

    // show salesperson statistic menu
    private void revenueCarSoldBySalespersonMenu(ArrayList<Transaction> transactions) {
        ArrayList<Salesperson> salespersons = userInterface.getAllSalespersons();
        Salesperson salesperson = (Salesperson) selectChoiceOrSearch(salespersons, "Salesperson", false);
        BigDecimal revenue = Statistics.revenueCarsBySalesperson(transactions, salesperson);
        System.out.println("\nSalesperson " + salesperson.getFullName() + " has a cars sold revenue of " + Statistics.numParse(revenue) + " VND.");
    }

    // explain date input
    private void explainDateInput() {
        System.out.println("1. Day (dd/MM/yyyy)");
        System.out.println("2. Week (Enter start date of the week in dd/MM/yyyy format)");
        System.out.println("3. Month (MM/yyyy)");
        System.out.println("0. Cancel");
        System.out.print("Enter your choice: ");
    }

    // process menu for statistic
    @FunctionalInterface
    private interface BiConsumerWithExceptions<T, U, V, B, E extends Exception> {
        void accept(T t, U u, V v, B b) throws E;
    }

    // process menu to enter date input
    private <T, U> void processMenu(String message, ArrayList<T> services, ArrayList<U> transactions, BiConsumerWithExceptions<ArrayList<T>, ArrayList<U>, String, String, ParseException> action) {
        String input;
        String dateType = "";
        int choice;
        do {
            System.out.println("\n" + message);
            explainDateInput();
            try {
                // get the date type
                input = scanner.nextLine();
                choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        dateType = "day";
                        System.out.print("Enter date (dd/MM/yyyy): ");
                        input = scanner.nextLine();
                        if (input.length() != 10) {
                            System.out.println("Invalid date. Please try again.");
                            continue;
                        }
                        break;
                    case 2:
                        dateType = "week";
                        System.out.print("Enter start date of the week (dd/MM/yyyy): ");
                        input = scanner.nextLine();
                        if (input.length() != 10) {
                            System.out.println("Invalid date. Please try again.");
                            continue;
                        }
                        break;
                    case 3:
                        dateType = "month";
                        System.out.print("Enter month (MM/yyyy): ");
                        input = scanner.nextLine();
                        if (input.length() != 7) {
                            System.out.println("Invalid date. Please try again.");
                            continue;
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid choice");
                        continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
                choice = -1;
                continue;
            }
    
            try {
                action.accept(services, transactions, input, dateType); // process the action
                return;
            } catch (ParseException d) {
                System.out.println("Invalid date. Please try again.");
            }
    
        } while (choice != 0);
    }

    // list cars sold in a period of time
    private void listCarsMenu(ArrayList<Transaction> transactions) {
        processMenu("List cars sold in a period of time:", null, transactions, (_, transactionsList, input, dateType) -> Statistics.listCarInAPeriod(transactionsList, input, dateType));
    }

    // list transactions in a period of time
    private void listTransactionsMenu(ArrayList<Transaction> transactions) {
        processMenu("List sales transactions created in a period of time:", null, transactions, (_, transactionsList, input, dateType) -> Statistics.listTransactionInAPeriod(transactionsList, input, dateType));
    }

    // list services done in a period of time
    private void listServicesMenu(ArrayList<Service> services) {
        processMenu("List services done in a period of time:", services, null, (servicesList, _, input, dateType) -> Statistics.listServiceInAPeriod(servicesList, input, dateType));
    }

    // list auto parts sold in a period of time
    private void listPartsMenu(ArrayList<Service> services, ArrayList<Transaction> transactions) {
        processMenu("List auto parts sold in a period of time:", services, transactions, Statistics::listPartInAPeriod);
    }

    // show employee statistic menu
    private void showEmployeeStatisticMenu() {
        int choice;
        ArrayList<Transaction> transactions;
        ArrayList<Service> services;
        do {
            transactions = transactionInterface.getAllTransactions();
            services = serviceInterface.getAllServices();
            System.out.println("\nStatistic Menu:");
            System.out.println("1. Total revenue in a period of time");
            System.out.println("2. List cars sold in a period of time");
            System.out.println("3. List services done in a period of time");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            choice = getChoice();

            switch (choice) {
                case 1:
                // calculate total revenue in a period of time
                    processMenu("Total revenue in a period of time:", services, transactions, (servicesList, transactionsList, input, dateType) -> {
                        BigDecimal revenue = Statistics.totalRevenueInPeriod(servicesList, transactionsList, input, dateType);
                        if (dateType.equals("week")) {
                            System.out.println("\n" + name + " has a revenue of " + Statistics.numParse(revenue) + " VND in the week starts from " + input + ".");
                        } else {
                            System.out.println("\n" + name + " has a revenue of " + Statistics.numParse(revenue) + " VND in " + input + ".");
                        }
                    });
                    break;
                case 2:
                    listCarsMenu(transactions);
                    break;
                case 3:
                    listServicesMenu(services);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }  while (choice != 0);
    }

    // controller for each entity's update operations
    private String updateEntityOperations(Entity entity) {
        if (entity instanceof AutoPart) {
            return updatePartOperations((AutoPart) entity);
        } else if (entity instanceof Car) {
            return updateCarOperations((Car) entity);
        } else if (entity instanceof User) {
            return updateUserOperations((User) entity);
        } else if (entity instanceof Transaction) {
            return updateTransactionOperations((Transaction) entity, false);
        } else if (entity instanceof Service) {
            return updateServiceOperations((Service) entity, false);
        }
        return "ok";
    }

    // controller for each entity's view only operations
    private String viewOnlyEntityOperations(Entity entity) {
         if (entity instanceof Transaction) {
            return updateTransactionOperations((Transaction) entity, true);
         } else if (entity instanceof Service) {
             return updateServiceOperations((Service) entity, true);
         }
        return "ok";
    }

    // search menu
    private void searchMenu(ArrayList<? extends Entity> items, String message, Function<Entity, String> updateOperations) {
        System.out.print(message);
        String searchInput = scanner.nextLine();

        // check if the search input is an ID or a string
        boolean searchWithID = false;
        try {
            Integer.parseInt(searchInput);
            searchWithID = true;
        } catch (NumberFormatException _) {
            List<String> startID = Arrays.asList("c-", "p-", "s-", "t-", "u-");
            boolean startsWithAny = startID.stream().anyMatch(searchInput::startsWith);
            if (startsWithAny) {
                searchWithID = true;
            }
        }

        if (searchWithID) { // search with ID
            // search for the entity with the ID by looping through list of items and compare the ID
            boolean found = false;
            for (Entity e : items) {
                if (e.getID().contains(searchInput.toLowerCase())) {
                    found = true;
                    updateOperations.apply(e); // update the entity
                    break;
                }
            }
            if (!found) {
                System.out.println(searchInput + " is might not in the system.");
            }
        } else { // search with string
            ArrayList<Entity> highSim = new ArrayList<>();
            ArrayList<Entity> partMatch = new ArrayList<>();

            for (Entity e : items) {
                String itemString = e.getSearchString();
                // search for the entity with the string by looping through list of items and compare the string
                if (itemString.toLowerCase().contains(searchInput.toLowerCase())) { 
                    partMatch.add(e);
                }

                // calculate the similarity between the search input and the item string
                int distance = levenshteinDistance(searchInput, itemString);
                if (distance <= 20) {
                    highSim.add(e);
                }
            }

            if (!partMatch.isEmpty()) {
                if (partMatch.size() == 1) { // if there is only one match, update the entity
                    updateOperations.apply(partMatch.getFirst());
                } else { // if there are multiple matches, list and select the match
                    listAndSelect(partMatch, "Matched search:", updateOperations);
                }
            } else {
                if (!highSim.isEmpty()) { // if there is no match, list the relevant search
                    listAndSelect(highSim, "Relevant search:", updateOperations);
                } else { // if there is no relevant search, print the message
                    System.out.println(searchInput + " is might not in the system.");
                }
            }
        }
    }

    // list all items and select the item
    private void listAndSelect(ArrayList<? extends Entity> items, String message, Function<Entity, String> updateOperations) {
        int choice;
        do {
            System.out.println("\n" + message);
            printEntities((ArrayList<Entity>) items);
            System.out.println("0. Back");
            System.out.print("Select choice: ");
            choice = getChoice();

            // select the item
            if (choice > 0 && choice <= items.size()) { // if the choice is valid, update the entity
                String result = updateOperations.apply(items.get(choice - 1)); // update the entity
                if (result.equals("remove")) { // if the entity is removed by update operations, break the loop
                    return;
                }
            } else if (choice == 0) {
                assert true; // do nothing
            }  else {
                System.out.println("Invalid option. Please try again.");
            }
        }  while (choice != 0);
    }

    // select a choice or search for an entity
    private Entity selectChoiceOrSearch(ArrayList<? extends Entity> items, String entity, boolean back) {
        String choiceString;
        int choice;
        boolean searchName;
        while (true) {
            System.out.println("\nSelect " + entity + ":");
            printEntities((ArrayList<Entity>) items);
            
            // print the back option to leave the same if back is true
            if (back) {
                System.out.println("0. Leave the same");
            }

            System.out.print("Enter " + entity + " (choice or search): ");
            choiceString = scanner.nextLine();
            try {
                choice = Integer.parseInt(choiceString);
                searchName = false;
            } catch (NumberFormatException e) {
                if (choiceString.isEmpty()) {
                    System.out.println("Cannot be empty. Please try again.");
                    continue;
                }
                searchName = true;
                choice = -1;
            }

            if (searchName) { // search for the entity with the string
                return searchMenuReturn(choiceString, items);
            } else { // select the entity with the choice
                if (choice > 0 && choice <= items.size()) { // if the choice is valid, return the entity
                    return items.get(choice - 1);
                } else if (choice == 0) {
                    if (back) { // if back is true, return null, else will not break the loop even user enter 0
                        break;
                    }
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            }
        }
        return null;
    }

    // search for the entity but return the entity instead of updating
    private Entity searchMenuReturn(String searchInput, ArrayList<? extends Entity> items) {
        ArrayList<Entity> highSim = new ArrayList<>();
        ArrayList<Entity> partMatch = new ArrayList<>();

        // search for the entity with the string by looping through list of items and compare the string
        for (Entity e : items) {
            // search for the entity with the string by looping through list of items and compare the string
            String itemString = e.getSearchString();
            if (itemString.toLowerCase().contains(searchInput.toLowerCase())) {
                partMatch.add(e);
            }

            // calculate the similarity between the search input and the item string
            int distance = levenshteinDistance(searchInput, itemString);
            if (distance <= 20) {
                highSim.add(e);
            }
        }

        if (!partMatch.isEmpty()) {
            if (partMatch.size() == 1) { // if there is only one match, return the entity
                return partMatch.getFirst();
            } else { // if there are multiple matches, return list and select the match return
                return listAndSelectReturn(partMatch, "Matched search:");
            }
        } else {
            if (!highSim.isEmpty()) { // if there is no match, return the relevant search return
                return listAndSelectReturn(highSim, "Relevant search:");
            } else { // if there is no relevant search, print the message and return null
                System.out.println(searchInput + " is might not in the system.");
                return null;
            }
        }
    }

    // list all items and select the item but return the entity instead of updating
    private Entity listAndSelectReturn(ArrayList<? extends Entity> items, String message) {
        int choice;
        do {
            System.out.println("\n" + message);
            printEntities((ArrayList<Entity>) items);
            System.out.print("Select choice: ");
            choice = getChoice();

            if (choice > 0 && choice <= items.size()) { // if the choice is valid, return the entity
                return items.get(choice - 1);
            } else if (choice == 0) {
                assert true; // do nothing
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }  while (choice != 0);
        return null;
    }

    // add choice or search for the entity
    private ArrayList<Entity> addChoiceOrSearch(ArrayList<? extends Entity> entities, String entity, ArrayList<? extends Entity> initialEntities) {
        int choice = -1;
        boolean done = false;
        ArrayList<Entity> addedEntities = new ArrayList<>();

        // if there are initial entities, replace addedEntities with initialEntities
        if (initialEntities != null) {
            addedEntities = (ArrayList<Entity>) initialEntities;
        }

        do {
            if (done) {
                break;
            }

            if (!addedEntities.isEmpty()) { // if there are added entities, show the current added entities
                System.out.println("\nCurrent added " + entity + ":");
                printEntities(addedEntities);

                System.out.println("\nUpdate " + entity + " list:");
                System.out.println("1. Add " + entity.toLowerCase());
                System.out.println("2. Remove " + entity.toLowerCase());
                System.out.println("0. Finish");
                System.out.print("Enter choice: ");
                choice = getChoiceNoEmpty();

                switch (choice) {
                    case 1:
                        addEntity((ArrayList<Entity>) entities, addedEntities, entity, false);
                        break;
                    case 2:
                        removeEntity(addedEntities, entity);
                        break;
                    case 0:
                        done = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } else { // if there are no added entities, show the add entity menu
                addEntity((ArrayList<Entity>) entities, addedEntities, entity, true);
            }
        } while (choice != 0);
        return addedEntities;
    }

    // add entity to the list
    private void addEntity(ArrayList<Entity> entities, ArrayList<Entity> addedEntities, String entity, boolean once) {
        int _choice;
        do {
            System.out.println("\nAdd " + entity + ":");
            printEntities(entities);
            System.out.println("0. Finish");
            System.out.print("Enter " + entity + " (choice or search): ");
            _choice = getChoiceNoEmpty();
    
            if (_choice > 0 && _choice <= entities.size()) { // if the choice is valid, add the entity
                addedEntities.add(entities.get(_choice - 1));
            } else if (_choice == 0) {
                assert true; // do nothing
            } else {
                System.out.println("Invalid option. Please try again.");
            }

            if (once) {
                break;
            }
        } while (_choice != 0);
    }
    
    // remove entity from the list
    private void removeEntity(ArrayList<Entity> addedEntities, String entity) {
        int _choice;
        do {
            System.out.println("\nRemove " + entity + ":");
            printEntities(addedEntities);
            System.out.println("0. Finish");
            System.out.print("Enter " + entity + " (choice or search): ");
            _choice = getChoiceNoEmpty();
    
            if (_choice > 0 && _choice <= addedEntities.size()) { // if the choice is valid, remove the entity
                addedEntities.remove(addedEntities.get(_choice - 1));
            } else if (_choice == 0) {
                assert true; // do nothing
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        } while (_choice != 0 && !addedEntities.isEmpty());
    }

    // print the entities
    private void printEntities(ArrayList<Entity> entities) {
        int count = 1;
        for (Entity e : entities) {
            System.out.println(count + ". " + e.getSearchString());
            count += 1;
        }
    }

    // get the choice from the user
    private int getChoice() {
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // consume left over
            return choice;
        } catch (InputMismatchException e) {
            scanner.next(); // consume the invalid token
            return -1; // default value that will not exit the loop
        }
    }

    // get the choice from the user and make sure it is not empty
    private int getChoiceNoEmpty() {
        String choiceString = scanner.nextLine();
        try {
            return Integer.parseInt(choiceString);
        } catch (NumberFormatException e) {
            if (choiceString.isEmpty()) {
                System.out.println("Cannot be empty. Please try again.");
            }
            return -1;
        }
    }

    // get the string from the user and make sure it is not empty
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

    // get the integer from the user, make sure it is not empty and is a valid integer
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

    // get the double from the user, make sure it is not empty and is a valid double
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

    // get the decimal from the user, make sure it is not empty and is a valid decimal
    private BigDecimal getNextBigDecimal() {
        while (true) {
            String stringInput = scanner.nextLine();
            if (stringInput.isEmpty()) {
                System.out.print("Field cannot be empty. Please try again: ");
                continue;
            }
            try {
                return new BigDecimal(stringInput);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a decimal: ");
            }
        }
    }

    // get the string from the user, make sure it is a valid string or empty
    private String getNextLineEmpty(String defaultValue) {
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            return defaultValue;
        }
        return input;
    }

    // get the integer from the user, make sure it is a valid integer or empty
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

    // get the double from the user, make sure it is a valid double or empty
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

    // get the decimal from the user, make sure it is a valid decimal or empty
    private BigDecimal getNextBigDecimalEmpty(BigDecimal defaultValue) {
        while (true) {
            String stringInput = scanner.nextLine();
            if (stringInput.isEmpty()) {
                return defaultValue;
            } else {
                try {
                    return new BigDecimal(stringInput);
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Please enter a valid decimal or leave it empty: ");
                }
            }
        }
    }

    // levenshtein distance algorithm to calculate the steps to change one string to another
    private int levenshteinDistance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // degenerate cases
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;

        // calculate edit distance
        for (int i = 1; i <= a.length(); i++) {
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

    // Save data to files
    private void saveData() {
        try {
            FileHandler.writeObjectsToFile(carInterface.getAllCars(), "data/cars.obj");
            FileHandler.writeObjectsToFile(autoPartInterface.getAllAutoParts(), "data/parts.obj");
            FileHandler.writeObjectsToFile(userInterface.getAllUsers(), "data/users.obj");
            FileHandler.writeObjectsToFile(transactionInterface.getAllTransactions(), "data/transactions.obj");
            FileHandler.writeObjectsToFile(serviceInterface.getAllServices(), "data/services.obj");
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save date: " + e.getMessage());
        }
    }

    // Load data from files
    private void loadData() {
        try {
            this.carInterface.setCarList(FileHandler.readObjectsFromFile("data/cars.obj"));
            this.autoPartInterface.setAutoPartList(FileHandler.readObjectsFromFile("data/parts.obj"));
            this.userInterface.setUserList(FileHandler.readObjectsFromFile("data/users.obj"));
            this.transactionInterface.setTransactionList(FileHandler.readObjectsFromFile("data/transactions.obj"));
            this.serviceInterface.setServiceList(FileHandler.readObjectsFromFile("data/services.obj"));
        } catch (IOException e) {
            System.out.println("Failed to load data: " + e.getMessage());
        }
    }
}