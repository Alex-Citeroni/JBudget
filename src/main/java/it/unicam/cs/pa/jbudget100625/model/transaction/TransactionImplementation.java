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

package it.unicam.cs.pa.jbudget100625.model.transaction;

import it.unicam.cs.pa.jbudget100625.controller.persistence.transaction.TransactionRegistry;
import it.unicam.cs.pa.jbudget100625.model.movement.Movement;
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static it.unicam.cs.pa.jbudget100625.model.movement.MovementType.CREDITS;

/**
 * @author Alex Citeroni
 */
public class TransactionImplementation implements Transaction {
    private int id;
    private List<Movement> movements;
    private List<Tag> tags;
    private LocalDate date;
    private static TransactionRegistry<Transaction> registry;

    public TransactionImplementation(int id, LocalDate date, List<Tag> tags, List<Movement> movements) {
        this.id = id;
        this.date = date;
        this.tags = tags;
        this.movements = movements;
    }

    public static TransactionRegistry<Transaction> getRegistry() {
        if (registry == null) registry = new TransactionRegistry<>(TransactionImplementation::new);
        return registry;
    }

    public static Transaction getInstance(int id, LocalDate date, List<Tag> tags, List<Movement> movements) {
        return getRegistry().getTransactionInstance(id, date, tags, movements);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public List<Tag> tags() {
        return this.tags;
    }

    @Override
    public void addTag(Tag tag) {
        this.tags.add(tag);
        movements.forEach(movement -> movement.addTag(tag));
    }

    @Override
    public void removeTag(Tag tag) {
        this.tags.remove(tag);
        movements.forEach(movement -> movement.removeTag(tag));
    }

    @Override
    public LocalDate getDate() {
        return this.date;
    }

    @Override
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public List<Movement> movements() {
        return this.movements;
    }

    @Override
    public void addMovement(Movement movement) {
        this.movements.add(movement);
        movement.getTags().stream().filter(tag -> !tags.contains(tag)).forEach(tag -> this.tags.add(tag));
    }

    @Override
    public void removeMovement(Movement movement) {
        this.movements.remove(movement);
        int count = 0;
        for (Tag tag : movement.getTags()) {
            count += movements.stream().filter(temp -> temp.getTags().contains(tag)).count();
            if (count == 0) tags.remove(tag);
        }
    }

    @Override
    public double getTotalAmount() {
        return movements.stream().mapToDouble(movement -> movement.getType() == CREDITS ? movement.getAmount() : -movement.getAmount()).sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionImplementation that = (TransactionImplementation) o;
        return id == that.id &&
                Objects.equals(movements, that.movements) &&
                Objects.equals(tags, that.tags) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movements, tags, date);
    }

    @Override
    public String toString() {
        return "TransactionImplementation{" +
                "id=" + id +
                ", movements=" + movements +
                ", tags=" + tags +
                ", date=" + date +
                '}';
    }
}
