package interfaces;

import core.Service;
import core.items.AutoPart;

import java.util.ArrayList;

public interface ServiceInterface {
    void setServiceList(ArrayList<Object> serviceList);

    void addService(Service service);

    ArrayList<Service> getAllServices();

    void updateService(Service updatedService);

    void removeService(Service service);

    boolean addReplacedPart(Service service, String autoPartName, ArrayList<AutoPart> autoPartList);
}
