package operations;

import core.Service;
import core.items.AutoPart;
import interfaces.ServiceInterface;

import java.util.ArrayList;

public class ServiceOperation implements ServiceInterface {
    private final ArrayList<Service> serviceList;

    public ServiceOperation() {
        this.serviceList = new ArrayList<>();
    }

    // set serviceList from reading objects from file
    public void setServiceList(ArrayList<Object> serviceList) {
        for (Object o : serviceList) {
            if (o instanceof Service) {
                this.serviceList.add((Service) o);
            }
        }
    }

    // add service to serviceList
    @Override
    public void addService(Service service) {
        int lastID = 0;
        for (Service s : serviceList) {
            int id = (int) Integer.parseInt(s.getServiceID().replace("s-", ""));
            if (id > lastID) {
                lastID = id;
            }
        }
        lastID = lastID + 1;
        service.setServiceID("s-" + lastID);
        serviceList.add(service);
        System.out.println("Service added: " + service.getSearchString());
    }

    // get all services from serviceList
    @Override
    public ArrayList<Service> getAllServices() {
        return serviceList;
    }

    // update a service in serviceList
    @Override
    public void updateService(Service updatedService) {
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).getServiceID().equals(updatedService.getServiceID())) {
                serviceList.set(i, updatedService);
                System.out.println("Service updated: " + updatedService.getSearchString());
                return;
            }
        }
        System.out.println("Service not found: " + updatedService.getSearchString());
    }

    // remove a service from serviceList
    @Override
    public void removeService(Service service) {
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).getServiceID().equals(service.getServiceID())) {
                serviceList.remove(i);
                System.out.println("Service removed: " + service.getSearchString());
                return;
            }
        }
        System.out.println("Service not found: " + service.getSearchString());
    }
}
