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

package it.unicam.cs.pa.jbudget100625.controller.persistence.movement;

import it.unicam.cs.pa.jbudget100625.model.account.Account;
import it.unicam.cs.pa.jbudget100625.model.movement.MovementType;
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Alex Citeroni
 */
public class MovementRegistry<T> {
    private Map<Integer, T> registry = new HashMap<>();
    private MovementRegistryService<Integer, Account, String, MovementType, List<Tag>, Double, T> factory;

    public MovementRegistry(MovementRegistryService<Integer, Account, String, MovementType, List<Tag>, Double, T> factory) {
        this.factory = factory;
    }

    public T getMovementInstance(int id, Account account, String description, MovementType type, List<Tag> tags, double amount) {
        if (registry.containsKey(id)) return registry.get(id);
        T movement = factory.apply(id, account, description, type, tags, amount);
        registry.put(id, movement);
        return movement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovementRegistry<?> that = (MovementRegistry<?>) o;
        return Objects.equals(registry, that.registry) &&
                Objects.equals(factory, that.factory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registry, factory);
    }

    @Override
    public String toString() {
        return "MovementRegistry{" +
                "registry=" + registry +
                ", factory=" + factory +
                '}';
    }
}
