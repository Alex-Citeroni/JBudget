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

package it.unicam.cs.pa.jbudget100625.controller.persistence.tag;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TagRegistry<T> {
    private Map<Integer, T> registry = new HashMap<>();
    private TagRegistryService<Integer, String, String, T> factory;

    public TagRegistry(TagRegistryService<Integer, String, String, T> factory) {
        this.factory = factory;
    }

    public T getTagInstance(int id, String name, String description) {
        if (registry.containsKey(id)) return registry.get(id);
        T tag = factory.apply(id, name, description);
        registry.put(id, tag);
        return tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagRegistry<?> that = (TagRegistry<?>) o;
        return Objects.equals(registry, that.registry) &&
                Objects.equals(factory, that.factory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registry, factory);
    }

    @Override
    public String toString() {
        return "TagRegistry{" +
                "registry=" + registry +
                ", factory=" + factory +
                '}';
    }
}
