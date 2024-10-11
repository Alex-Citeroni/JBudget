package it.unicam.cs.pa.jbudget100625.model.movement;

import it.unicam.cs.pa.jbudget100625.model.account.Account;
import it.unicam.cs.pa.jbudget100625.model.account.AccountImplementation;
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;
import it.unicam.cs.pa.jbudget100625.model.tag.TagImplementation;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static it.unicam.cs.pa.jbudget100625.model.account.AccountType.ASSETS;
import static it.unicam.cs.pa.jbudget100625.model.account.AccountType.LIABILITIES;
import static it.unicam.cs.pa.jbudget100625.model.movement.MovementType.DEBITS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Alex Citeroni
 */
public class MovementImplementationTest {
    private final List<Tag> tags = new ArrayList<>();
    private final Account account = new AccountImplementation(1,LIABILITIES, "Conto corrente", "Banca di Spagna", 100.0);
    private final Movement movement = new MovementImplementation(1,account, "a", DEBITS, tags, 10);
    private final Tag tag = new TagImplementation(1,"Galaxy S20", "Samsung");

    @Test
    public void setDescription() {
        movement.setDescription("new description");
        assertEquals("new description", movement.getDescription());
    }

    @Test
    public void type() {
        assertEquals(DEBITS, movement.getType());
    }

    @Test
    public void setAmount() {
        movement.setAmount(20.0);
        assertEquals(20.0, movement.getAmount(), 0);
    }

    @Test
    public void getTransaction() {
        assertNull(movement.getTransaction());
    }

    @Test
    public void tags() {
        assertEquals(tags, movement.getTags());
    }

    @Test
    public void addTag() {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tag);
        tags.add(tag);
        assertEquals(tagList, movement.getTags());
    }

    @Test
    public void removeTag() {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tag);
        tags.add(tag);
        tags.remove(tag);
        tagList.remove(tag);
        assertEquals(tagList, movement.getTags());
    }

    @Test
    public void setAccount() {
        Account temp = new AccountImplementation(1,ASSETS, "Carta prepagata", "Hype", 1000.0);
        movement.setAccount(temp);
        assertEquals(temp, movement.getAccount());
    }
}
