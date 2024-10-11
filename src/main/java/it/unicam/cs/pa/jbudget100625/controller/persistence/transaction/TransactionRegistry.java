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

package it.unicam.cs.pa.jbudget100625.controller.persistence.transaction;

import it.unicam.cs.pa.jbudget100625.model.movement.Movement;
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TransactionRegistry<T> {
    private Map<Integer, T> registry = new HashMap<>();
    private TransactionRegistryService<Integer, LocalDate, List<Tag>, List<Movement>, T> factory;

    public TransactionRegistry(TransactionRegistryService<Integer, LocalDate, List<Tag>, List<Movement>, T> factory) {
        this.factory = factory;
    }

    public T getTransactionInstance(int id, LocalDate date, List<Tag> tags, List<Movement> movements) {
        if (registry.containsKey(id)) return registry.get(id);
        T transaction = factory.apply(id, date, tags, movements);
        registry.put(id, transaction);
        return transaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionRegistry<?> that = (TransactionRegistry<?>) o;
        return Objects.equals(registry, that.registry) &&
                Objects.equals(factory, that.factory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registry, factory);
    }

    @Override
    public String toString() {
        return "TransactionRegistry{" +
                "registry=" + registry +
                ", factory=" + factory +
                '}';
    }
}
