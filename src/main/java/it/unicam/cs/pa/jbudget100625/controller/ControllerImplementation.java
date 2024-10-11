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

package it.unicam.cs.pa.jbudget100625.controller;

import it.unicam.cs.pa.jbudget100625.controller.persistence.JsonPersistenceManager;
import it.unicam.cs.pa.jbudget100625.controller.persistence.PersistenceManager;
import it.unicam.cs.pa.jbudget100625.model.account.Account;
import it.unicam.cs.pa.jbudget100625.model.budget.*;
import it.unicam.cs.pa.jbudget100625.model.ledger.Ledger;
import it.unicam.cs.pa.jbudget100625.model.ledger.LedgerImplementation;
import it.unicam.cs.pa.jbudget100625.model.scheduledTransaction.ScheduledTransaction;
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;
import it.unicam.cs.pa.jbudget100625.model.transaction.Transaction;
import javafx.scene.control.TextFormatter.Change;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author Alex Citeroni
 */
public class ControllerImplementation implements Controller {
    private PersistenceManager persistenceManager = new JsonPersistenceManager();
    private BudgetManager budgetManager;
    private Budget budget;
    private Ledger ledger;

    public ControllerImplementation() {
        ledger = new LedgerImplementation(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        budget = new BudgetImplementation(new ArrayList<>(), new ArrayList<>());
        budgetManager = new BudgetManagerImplementation();
    }

    @Override
    public void save(File fileLedger, File fileBudget) throws IOException {
        persistenceManager.saveLedger(ledger, fileLedger);
        persistenceManager.saveBudget(budget, fileBudget);
    }

    @Override
    public void load(File fileLedger, File fileBudget) throws IOException {
        if (fileLedger.exists() && fileLedger.length() != 0)
            ledger = persistenceManager.loadLedger(fileLedger);
        else return;
        if (fileBudget.exists() && fileBudget.length() != 0)
            budget = persistenceManager.loadBudget(fileBudget);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        this.ledger.addTransaction(transaction);
    }

    @Override
    public void removeTransaction(Transaction transaction) {
        this.ledger.removeTransaction(transaction);
    }

    @Override
    public List<Transaction> getTransactions() {
        return this.ledger.getTransactions();
    }

    @Override
    public List<Transaction> getTransactions(Predicate<Transaction> predicate) {
        return this.ledger.getTransactions(predicate);
    }

    @Override
    public List<Account> getAccounts() {
        return this.ledger.getAccounts();
    }

    @Override
    public void addAccount(Account account) {
        this.ledger.addAccount(account);
    }

    @Override
    public void removeAccount(Account account) {
        this.ledger.removeAccount(account);
    }

    @Override
    public List<Tag> getLedgerTags() {
        return this.ledger.getTags();
    }

    @Override
    public void addLedgerTag(Tag tag) {
        this.ledger.addTag(tag);
    }

    @Override
    public void removeLedgerTag(Tag tag) {
        this.ledger.removeTag(tag);
    }

    @Override
    public List<ScheduledTransaction> getScheduledTransaction() {
        return this.ledger.getScheduledTransaction();
    }

    @Override
    public void addScheduledTransaction(ScheduledTransaction scheduledTransaction) {
        this.ledger.addScheduledTransaction(scheduledTransaction);
    }

    @Override
    public void removeScheduledTransaction(ScheduledTransaction scheduledTransaction) {
        this.ledger.removeScheduledTransaction(scheduledTransaction);
    }

    @Override
    public void schedule(LocalDate date) {
        this.ledger.schedule(date);
    }

    @Override
    public List<Tag> budgetTags() {
        return this.budget.tags();
    }

    @Override
    public void setBudget(Tag tag, double expected) {
        this.budget.set(tag, expected);
    }

    @Override
    public void removeBudget(Tag tag) {
        this.budget.remove(tag);
    }

    @Override
    public double getBudget(Tag tag) {
        return this.budget.get(tag);
    }

    @Override
    public Predicate<Transaction> getPredicate() {
        return this.budget.getPredicate();
    }

    @Override
    public void setPredicate(Predicate<Transaction> predicate) {
        this.budget.setPredicate(predicate);
    }

    @Override
    public BudgetReport generateReport() {
        return this.budgetManager.generateReport(ledger, budget);
    }

    public static Change apply(Change change) {
        if (change.isContentChange()) {
            ParsePosition parsePosition = new ParsePosition(0);
            NumberFormat.getInstance().parse(change.getControlNewText(), parsePosition);
            if (parsePosition.getIndex() == 0 || parsePosition.getIndex() < change.getControlNewText().length())
                return null;
        }
        return change;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ControllerImplementation that = (ControllerImplementation) o;
        return Objects.equals(persistenceManager, that.persistenceManager) &&
                Objects.equals(budgetManager, that.budgetManager) &&
                Objects.equals(budget, that.budget) &&
                Objects.equals(ledger, that.ledger);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persistenceManager, budgetManager, budget, ledger);
    }

    @Override
    public String toString() {
        return "ControllerImplementation{" +
                "persistenceManager=" + persistenceManager +
                ", budgetManager=" + budgetManager +
                ", budget=" + budget +
                ", ledger=" + ledger +
                '}';
    }
}
