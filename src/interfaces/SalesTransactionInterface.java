package interfaces;

import core.SalesTransaction;

import java.util.ArrayList;

public interface SalesTransactionInterface {
    void setSalesTransactionList(ArrayList<Object> salesTransactionList);

    // Add a new sales transaction
    void addSalesTransaction(SalesTransaction transaction);

    // Retrieve all transactions
    ArrayList<SalesTransaction> getAllSalesTransactions();

    // Update a transaction
    void updateSalesTransaction(SalesTransaction transaction);

    // Remove a transaction (Soft delete)
    void removeSalesTransaction(SalesTransaction transaction);
}
