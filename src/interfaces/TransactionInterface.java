package interfaces;

import core.Transaction;

import java.util.ArrayList;

public interface TransactionInterface {
    void setTransactionList(ArrayList<Object> salesTransactionList);

    // Add a new sales transaction
    void addTransaction(Transaction transaction);

    // Retrieve all transactions
    ArrayList<Transaction> getAllTransactions();

    // Update a transaction
    void updateTransaction(Transaction transaction);

    // Remove a transaction (Soft delete)
    void removeTransaction(Transaction transaction);
}
