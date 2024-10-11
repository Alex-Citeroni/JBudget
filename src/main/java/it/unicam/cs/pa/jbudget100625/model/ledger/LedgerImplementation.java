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

package it.unicam.cs.pa.jbudget100625.model.ledger;

import it.unicam.cs.pa.jbudget100625.model.account.Account;
import it.unicam.cs.pa.jbudget100625.model.scheduledTransaction.ScheduledTransaction;
import it.unicam.cs.pa.jbudget100625.model.scheduledTransaction.ScheduledTransactionImplementation;
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;
import it.unicam.cs.pa.jbudget100625.model.transaction.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Alex Citeroni
 */
public class LedgerImplementation implements Ledger {
    private List<Account> accounts;
    private List<Transaction> transactions;
    private List<Tag> tags;
    private List<ScheduledTransaction> scheduledTransactions;

    public LedgerImplementation(List<Account> accounts, List<Transaction> transactions, List<Tag> tags, List<ScheduledTransaction> scheduledTransactions) {
        this.accounts = accounts;
        this.transactions = transactions;
        this.tags = tags;
        this.scheduledTransactions = scheduledTransactions;
    }

    @Override
    public List<Account> getAccounts() {
        return this.accounts;
    }

    @Override
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.movements().forEach(movement -> movement.getAccount().getMovements().add(movement));
    }

    @Override
    public void removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.movements().forEach(movement -> movement.getAccount().getMovements().remove(movement));
    }

    @Override
    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    @Override
    public List<Transaction> getTransactions(Predicate<Transaction> predicate) {
        return transactions.stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public List<Tag> getTags() {
        return this.tags;
    }

    @Override
    public Account addAccount(Account account) {
        this.accounts.add(account);
        return account;
    }

    @Override
    public void removeAccount(Account account) {
        // account.getMovements().forEach(movement -> movement.getTransaction().removeMovement(movement));
        this.accounts.remove(account);
    }

    @Override
    public Tag addTag(Tag tag) {
        this.tags.add(tag);
        return tag;
    }

    @Override
    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }

    @Override
    public List<ScheduledTransaction> getScheduledTransaction() {
        return this.scheduledTransactions;
    }

    @Override
    public void addScheduledTransaction(ScheduledTransaction scheduledTransaction) {
        this.scheduledTransactions.add(scheduledTransaction);
    }

    @Override
    public void removeScheduledTransaction(ScheduledTransaction scheduledTransaction) {
        this.scheduledTransactions.remove(scheduledTransaction);
    }

    @Override
    public void schedule(LocalDate date) {
        List<Transaction> transactionList = new ArrayList<>();
        this.transactions.forEach(transaction -> {
            transaction.setDate(date);
            transactionList.add(transaction);
        });
        addScheduledTransaction(new ScheduledTransactionImplementation(new Date().hashCode(), "Scheduled Transaction", transactionList));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LedgerImplementation that = (LedgerImplementation) o;
        return Objects.equals(accounts, that.accounts) &&
                Objects.equals(transactions, that.transactions) &&
                Objects.equals(tags, that.tags) &&
                Objects.equals(scheduledTransactions, that.scheduledTransactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accounts, transactions, tags, scheduledTransactions);
    }

    @Override
    public String toString() {
        return "LedgerImplementation{" +
                "accounts=" + accounts +
                ", transactions=" + transactions +
                ", tags=" + tags +
                ", scheduledTransactions=" + scheduledTransactions +
                '}';
    }
}
