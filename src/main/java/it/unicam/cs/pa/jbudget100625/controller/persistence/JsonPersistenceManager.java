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

package it.unicam.cs.pa.jbudget100625.controller.persistence;

import com.google.gson.GsonBuilder;
import it.unicam.cs.pa.jbudget100625.controller.persistence.account.AccountTypeAdapter;
import it.unicam.cs.pa.jbudget100625.controller.persistence.movement.MovementTypeAdapter;
import it.unicam.cs.pa.jbudget100625.controller.persistence.scheduledTransaction.ScheduledTransactionTypeAdapter;
import it.unicam.cs.pa.jbudget100625.controller.persistence.tag.TagTypeAdapter;
import it.unicam.cs.pa.jbudget100625.controller.persistence.transaction.TransactionTypeAdapter;
import it.unicam.cs.pa.jbudget100625.model.account.Account;
import it.unicam.cs.pa.jbudget100625.model.budget.Budget;
import it.unicam.cs.pa.jbudget100625.model.budget.BudgetImplementation;
import it.unicam.cs.pa.jbudget100625.model.ledger.Ledger;
import it.unicam.cs.pa.jbudget100625.model.ledger.LedgerImplementation;
import it.unicam.cs.pa.jbudget100625.model.movement.Movement;
import it.unicam.cs.pa.jbudget100625.model.scheduledTransaction.ScheduledTransaction;
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;
import it.unicam.cs.pa.jbudget100625.model.transaction.Transaction;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Alex Citeroni
 */
public class JsonPersistenceManager implements PersistenceManager {
    @Override
    public void saveLedger(Ledger ledger, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting().registerTypeAdapter(Account.class, new AccountTypeAdapter());
        gsonBuilder.setPrettyPrinting().registerTypeAdapter(Movement.class, new MovementTypeAdapter());
        gsonBuilder.setPrettyPrinting().registerTypeAdapter(Transaction.class, new TransactionTypeAdapter());
        gsonBuilder.setPrettyPrinting().registerTypeAdapter(Tag.class, new TagTypeAdapter());
        gsonBuilder.setPrettyPrinting().registerTypeAdapter(ScheduledTransaction.class, new ScheduledTransactionTypeAdapter());
        gsonBuilder.create().toJson(ledger, fileWriter);
        fileWriter.close();
    }

    @Override
    public void saveBudget(Budget budget, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        new GsonBuilder().setPrettyPrinting().create().toJson(budget, fileWriter);
        fileWriter.close();
    }

    @Override
    public Ledger loadLedger(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Account.class, new AccountTypeAdapter());
        gsonBuilder.registerTypeAdapter(Transaction.class, new TransactionTypeAdapter());
        gsonBuilder.registerTypeAdapter(Tag.class, new TagTypeAdapter());
        gsonBuilder.registerTypeAdapter(ScheduledTransaction.class, new ScheduledTransactionTypeAdapter());
        Ledger elements = gsonBuilder.create().fromJson(fileReader, LedgerImplementation.class);
        fileReader.close();
        return elements;
    }

    @Override
    public Budget loadBudget(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        Budget budget = new GsonBuilder().registerTypeAdapter(Tag.class, new TagTypeAdapter()).create().fromJson(fileReader, BudgetImplementation.class);
        fileReader.close();
        return budget;
    }
}
