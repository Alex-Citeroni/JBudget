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

import it.unicam.cs.pa.jbudget100625.controller.persistence.account.AccountRegistry;
import it.unicam.cs.pa.jbudget100625.model.movement.Movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static it.unicam.cs.pa.jbudget100625.model.account.AccountType.ASSETS;
import static it.unicam.cs.pa.jbudget100625.model.movement.MovementType.CREDITS;

/**
 * @author Alex Citeroni
 */
public class AccountImplementation implements Account {
    private final int id;
    private AccountType accountType;
    private double balance, openingBalance;
    private String name, description;
    private List<Movement> movements;
    private static AccountRegistry<Account> registry;

    public AccountImplementation(int id, AccountType accountType, String name, String description, double openingBalance) {
        this.id = id;
        this.accountType = accountType;
        this.name = name;
        this.description = description;
        this.openingBalance = openingBalance;
        this.balance = openingBalance;
        this.movements = new ArrayList<>();
    }

    public static AccountRegistry<Account> getRegistry() {
        if (registry == null) registry = new AccountRegistry<>(AccountImplementation::new);
        return registry;
    }

    public static Account getInstance(int id, AccountType accountType, String name, String description, double openingBalance) {
        return getRegistry().getAccountInstance(id, accountType, name, description, openingBalance);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
    public int getId() {
        return this.id;
    }

    @Override
    public AccountType getType() {
        return this.accountType;
    }

    @Override
    public void setType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public double getOpeningBalance() {
        return this.openingBalance;
    }

    @Override
    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }

    @Override
    public double getBalance() {
        if (!movements.isEmpty()) {
            balance = 0;
            movements.forEach(movement -> {
                if (movement.getType() == CREDITS)
                    balance += movement.getAccount().getType() == ASSETS ? movement.getAmount() : -movement.getAmount();
                else
                    balance -= movement.getAccount().getType() == ASSETS ? movement.getAmount() : -movement.getAmount();
            });
        }
        return this.balance + openingBalance;
    }

    @Override
    public List<Movement> getMovements() {
        return this.movements;
    }

    @Override
    public List<Movement> getMovements(Predicate<Movement> predicate) {
        return this.movements.stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountImplementation that = (AccountImplementation) o;
        return id == that.id &&
                Double.compare(that.balance, balance) == 0 &&
                Double.compare(that.openingBalance, openingBalance) == 0 &&
                accountType == that.accountType &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(movements, that.movements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountType, balance, openingBalance, name, description, movements);
    }

    @Override
    public String toString() {
        return "AccountImplementation{" +
                "id=" + id +
                ", accountType=" + accountType +
                ", balance=" + balance +
                ", openingBalance=" + openingBalance +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", movements=" + movements +
                '}';
    }
}
