package it.unicam.cs.pa.jbudget100625.model.account;

import org.junit.Test;

import java.util.ArrayList;

import static it.unicam.cs.pa.jbudget100625.model.account.AccountType.ASSETS;
import static it.unicam.cs.pa.jbudget100625.model.account.AccountType.LIABILITIES;
import static org.junit.Assert.assertEquals;

/**
 * @author Alex Citeroni
 */
public class AccountImplementationTest {
    private final Account account = new AccountImplementation(1, ASSETS, "Name", "Description", 10.0);

    @Test
    public void getName() {
        assertEquals("Name", account.getName());
    }

    @Test
    public void setName() {
        account.setName("Nuovo nome");
        assertEquals("Nuovo nome", account.getName());
    }

    @Test
    public void getDescription() {
        assertEquals("Description", account.getDescription());
    }

    @Test
    public void setDescription() {
        account.setDescription("Nuova descrizione");
        assertEquals("Nuova descrizione", account.getDescription());
    }

    @Test
    public void getType() {
        assertEquals(ASSETS, account.getType());
    }

    @Test
    public void setType() {
        account.setType(LIABILITIES);
        assertEquals(LIABILITIES, account.getType());
    }

    @Test
    public void getOpeningBalance() {
        assertEquals(10.0, account.getOpeningBalance(), 10);
    }

    @Test
    public void setOpeningBalance() {
        account.setOpeningBalance(100.0);
        assertEquals(100.0, account.getOpeningBalance(), 100);
    }

    @Test
    public void getBalance() {
        assertEquals(10.0, account.getBalance(), 10);
    }

    @Test
    public void getMovements() {
        assertEquals(new ArrayList<>(), account.getMovements());
    }

    @Test
    public void testGetMovements() {
        assertEquals(new ArrayList<>(), account.getMovements(predicate -> predicate.getDescription().equals("descrizione")));
    }
}