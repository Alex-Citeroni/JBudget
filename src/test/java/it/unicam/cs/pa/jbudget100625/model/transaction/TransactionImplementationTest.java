package it.unicam.cs.pa.jbudget100625.model.transaction;

import it.unicam.cs.pa.jbudget100625.model.account.Account;
import it.unicam.cs.pa.jbudget100625.model.account.AccountImplementation;
import it.unicam.cs.pa.jbudget100625.model.account.AccountType;
import it.unicam.cs.pa.jbudget100625.model.movement.Movement;
import it.unicam.cs.pa.jbudget100625.model.movement.MovementImplementation;
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;
import it.unicam.cs.pa.jbudget100625.model.tag.TagImplementation;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static it.unicam.cs.pa.jbudget100625.model.movement.MovementType.DEBITS;
import static org.junit.Assert.assertEquals;

/**
 * @author Alex Citeroni
 */
public class TransactionImplementationTest {
    private final Account account = new AccountImplementation(1, AccountType.ASSETS, "Nome", "Descrizione", 10);
    private final List<Movement> movements = new ArrayList<>();
    private final List<Tag> tags = new ArrayList<>();
    private LocalDate date;
    private final Transaction transaction = new TransactionImplementation(1, date, new ArrayList<>(), movements);
    private final Movement movement = new MovementImplementation(1, account, "A", DEBITS, tags, 10);

    @Test
    public void tags() {
        assertEquals(new ArrayList<>(), transaction.tags());
    }

    @Test
    public void removeTag() {
        Tag tag = new TagImplementation(1, "Galaxy S20", "Samsung");
        transaction.addTag(tag);
        transaction.removeTag(tag);
        assertEquals(tags, transaction.tags());
    }

    @Test
    public void setDate() {
        transaction.setDate(date);
        assertEquals(date, transaction.getDate());
    }

    @Test
    public void movements() {
        assertEquals(movements, transaction.movements());
    }

    @Test
    public void addMovement() {
        transaction.addMovement(movement);
        assertEquals(movements, transaction.movements());
    }

    @Test
    public void removeMovement() {
        transaction.addMovement(movement);
        transaction.removeMovement(movement);
        assertEquals(movements, transaction.movements());
    }

    @Test
    public void getTotalAmount() {
        assertEquals(0.0, transaction.getTotalAmount(), 0);
    }
}