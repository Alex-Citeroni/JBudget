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

package it.unicam.cs.pa.jbudget100625.controller.persistence.account;

import it.unicam.cs.pa.jbudget100625.model.account.AccountType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Alex Citeroni
 */
public class AccountRegistry<T> {
    private Map<Integer, T> registry = new HashMap<>();
    private AccountRegistryService<Integer, AccountType, String, String, Double, T> factory;

    public AccountRegistry(AccountRegistryService<Integer, AccountType, String, String, Double, T> factory) {
        this.factory = factory;
    }

    public T getAccountInstance(int id, AccountType accountType, String name, String description, double openingBalance) {
        if (registry.containsKey(id)) return registry.get(id);
        T account = factory.apply(id, accountType, name, description, openingBalance);
        registry.put(id, account);
        return account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountRegistry<?> that = (AccountRegistry<?>) o;
        return Objects.equals(registry, that.registry) &&
                Objects.equals(factory, that.factory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registry, factory);
    }

    @Override
    public String toString() {
        return "AccountRegistry{" +
                "registry=" + registry +
                ", factory=" + factory +
                '}';
    }
}
