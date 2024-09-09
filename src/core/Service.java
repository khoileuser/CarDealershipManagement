package core;

import core.items.AutoPart;

import java.io.Serial;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

public class Service implements Serializable {
    @Serial
    private static final long serialVersionUID = 11L;

    // Attribute
    private String serviceID;
    private final Date serviceDate;
    private String clientID;
    private String mechanicID;
    private String serviceType;
    private ArrayList<AutoPart> replacedParts;
    private BigDecimal serviceCost;
    private String notes;

    public Service(String clientID, String mechanicID, String serviceType, BigDecimal serviceCost, String notes) {
        this.serviceDate = setServiceDate();
        this.clientID = clientID;
        this.mechanicID = mechanicID;
        this.serviceType = serviceType;
        this.serviceCost = serviceCost;
        this.notes = notes;

        this.replacedParts = new ArrayList<>();
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) { this.serviceID = serviceID; }

    public Date getServiceDate() {
        return serviceDate;
    }

    public String getStringServiceDate() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return df.format(serviceDate);
    }

    private Date setServiceDate() {
        return new Date();
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getMechanicID() {
        return mechanicID;
    }

    public void setMechanicID(String mechanicID) {
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

    public void addReplacedPart(AutoPart autoPart) {
        this.replacedParts.add(autoPart);
    }

    public BigDecimal getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(BigDecimal serviceCost) {
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
