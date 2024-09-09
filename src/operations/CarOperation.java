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

    public void setCarList(ArrayList<Object> carList) {
        for (Object o : carList) {
            if (o instanceof Car) {
                this.carList.add((Car) o);
            }
        }
    }

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
        System.out.println("Car added: " + car);
    }

    @Override
    public ArrayList<Car> getAllCars() {
        return carList;
    }

    @Override
    public void updateCar(Car updatedCar) {
        for (int i = 0; i < carList.size(); i++) {
            if (carList.get(i).getCarID().equals(updatedCar.getCarID())) {
                updatedCar.setCarID(carList.get(i).getCarID());
                carList.set(i, updatedCar);
                System.out.println("Car updated: " + updatedCar);
                return;
            }
        }
        System.out.println("Car not found: " + updatedCar);
    }

    @Override
    public void removeCar(Car car) {
        for (int i = 0; i < carList.size(); i++) {
            if (carList.get(i).getCarID().equals(car.getCarID())) {
                carList.remove(i);
                System.out.println("Car removed: " + car);
                return;
            }
        }
        System.out.println("Car not found: " + car);
    }

    @Override
    public void addService(Car car, Service service) {
        car.addService(service);
    }
}
