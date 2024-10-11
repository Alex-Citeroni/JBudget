package it.unicam.cs.pa.jbudget100625.model.budget;

import it.unicam.cs.pa.jbudget100625.model.tag.Tag;
import it.unicam.cs.pa.jbudget100625.model.tag.TagImplementation;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Alex Citeroni
 */
public class BudgetImplementationTest {
    private final Tag tag = new TagImplementation(1,"Nome", "Descrizione");
    private final List<Tag> tags = new ArrayList<>();
    private final List<Double> expected = new ArrayList<>();
    private final Budget budget = new BudgetImplementation(tags, expected);

    @Test
    public void tags() {
        assertEquals(tags, budget.tags());
    }

    @Test
    public void set() {
        Budget bt = new BudgetImplementation(tags, expected);
        budget.set(tag, 10);
        bt.set(tag, 10);
        assertEquals(bt.tags(), budget.tags());
    }

    @Test
    public void get() {
        budget.set(tag, 10);
        assertEquals(10, budget.get(tag), 10);
    }

    @Test
    public void getPredicate() {
        assertNull(budget.getPredicate());
    }
}