package it.unicam.cs.pa.jbudget100625.model.budget;

import it.unicam.cs.pa.jbudget100625.model.ledger.Ledger;
import it.unicam.cs.pa.jbudget100625.model.ledger.LedgerImplementation;
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Alex Citeroni
 */
public class BudgetReportImplementationTest {
    private final List<Tag> tags = new ArrayList<>();
    private final List<Double> expected = new ArrayList<>();
    private final Ledger ledger = new LedgerImplementation(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    private final Budget budget = new BudgetImplementation(tags, expected);
    private final BudgetReport budgetReport = new BudgetReportImplementation(ledger, budget);

    @Test
    public void getBudget() {
        assertEquals(budget, budgetReport.getBudget());
    }
}
