import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Service {
    //Atribute
    private final String serviceID;
    private Date serviceDate;
    private int clientID;
    private int mechanicID;
    private String serviceType;
    private ArrayList<AutoPart> replacedParts;
    private double serviceCost;
    private String notes;

    public Service(int clientID, int mechanicID, String serviceType, ArrayList<AutoPart> replacedParts, double serviceCost, String notes) {
        this.serviceID = setServiceID();
        this.serviceDate = setServiceDate();
        this.clientID = clientID;
        this.mechanicID = mechanicID;
        this.serviceType = serviceType;
        this.replacedParts = replacedParts;
        this.serviceCost = serviceCost;
        this.notes = notes;
    }

    private String setServiceID() {
        int number = (int) (Math.random() * 10000);
        return "s-" + number;
    }

    public String getServiceID() {
        return serviceID;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public String getStringServiceDate() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return df.format(serviceDate);
    }

    public Date setServiceDate() {
        return new Date();
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getMechanicID() {
        return mechanicID;
    }

    public void setMechanicID(int mechanicID) {
        this.mechanicID = mechanicID;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public ArrayList<AutoPart> getReplacedParts() {
        return replacedParts;
    }

    public void setReplacedParts(ArrayList<AutoPart> replacedParts) {
        this.replacedParts = replacedParts;
    }

    public double getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(double serviceCost) {
        this.serviceCost = serviceCost;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceID='" + serviceID + '\'' +
                ", serviceDate=" + serviceDate +
                ", clientID=" + clientID +
                ", mechanicID=" + mechanicID +
                ", serviceType='" + serviceType + '\'' +
                ", replacedParts=" + replacedParts +
                ", serviceCost=" + serviceCost +
                ", notes='" + notes + '\'' +
                '}';
    }
}
