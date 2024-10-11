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

import it.unicam.cs.pa.jbudget100625.model.ledger.Ledger;
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;
import it.unicam.cs.pa.jbudget100625.model.transaction.Transaction;

import java.util.*;

/**
 * @author Alex Citeroni
 */
public class BudgetReportImplementation implements BudgetReport {
    private Budget budget;
    private Ledger ledger;
    private Map<Tag, Double> report;

    public BudgetReportImplementation(Ledger ledger, Budget budget) {
        this.ledger = ledger;
        this.budget = budget;
        this.report = new HashMap<>();
    }

    @Override
    public List<Tag> tags() {
        return new ArrayList<>(report.keySet());
    }

    @Override
    public Map<Tag, Double> report() {
        budget.tags().forEach(tag -> report.put(tag, budget.get(tag) + ledger.getTransactions(transaction ->
        {
            for (Tag tag1 : transaction.tags())
                if (tag1.getName().equals(tag.getName()) && tag1.getDescription().equals(tag.getDescription()))
                    return true;
            return false;
        }).stream().mapToDouble(Transaction::getTotalAmount).sum()));
        return report;
    }

    @Override
    public Budget getBudget() {
        return this.budget;
    }

    @Override
    public double get(Tag tag) {
        return this.report.get(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BudgetReportImplementation that = (BudgetReportImplementation) o;
        return Objects.equals(budget, that.budget) &&
                Objects.equals(ledger, that.ledger) &&
                Objects.equals(report, that.report);
    }

    @Override
    public int hashCode() {
        return Objects.hash(budget, ledger, report);
    }

    @Override
    public String toString() {
        return "BudgetReportImplementation{" +
                "budget=" + budget +
                ", ledger=" + ledger +
                ", report=" + report +
                '}';
    }
}
