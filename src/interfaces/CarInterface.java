package interfaces;

import core.Service;
import core.items.Car;

import java.util.ArrayList;

public interface CarInterface {
    void setCarList(ArrayList<Object> carList);

    // Create a new car
    void addCar(Car car);

    // Retrieve all cars
    ArrayList<Car> getAllCars();

    ArrayList<Car> getAvailableCars();

    // Update car details
    void updateCar(Car car);

    // Remove a car (Soft delete)
    void removeCar(Car car);

    void removeService(Service service);
}
