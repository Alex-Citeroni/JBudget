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

package it.unicam.cs.pa.jbudget100625.controller.persistence.movement;

import com.google.gson.*;
import it.unicam.cs.pa.jbudget100625.model.account.AccountImplementation;
import it.unicam.cs.pa.jbudget100625.model.movement.Movement;
import it.unicam.cs.pa.jbudget100625.model.movement.MovementImplementation;
import it.unicam.cs.pa.jbudget100625.model.movement.MovementType;
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;
import it.unicam.cs.pa.jbudget100625.model.tag.TagImplementation;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Alex Citeroni
 */
public class MovementTypeAdapter implements JsonSerializer<Movement>, JsonDeserializer<Movement> {
    private Set<Integer> alreadySaved = new HashSet<>();

    @Override
    public JsonElement serialize(Movement src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        if (!alreadySaved.contains(src.getId())) {
            alreadySaved.add(src.getId());
            jsonObject.add("id", new JsonPrimitive(src.getId()));
            jsonObject.add("account", context.serialize(src.getAccount()));
            jsonObject.add("description", new JsonPrimitive(src.getDescription()));
            jsonObject.add("type", context.serialize(src.getType()));
            jsonObject.add("tags", context.serialize(src.getTags()));
            jsonObject.add("amount", new JsonPrimitive(src.getAmount()));
            if (src.getAccount() != null) jsonObject.add("movements", context.serialize(src.getAccount()));
        } else jsonObject.add("lazyId", new JsonPrimitive(src.getId()));
        return jsonObject;
    }

    @Override
    public Movement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject.get("lazyId") != null)
            return MovementImplementation.getInstance(jsonObject.get("lazyId").getAsInt(), null, "", null, null, 0.0);
        else {
            JsonArray array = context.deserialize(jsonObject.get("tags"), JsonArray.class);
            List<Tag> tags = new ArrayList<>();
            array.forEach(element -> tags.add(context.deserialize(element, TagImplementation.class)));
            return MovementImplementation.getInstance(jsonObject.get("id").getAsInt(), context.deserialize(jsonObject.get("account"), AccountImplementation.class), jsonObject.get("description").getAsString(), context.deserialize(jsonObject.get("type"), MovementType.class), tags, jsonObject.get("amount").getAsDouble());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovementTypeAdapter that = (MovementTypeAdapter) o;
        return Objects.equals(alreadySaved, that.alreadySaved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alreadySaved);
    }

    @Override
    public String toString() {
        return "MovementTypeAdapter{" +
                "alreadySaved=" + alreadySaved +
                '}';
    }
}
