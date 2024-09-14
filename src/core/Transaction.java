package core;

import java.io.Serial;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.Serializable;

import core.items.AutoPart;
import core.items.Item;
import utils.Statistics;

public class Transaction implements Serializable, Entity {

    private String transactionID;
    private final Date transactionDate;
    private String clientID;
    private String salespersonID;
    private ArrayList<Item> items;
    private int discountPercentage;
    private BigDecimal totalAmount;
    private String notes;

    public Transaction(String clientID, String salespersonID, BigDecimal totalAmount, String notes) {
        this.transactionDate = setTransactionDate();
        this.clientID = clientID;
        this.salespersonID = salespersonID;
        this.notes = notes;

        this.items = new ArrayList<>();
        this.discountPercentage = 0;
        this.totalAmount = totalAmount;
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
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setDiscountPercentage(Membership membership) {
        this.discountPercentage = membership.getDiscountPercentage();
        if (membership != Membership.NONE) {
            System.out.println("\nClient has " + membership + " membership, gets " + membership.getDiscountPercentage() + "% off from auto parts.");
            BigDecimal partsTotal = new BigDecimal(0);
            for (Item i : this.items) {
                if (i instanceof AutoPart) {
                    partsTotal = partsTotal.add(i.getPrice());
                }
            }
            BigDecimal discountAmount = partsTotal.multiply(new BigDecimal(this.discountPercentage)).divide(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP);
            this.totalAmount = this.totalAmount.subtract(discountAmount);
            System.out.println("New total amount: " + Statistics.numParse(this.totalAmount) + ", saved: " + Statistics.numParse(discountAmount));
        }
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

    public StringBuilder getStringItems() {
        StringBuilder parts = new StringBuilder();
        for (Item i : items) {
            parts.append(i.getSearchString()).append("; ");
        }
        StringBuilder re;
        try {
            re = new StringBuilder(parts.substring(0, parts.length() - 2));
        } catch (StringIndexOutOfBoundsException _) {
            re = new StringBuilder("0");
        }
        return re;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID='" + transactionID + '\'' +
                ", transactionDate=" + transactionDate +
                ", clientID='" + clientID + '\'' +
                ", salespersonID='" + salespersonID + '\'' +
                ", items=" + items +
                ", discountPercentage=" + discountPercentage +
                ", totalAmount=" + totalAmount +
                ", notes='" + notes + '\'' +
                '}';
    }

    @Override
    public String getID() {
        return transactionID;
    }

    @Override
    public String getSearchString() {
        return getStringTransactionDate() + " | " + "Items: " + getStringItems();
    }
}
