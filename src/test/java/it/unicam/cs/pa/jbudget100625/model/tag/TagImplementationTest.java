package it.unicam.cs.pa.jbudget100625.model.tag;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Alex Citeroni
 */
public class TagImplementationTest {
    private final Tag tag = new TagImplementation(1,"name", "description");

    @Test
    public void getName() {
        assertEquals("name", tag.getName());
    }

    @Test
    public void getDescription() {
        assertEquals("description", tag.getDescription());
    }
}
