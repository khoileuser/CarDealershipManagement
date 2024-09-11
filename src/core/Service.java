package core;

import core.items.AutoPart;

import java.io.Serial;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

public class Service implements Serializable, Entity {
    @Serial
    private static final long serialVersionUID = 11L;

    // Attribute
    private String serviceID;
    private final Date serviceDate;
    private String clientID;
    private String mechanicID;
    private String carID;
    private String serviceType;
    private ArrayList<AutoPart> replacedParts;
    private BigDecimal serviceCost;
    private String notes;

    public Service(String clientID, String mechanicID, String carID, String serviceType, BigDecimal serviceCost, String notes) {
        this.serviceDate = setServiceDate();
        this.clientID = clientID;
        this.mechanicID = mechanicID;
        this.carID = carID;
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

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
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

    public StringBuilder getStringParts() {
        StringBuilder parts = new StringBuilder();
        for (AutoPart p : replacedParts) {
            parts.append(p.getSearchString()).append(", ");
        }
        return new StringBuilder(parts.substring(0, parts.length() - 2));
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceID='" + serviceID + '\'' +
                ", serviceDate=" + serviceDate +
                ", clientID='" + clientID + '\'' +
                ", mechanicID='" + mechanicID + '\'' +
                ", carID='" + carID + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", replacedParts=" + replacedParts +
                ", serviceCost=" + serviceCost +
                ", notes='" + notes + '\'' +
                '}';
    }

    @Override
    public String getSearchString() {
        return getStringServiceDate() + " | " + serviceType + " | Parts: " + getStringParts();
    }
}
