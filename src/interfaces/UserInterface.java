package interfaces;

import core.user.*;

import java.util.ArrayList;

public interface UserInterface {
    void setUserList(ArrayList<Object> userList);

    // Retrieve all users
    ArrayList<User> getAllUsers();

    // Add a new user
    void addUser(User user);

    // Update user details
    void updateUser(User user);

    // Remove a user (Soft delete)
    void removeUser(User user);

    ArrayList<Client> getAllClients();

    ArrayList<Employee> getAllEmployees();

    ArrayList<Salesperson> getAllSalespersons();

    ArrayList<Mechanic> getAllMechanics();

    ArrayList<Manager> getAllMangers();
}
