import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class SalesTransaction {
    private final String transactionID;
    private final Date transactionDate;
    private String clientID;
    private String salespersonID;
    private ArrayList<Item> items;
    private Discount discount;
    private double total;
    private String notes;

    public SalesTransaction(String clientID, String salespersonID, ArrayList<Item> items, Discount discount, double total, String notes) {
        this.transactionID = setTransactionID();
        this.transactionDate = setTransactionDate();
        this.clientID = clientID;
        this.salespersonID = salespersonID;
        this.items = items;
        this.discount = discount;
        this.total = total;
        this.notes = notes;
    }

    public String getTransactionID() {
        return transactionID;
    }

    private String setTransactionID() {
        int number = (int) (Math.random() * 10000);
        return "t-" + number;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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
                ", total=" + total +
                ", notes='" + notes + '\'' +
                '}';
    }
}
