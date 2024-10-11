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

package it.unicam.cs.pa.jbudget100625.model.movement;

import it.unicam.cs.pa.jbudget100625.model.account.Account;
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;
import it.unicam.cs.pa.jbudget100625.model.transaction.Transaction;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Alex Citeroni
 */
public interface Movement {
    String getDescription();

    void setDescription(String description);

    MovementType getType();

    double getAmount();

    void setAmount(double amount);

    int getId();

    LocalDate getDate();

    List<Tag> getTags();

    void addTag(Tag tag);

    void removeTag(Tag tag);

    Account getAccount();

    void setAccount(Account account);

    Transaction getTransaction();

    void setTransaction(Transaction transaction);
}
