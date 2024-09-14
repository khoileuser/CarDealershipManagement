package core.items;

import core.Entity;
import utils.Statistics;

import java.io.Serial;
import java.math.BigDecimal;

public class AutoPart extends Item implements Entity {
    @Serial
    private static final long serialVersionUID = 2L;

    // Attributes
    private String partID;
    private String partName;
    private String manufacturer;
    private String partNumber;
    private String condition; // new, used, refurbished
    private String warranty;
    private BigDecimal cost;
    private String notes;

    // Constructor
    public AutoPart(String partName, String manufacturer, String partNumber, String condition, String warranty, BigDecimal cost, String notes) {
        this.partName = partName;
        this.manufacturer = manufacturer;
        this.partNumber = partNumber;
        this.condition = condition;
        this.warranty = warranty;
        this.cost = cost;
        this.notes = notes;
    }

    // Getters and Setters
    public String getPartID() {
        return partID;
    }

    public void setPartID(String partID) { this.partID = partID; }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public BigDecimal getCost() {
        return cost;
    }

    @Override
    public BigDecimal getPrice() {return cost;}

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // toString method to display part information
    @Override
    public String toString() {
        return "AutoPart{" +
                "partID='" + partID + '\'' +
                ", partName='" + partName + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", partNumber='" + partNumber + '\'' +
                ", condition='" + condition + '\'' +
                ", warranty='" + warranty + '\'' +
                ", cost=" + cost +
                ", notes='" + notes + '\'' +
                '}';
    }

    @Override
    public String getID() {
        return partID;
    }

    @Override
    public String getSearchString() {
        return manufacturer + " " + partName + " (" + partNumber + ") | " + Statistics.numParse(getPrice());
    }
}
