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

package it.unicam.cs.pa.jbudget100625.model.tag;

import it.unicam.cs.pa.jbudget100625.controller.persistence.tag.TagRegistry;

import java.util.Objects;

/**
 * @author Alex Citeroni
 */
public class TagImplementation implements Tag {
    private int id;
    private String name, description;
    private static TagRegistry<Tag> registry;

    public TagImplementation(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static TagRegistry<Tag> getRegistry() {
        if (registry == null) registry = new TagRegistry<>(TagImplementation::new);
        return registry;
    }

    public static Tag getInstance(int id, String name, String description) {
        return getRegistry().getTagInstance(id, name, description);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagImplementation that = (TagImplementation) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "TagImplementation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
