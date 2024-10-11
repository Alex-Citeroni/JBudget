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

import it.unicam.cs.pa.jbudget100625.model.account.Account;
import it.unicam.cs.pa.jbudget100625.model.budget.BudgetReport;
import it.unicam.cs.pa.jbudget100625.model.scheduledTransaction.ScheduledTransaction;
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;
import it.unicam.cs.pa.jbudget100625.model.transaction.Transaction;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Alex Citeroni
 */
public interface Controller {
    void save(File fileLedger, File fileBudget) throws IOException;

    void load(File fileLedger, File fileBudget) throws IOException;

    void addTransaction(Transaction transaction);

    void removeTransaction(Transaction transaction);

    List<Transaction> getTransactions();

    List<Transaction> getTransactions(Predicate<Transaction> predicate);

    List<Account> getAccounts() throws IOException;

    void addAccount(Account account);

    void removeAccount(Account account);

    List<Tag> getLedgerTags();

    void addLedgerTag(Tag tag);

    void removeLedgerTag(Tag tag);

    List<ScheduledTransaction> getScheduledTransaction();

    void addScheduledTransaction(ScheduledTransaction scheduledTransaction);

    void removeScheduledTransaction(ScheduledTransaction scheduledTransaction);

    void schedule(LocalDate date);

    List<Tag> budgetTags();

    void setBudget(Tag tag, double expected);

    void removeBudget(Tag tag);

    double getBudget(Tag tag);

    Predicate<Transaction> getPredicate();

    void setPredicate(Predicate<Transaction> predicate);

    BudgetReport generateReport();
}
