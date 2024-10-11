/*
 *
 *  * Copyright (c) 2019. [Acme Corp]
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in
 *  * all copies or substantial portions of the Software.
 *
 */

package it.unicam.cs.pa.jbudget100625.model.scheduledTransaction;

import it.unicam.cs.pa.jbudget100625.controller.persistence.scheduledTransaction.ScheduledTransactionRegistry;
import it.unicam.cs.pa.jbudget100625.model.transaction.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author Alex Citeroni
 */
public class ScheduledTransactionImplementation implements ScheduledTransaction {
    private int id;
    private String description;
    private List<Transaction> transactions;
    private static ScheduledTransactionRegistry<ScheduledTransaction> registry;

    public ScheduledTransactionImplementation(int id, String description, List<Transaction> transactions) {
        this.id = id;
        this.description = description;
        this.transactions = transactions;
    }

    public static ScheduledTransactionRegistry<ScheduledTransaction> getRegistry() {
        if (registry == null) registry = new ScheduledTransactionRegistry<>(ScheduledTransactionImplementation::new);
        return registry;
    }

    public static ScheduledTransaction getInstance(int id, String description, List<Transaction> transactions) {
        return getRegistry().getScheduledTransactionInstance(id, description, transactions);
    }
    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public List<Transaction> getTransactions(LocalDate date) {
        List<Transaction> transactionList = transactions;
        transactionList.removeIf(transactions -> (transactions.getDate() != date));
        return transactionList;
    }

    @Override
    public boolean isCompleted() {
        return this.transactions.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledTransactionImplementation that = (ScheduledTransactionImplementation) o;
        return id == that.id &&
                Objects.equals(description, that.description) &&
                Objects.equals(transactions, that.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, transactions);
    }

    @Override
    public String toString() {
        return "ScheduledTransactionImplementation{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", transactions=" + transactions +
                '}';
    }
}
