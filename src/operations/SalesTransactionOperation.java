package operations;

import core.SalesTransaction;
import core.user.User;
import interfaces.SalesTransactionInterface;

import java.math.BigDecimal;
import java.util.ArrayList;

public class SalesTransactionOperation implements SalesTransactionInterface {
    private final ArrayList<SalesTransaction> salesTransactionList;

    public SalesTransactionOperation() {
        this.salesTransactionList = new ArrayList<>();
    }

    public void setSalesTransactionList(ArrayList<Object> salesTransactionList) {
        for (Object o : salesTransactionList) {
            if (o instanceof SalesTransaction) {
                this.salesTransactionList.add((SalesTransaction) o);
            }
        }
    }

    @Override
    public void addSalesTransaction(SalesTransaction transaction) {
        int lastID = 0;
        for (SalesTransaction s : salesTransactionList) {
            int id = (int) Integer.parseInt(s.getTransactionID().replace("t-", ""));
            if (id > lastID) {
                lastID = id;
            }
        }
        lastID = lastID + 1;
        transaction.setTransactionID("t-" + lastID);
        salesTransactionList.add(transaction);
        System.out.println("Transaction added: " + transaction);
    }

    @Override
    public ArrayList<SalesTransaction> getAllSalesTransactions() {
        return salesTransactionList;
    }

    @Override
    public void updateSalesTransaction(SalesTransaction updatedTransaction) {
        for (int i = 0; i < salesTransactionList.size(); i++) {
            if (salesTransactionList.get(i).getTransactionID().equals(updatedTransaction.getTransactionID())) {
                salesTransactionList.set(i, updatedTransaction);
                System.out.println("Transaction updated: " + updatedTransaction);
                return;
            }
        }
        System.out.println("Transaction not found: " + updatedTransaction);
    }

    @Override
    public void removeSalesTransaction(SalesTransaction transaction) {
        for (int i = 0; i < salesTransactionList.size(); i++) {
            if (salesTransactionList.get(i).getTransactionID().equals(transaction.getTransactionID())) {
                salesTransactionList.remove(i);
                System.out.println("Transaction removed: " + transaction);
                return;
            }
        }
        System.out.println("Transaction not found: " + transaction);
    }
}
