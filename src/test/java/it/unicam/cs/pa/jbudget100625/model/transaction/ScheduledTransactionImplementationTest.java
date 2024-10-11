package it.unicam.cs.pa.jbudget100625.model.transaction;

import it.unicam.cs.pa.jbudget100625.model.scheduledTransaction.ScheduledTransaction;
import it.unicam.cs.pa.jbudget100625.model.scheduledTransaction.ScheduledTransactionImplementation;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Alex Citeroni
 */
public class ScheduledTransactionImplementationTest {
    private final ScheduledTransaction scheduledTransaction = new ScheduledTransactionImplementation(1, "description", new ArrayList<>());
    private LocalDate date;

    @Test
    public void getDescription() {
        assertEquals("description", scheduledTransaction.getDescription());
    }

    @Test
    public void setDescription() {
        scheduledTransaction.setDescription("New description");
        assertEquals("New description", scheduledTransaction.getDescription());
    }

    @Test
    public void getTransactions() {
        assertEquals(new ArrayList<>(), scheduledTransaction.getTransactions(date));
    }

    @Test
    public void isCompleted() {
        assertTrue(scheduledTransaction.isCompleted());
    }
}