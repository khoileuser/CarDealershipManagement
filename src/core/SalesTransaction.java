package core;

import java.io.Serial;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.Serializable;

import core.items.Item;
import core.user.Client;
import utils.Membership;

public class SalesTransaction implements Serializable {
    @Serial
    private static final long serialVersionUID = 10L;

    private String transactionID;
    private final Date transactionDate;
    private String clientID;
    private String salespersonID;
    private ArrayList<Item> items;
    private int discountPercentage;
    private BigDecimal totalAmount;
    private String notes;

    public SalesTransaction(String clientID, String salespersonID, String notes) {
        this.transactionDate = setTransactionDate();
        this.clientID = clientID;
        this.salespersonID = salespersonID;
        this.notes = notes;

        this.items = new ArrayList<>();
        this.discountPercentage = 0;
        this.totalAmount = new BigDecimal(0);
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public String getStringTransactionDate() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return df.format(transactionDate);
    }

    private Date setTransactionDate() {
        return new Date();
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getSalespersonID() {
        return salespersonID;
    }

    public void setSalespersonID(String salespersonID) {
        this.salespersonID = salespersonID;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
        this.totalAmount = new BigDecimal(0);
        for (Item i : items) {
            this.totalAmount = this.totalAmount.add(i.getPrice());
        }
    }

    public void addItem(Item item) {
        this.items.add(item);
        BigDecimal itemPrice = item.getPrice();
        this.totalAmount = this.totalAmount.add(itemPrice);
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String checkDiscount(ArrayList<Client> clientList) {
        for (Client c : clientList) {
            if (c.getUserID().equals(this.clientID)) {
                this.discountPercentage = Membership.determineMembership(c.getTotalSpending()).getDiscountPercentage();
            }
        }
        BigDecimal discountAmount = this.totalAmount.multiply(new BigDecimal(this.discountPercentage)).divide(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP);
        this.totalAmount = this.totalAmount.subtract(discountAmount);
        return "New total: " + this.totalAmount + ", saved: " + discountAmount;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        return "SaleTransaction{" +
                "transactionID='" + transactionID + '\'' +
                ", transactionDate=" + formatter.format(transactionDate) +
                ", clientID='" + clientID + '\'' +
                ", salespersonID='" + salespersonID + '\'' +
                ", items=" + items +
                ", discountPercentage=" + discountPercentage +
                ", totalAmount=" + totalAmount +
                ", notes='" + notes + '\'' +
                '}';
    }
}
