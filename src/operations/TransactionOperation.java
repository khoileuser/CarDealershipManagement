package operations;

import core.Transaction;
import interfaces.TransactionInterface;

import java.util.ArrayList;

public class TransactionOperation implements TransactionInterface {
    private final ArrayList<Transaction> transactionList;

    public TransactionOperation() {
        this.transactionList = new ArrayList<>();
    }

    public void setTransactionList(ArrayList<Object> transactionList) {
        for (Object o : transactionList) {
            if (o instanceof Transaction) {
                this.transactionList.add((Transaction) o);
            }
        }
    }

    @Override
    public void addTransaction(Transaction transaction) {
        int lastID = 0;
        for (Transaction s : transactionList) {
            int id = (int) Integer.parseInt(s.getTransactionID().replace("t-", ""));
            if (id > lastID) {
                lastID = id;
            }
        }
        lastID = lastID + 1;
        transaction.setTransactionID("t-" + lastID);
        transactionList.add(transaction);
        System.out.println("Transaction added: " + transaction);
    }

    @Override
    public ArrayList<Transaction> getAllTransactions() {
        return transactionList;
    }

    @Override
    public void updateTransaction(Transaction updatedTransaction) {
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).getTransactionID().equals(updatedTransaction.getTransactionID())) {
                transactionList.set(i, updatedTransaction);
                System.out.println("Transaction updated: " + updatedTransaction);
                return;
            }
        }
        System.out.println("Transaction not found: " + updatedTransaction);
    }

    @Override
    public void removeTransaction(Transaction transaction) {
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).getTransactionID().equals(transaction.getTransactionID())) {
                transactionList.remove(i);
                System.out.println("Transaction removed: " + transaction);
                return;
            }
        }
        System.out.println("Transaction not found: " + transaction);
    }
}
