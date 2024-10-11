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

import it.unicam.cs.pa.jbudget100625.controller.persistence.movement.MovementRegistry;
import it.unicam.cs.pa.jbudget100625.model.account.Account;
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;
import it.unicam.cs.pa.jbudget100625.model.transaction.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author Alex Citeroni
 */
public class MovementImplementation implements Movement {
    private int id;
    private Account account;
    private String description;
    private MovementType type;
    private double amount;
    private List<Tag> tags;
    private Transaction transaction;
    private static MovementRegistry<Movement> registry;

    public MovementImplementation(int id, Account account, String description, MovementType type, List<Tag> tags, double amount) {
        this.id = id;
        this.type = type;
        this.tags = tags;
        this.amount = amount;
        this.account = account;
        this.description = description;
    }

    public static MovementRegistry<Movement> getRegistry() {
        if (registry == null) registry = new MovementRegistry<>(MovementImplementation::new);
        return registry;
    }

    public static Movement getInstance(int id, Account account, String description, MovementType type, List<Tag> tags, double amount) {
        return getRegistry().getMovementInstance(id, account, description, type, tags, amount);
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
    public MovementType getType() {
        return this.type;
    }

    @Override
    public double getAmount() {
        return this.amount;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public LocalDate getDate() {
        return this.transaction.getDate();
    }

    @Override
    public List<Tag> getTags() {
        return this.tags;
    }

    @Override
    public void addTag(Tag tag) {
        if (!tags.contains(tag)) this.tags.add(tag);
    }

    @Override
    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }

    @Override
    public Account getAccount() {
        return this.account;
    }

    @Override
    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public Transaction getTransaction() {
        return this.transaction;
    }

    @Override
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovementImplementation that = (MovementImplementation) o;
        return id == that.id &&
                Double.compare(that.amount, amount) == 0 &&
                Objects.equals(account, that.account) &&
                Objects.equals(description, that.description) &&
                type == that.type &&
                Objects.equals(tags, that.tags) &&
                Objects.equals(transaction, that.transaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, description, type, amount, tags, transaction);
    }

    @Override
    public String toString() {
        return "MovementImplementation{" +
                "id=" + id +
                ", account=" + account +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", tags=" + tags +
                ", transaction=" + transaction +
                '}';
    }
}
