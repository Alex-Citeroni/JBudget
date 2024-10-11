package it.unicam.cs.pa.jbudget100625.controller.persistence;

import it.unicam.cs.pa.jbudget100625.model.ledger.Ledger;
import it.unicam.cs.pa.jbudget100625.model.ledger.LedgerImplementation;
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;
import it.unicam.cs.pa.jbudget100625.model.tag.TagImplementation;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Citeroni
 */
public class JsonPersistenceManagerTest {
    private final File file = new File("prova.json");
    private final Tag tag = new TagImplementation(1, "Acquisto", "Bicicletta elettrica");
    private final List<Tag> tags = new ArrayList<>();
    private final JsonPersistenceManager jsonPersistenceManager = new JsonPersistenceManager();
    private final Ledger ledger = new LedgerImplementation(new ArrayList<>(), new ArrayList<>(), tags, new ArrayList<>());

    @Test
    public void save() throws IOException {
        ledger.addTag(tag);
        jsonPersistenceManager.saveLedger(ledger, file);
    }
}