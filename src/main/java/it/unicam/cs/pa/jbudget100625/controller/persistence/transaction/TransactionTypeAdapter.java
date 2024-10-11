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

package it.unicam.cs.pa.jbudget100625.controller.persistence.transaction;

import com.google.gson.*;
import it.unicam.cs.pa.jbudget100625.model.movement.Movement;
import it.unicam.cs.pa.jbudget100625.model.movement.MovementImplementation;
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;
import it.unicam.cs.pa.jbudget100625.model.tag.TagImplementation;
import it.unicam.cs.pa.jbudget100625.model.transaction.Transaction;
import it.unicam.cs.pa.jbudget100625.model.transaction.TransactionImplementation;

import java.lang.reflect.Type;
import java.util.*;

public class TransactionTypeAdapter implements JsonSerializer<Transaction>, JsonDeserializer<Transaction> {
    private Set<Integer> alreadySaved = new HashSet<>();

    @Override
    public Transaction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject.get("lazyId") != null)
            return TransactionImplementation.getInstance(jsonObject.get("lazyId").getAsInt(), null, null, null);
        else {
            JsonArray array = context.deserialize(jsonObject.get("tags"), JsonArray.class);
            JsonArray movementArray = context.deserialize(jsonObject.get("movements"), JsonArray.class);
            List<Movement> movements = new ArrayList<>();
            List<Tag> tags = new ArrayList<>();
            array.forEach(element -> tags.add(context.deserialize(element, TagImplementation.class)));
            movementArray.forEach(element -> movements.add(context.deserialize(element, MovementImplementation.class)));
            return TransactionImplementation.getInstance(jsonObject.get("id").getAsInt(), context.deserialize(jsonObject.get("date"), Date.class), tags, movements);
        }
    }

    @Override
    public JsonElement serialize(Transaction src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        if (!alreadySaved.contains(src.getId())) {
            alreadySaved.add(src.getId());
            jsonObject.add("id", new JsonPrimitive(src.getId()));
            jsonObject.add("date", context.serialize(src.getDate()));
            jsonObject.add("tags", context.serialize(src.tags()));
            if (src.movements() != null) jsonObject.add("movements", context.serialize(src.movements()));
        } else jsonObject.add("lazyId", new JsonPrimitive(src.getId()));
        return jsonObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionTypeAdapter that = (TransactionTypeAdapter) o;
        return Objects.equals(alreadySaved, that.alreadySaved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alreadySaved);
    }

    @Override
    public String toString() {
        return "TransactionTypeAdapter{" +
                "alreadySaved=" + alreadySaved +
                '}';
    }
}
