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

import it.unicam.cs.pa.jbudget100625.model.budget.Budget;
import it.unicam.cs.pa.jbudget100625.model.ledger.Ledger;

import java.io.File;
import java.io.IOException;

/**
 * @author Alex Citeroni
 */
public interface PersistenceManager {
    void saveLedger(Ledger ledger, File file) throws IOException;

    void saveBudget(Budget budget, File file) throws IOException;

    Ledger loadLedger(File file) throws IOException;

    Budget loadBudget(File file) throws IOException;
}
