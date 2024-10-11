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

package it.unicam.cs.pa.jbudget100625.model.account;

import it.unicam.cs.pa.jbudget100625.model.movement.Movement;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author Alex Citeroni
 */
public interface Account {
    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    int getId();

    AccountType getType();

    void setType(AccountType accountType);

    double getOpeningBalance();

    void setOpeningBalance(double openingBalance);

    double getBalance();

    List<Movement> getMovements();

    List<Movement> getMovements(Predicate<Movement> predicate);
}
