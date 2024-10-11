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

package it.unicam.cs.pa.jbudget100625.model.budget;

import it.unicam.cs.pa.jbudget100625.model.tag.Tag;
import it.unicam.cs.pa.jbudget100625.model.transaction.Transaction;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author Alex Citeroni
 */
public class BudgetImplementation implements Budget {
    private List<Tag> tags;
    private List<Double> expected;
    private Predicate<Transaction> predicate;

    public BudgetImplementation(List<Tag> tags, List<Double> expected) {
        this.tags = tags;
        this.expected = expected;
    }

    @Override
    public List<Tag> tags() {
        return this.tags;
    }

    @Override
    public void set(Tag tag, double expected) {
        if (!tags.contains(tag)) {
            this.tags.add(tag);
            this.expected.add(expected);
        }
    }

    @Override
    public void remove(Tag tag) {
        this.expected.remove(expected.get(tags.indexOf(tag)));
        this.tags.remove(tag);
    }

    @Override
    public double get(Tag tag) {
        return this.expected.get(tags.indexOf(tag));
    }

    @Override
    public Predicate<Transaction> getPredicate() {
        return this.predicate;
    }

    @Override
    public void setPredicate(Predicate<Transaction> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BudgetImplementation that = (BudgetImplementation) o;
        return Objects.equals(tags, that.tags) &&
                Objects.equals(expected, that.expected) &&
                Objects.equals(predicate, that.predicate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tags, expected, predicate);
    }

    @Override
    public String toString() {
        return "BudgetImplementation{" +
                "tags=" + tags +
                ", expected=" + expected +
                ", predicate=" + predicate +
                '}';
    }
}
