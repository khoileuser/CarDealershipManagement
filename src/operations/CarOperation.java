package operations;

import core.Service;
import core.items.Car;
import interfaces.CarInterface;
import java.util.ArrayList;

public class CarOperation implements CarInterface {
    private final ArrayList<Car> carList;

    public CarOperation() {
        this.carList = new ArrayList<>();
    }

    // set carList from reading objects from file
    public void setCarList(ArrayList<Object> carList) {
        for (Object o : carList) {
            if (o instanceof Car) {
                this.carList.add((Car) o);
            }
        }
    }

    // add car to carList
    @Override
    public void addCar(Car car) {
        int lastID = 0;
        for (Car c : carList) {
            int id = (int) Integer.parseInt(c.getCarID().replace("c-", ""));
            if (id > lastID) {
                lastID = id;
            }
        }
        lastID = lastID + 1;
        car.setCarID("c-" + lastID);
        carList.add(car);
        System.out.println("Car added: " + car.getSearchString());
    }

    // get all cars from carList
    @Override
    public ArrayList<Car> getAllCars() {
        return carList;
    }

    // get all available cars from carList
    @Override
    public ArrayList<Car> getAvailableCars() {
        ArrayList<Car> availableCars = new ArrayList<>();
        for (Car c : carList) {
            if (c.getStatus().equals("available")) {
                availableCars.add(c);
            }
        }
        return availableCars;
    }

    // update a car in carList
    @Override
    public void updateCar(Car updatedCar) {
        for (int i = 0; i < carList.size(); i++) {
            if (carList.get(i).getCarID().equals(updatedCar.getCarID())) {
                updatedCar.setCarID(carList.get(i).getCarID());
                carList.set(i, updatedCar);
                System.out.println("Car updated: " + updatedCar.getSearchString());
                return;
            }
        }
        System.out.println("Car not found: " + updatedCar.getSearchString());
    }

    // remove a car from carList
    @Override
    public void removeCar(Car car) {
        for (int i = 0; i < carList.size(); i++) {
            if (carList.get(i).getCarID().equals(car.getCarID())) {
                carList.remove(i);
                System.out.println("Car removed: " + car.getSearchString());
                return;
            }
        }
        System.out.println("Car not found: " + car.getSearchString());
    }

    // add service from associated car's service history
    @Override
    public void removeService(Service service) {
        for (Car c : carList) {
            if (c.getCarID().equals(service.getCarID())) {
                for (Service s : c.getServicesHistory()) {
                    if (s.getServiceID().equals(service.getServiceID())) {
                        c.removeService(service);
                        System.out.println("Service removed from: " + c.getSearchString());
                        break;
                    }
                }
            }
        }
    }
}
