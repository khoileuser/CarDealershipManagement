package operations;

import core.user.*;
import core.user.UserType;
import interfaces.UserInterface;
import java.util.ArrayList;

public class UserOperation implements UserInterface {
    private final ArrayList<User> userList;

    public UserOperation() {
        this.userList = new ArrayList<>();
    }

    public void setUserList(ArrayList<Object> userList) {
        for (Object o : userList) {
            if (o instanceof User) {
                this.userList.add((User) o);
            }
        }
    }

    @Override
    public void addUser(User user) {
        int lastID = 0;
        for (User u : userList) {
            int id = (int) Integer.parseInt(u.getUserID().replace("u-", ""));
            if (id > lastID) {
                lastID = id;
            }
        }
        lastID = lastID + 1;
        user.setUserID("u-" + lastID);
        userList.add(user);
        System.out.println("User added: " + user);
    }

    @Override
    public boolean loginUser(String username, String password) {
        return false;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return userList;
    }

    @Override
    public ArrayList<Client> getAllClients() {
        ArrayList<Client> clientList = new ArrayList<>();
        for (User u : userList) {
            if (u.getUserType() == UserType.CLIENT) {
                clientList.add((Client) u);
            }
        }
        return clientList;
    }

    @Override
    public ArrayList<Employee> getAllEmployees() {
        ArrayList<Employee> employeeList = new ArrayList<>();
        for (User u : userList) {
            if (u.getUserType() == UserType.SALESPERSON || u.getUserType() == UserType.MECHANIC) {
                employeeList.add((Employee) u);
            }
        }
        return employeeList;
    }

    @Override
    public ArrayList<Salesperson> getAllSalespersons() {
        ArrayList<Salesperson> salespersonList = new ArrayList<>();
        for (User u : userList) {
            if (u.getUserType() == UserType.SALESPERSON) {
                salespersonList.add((Salesperson) u);
            }
        }
        return salespersonList;
    }

    @Override
    public ArrayList<Mechanic> getAllMechanics() {
        ArrayList<Mechanic> mechanicList = new ArrayList<>();
        for (User u : userList) {
            if (u.getUserType() == UserType.MECHANIC) {
                mechanicList.add((Mechanic) u);
            }
        }
        return mechanicList;
    }

    @Override
    public ArrayList<Manager> getAllMangers() {
        ArrayList<Manager> managerList = new ArrayList<>();
        for (User u : userList) {
            if (u.getUserType() == UserType.MECHANIC) {
                managerList.add((Manager) u);
            }
        }
        return managerList;
    }

    @Override
    public void updateUser(User updatedUser) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserID().equals(updatedUser.getUserID())) {
                userList.set(i, updatedUser);
                System.out.println("User updated: " + updatedUser);
                return;
            }
        }
        System.out.println("User not found: " + updatedUser);
    }

    @Override
    public void removeUser(User user) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserID().equals(user.getUserID())) {
                userList.remove(i);
                System.out.println("User removed: " + user);
                return;
            }
        }
        System.out.println("User not found: " + user);
    }
}
