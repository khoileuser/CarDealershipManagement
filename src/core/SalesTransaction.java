package core;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

import utils.Discount;

public class SalesTransaction {
    private String transactionID;
    private final Date transactionDate;
    private String clientID;
    private String salespersonID;
    private ArrayList<Item> items;
    private Discount discount;
    private double totalAmount;
    private String notes;

    public SalesTransaction(String transactionID, String clientID, String salespersonID, ArrayList<Item> items, Discount discount, double totalAmount, String notes) {
        this.transactionID = transactionID;
        this.transactionDate = setTransactionDate();
        this.clientID = clientID;
        this.salespersonID = salespersonID;
        this.items = items;
        this.discount = discount;
        this.totalAmount = totalAmount;
        this.notes = notes;
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

    public String getStringTransaction() {
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
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
                ", discount=" + discount +
                ", totalAmount=" + totalAmount +
                ", notes='" + notes + '\'' +
                '}';
    }
}
