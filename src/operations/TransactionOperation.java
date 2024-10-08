package operations;

import core.Transaction;
import core.items.Car;
import core.items.Item;
import interfaces.TransactionInterface;

import java.util.ArrayList;

public class TransactionOperation implements TransactionInterface {
    private final ArrayList<Transaction> transactionList;

    public TransactionOperation() {
        this.transactionList = new ArrayList<>();
    }

    // set transactionList from reading objects from file
    public void setTransactionList(ArrayList<Object> transactionList) {
        for (Object o : transactionList) {
            if (o instanceof Transaction) {
                this.transactionList.add((Transaction) o);
            }
        }
    }

    // add transaction to transactionList
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
        for (Item i : transaction.getItems()) {
            if (i instanceof Car) {
                ((Car) i).setStatus("sold");
            }
        }
        System.out.println("Transaction added: " + transaction.getSearchString());
    }

    @Override
    public ArrayList<Transaction> getAllTransactions() {
        return transactionList;
    }

    // update a transaction in transactionList
    @Override
    public void updateTransaction(Transaction updatedTransaction) {
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).getTransactionID().equals(updatedTransaction.getTransactionID())) {
                for (Item e : updatedTransaction.getItems()) {
                    if (e instanceof Car) {
                        ((Car) e).setStatus("sold");
                    }
                }
                transactionList.set(i, updatedTransaction);
                System.out.println("Transaction updated: " + updatedTransaction.getSearchString());
                return;
            }
        }
        System.out.println("Transaction not found: " + updatedTransaction.getSearchString());
    }

    // remove a transaction from transactionList
    @Override
    public void removeTransaction(Transaction transaction) {
        for (int i = 0; i < transactionList.size(); i++) {
            if (transactionList.get(i).getTransactionID().equals(transaction.getTransactionID())) {
                transactionList.remove(i);
                System.out.println("Transaction removed: " + transaction.getSearchString());
                return;
            }
        }
        System.out.println("Transaction not found: " + transaction.getSearchString());
    }
}
