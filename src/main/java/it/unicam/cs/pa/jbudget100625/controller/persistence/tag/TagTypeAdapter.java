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

import com.google.gson.*;
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;
import it.unicam.cs.pa.jbudget100625.model.tag.TagImplementation;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TagTypeAdapter implements JsonSerializer<Tag>, JsonDeserializer<Tag> {
    private Set<Integer> alreadySaved = new HashSet<>();

    @Override
    public Tag deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        return TagImplementation.getInstance(jsonObject.get("id").getAsInt(), jsonObject.get("name").getAsString(), jsonObject.get("description").getAsString());
    }

    @Override
    public JsonElement serialize(Tag src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        if (!alreadySaved.contains(src.getId())) {
            alreadySaved.add(src.getId());
            jsonObject.add("id", new JsonPrimitive(src.getId()));
            jsonObject.add("name", new JsonPrimitive(src.getName()));
            jsonObject.add("description", new JsonPrimitive(src.getDescription()));
        }
        return jsonObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagTypeAdapter that = (TagTypeAdapter) o;
        return Objects.equals(alreadySaved, that.alreadySaved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alreadySaved);
    }

    @Override
    public String toString() {
        return "TagTypeAdapter{" +
                "alreadySaved=" + alreadySaved +
                '}';
    }
}
