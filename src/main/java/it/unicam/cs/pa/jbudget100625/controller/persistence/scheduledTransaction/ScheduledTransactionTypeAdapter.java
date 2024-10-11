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

package it.unicam.cs.pa.jbudget100625.controller.persistence.scheduledTransaction;

import com.google.gson.*;
import it.unicam.cs.pa.jbudget100625.model.scheduledTransaction.ScheduledTransaction;
import it.unicam.cs.pa.jbudget100625.model.scheduledTransaction.ScheduledTransactionImplementation;
import it.unicam.cs.pa.jbudget100625.model.transaction.Transaction;
import it.unicam.cs.pa.jbudget100625.model.transaction.TransactionImplementation;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Alex Citeroni
 */
public class ScheduledTransactionTypeAdapter implements JsonSerializer<ScheduledTransaction>, JsonDeserializer<ScheduledTransaction> {
    private Set<Integer> alreadySaved = new HashSet<>();

    @Override
    public ScheduledTransaction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray array = context.deserialize(jsonObject.get("transactions"), JsonArray.class);
        List<Transaction> transactions = new ArrayList<>();
        array.forEach(element -> transactions.add(context.deserialize(element, TransactionImplementation.class)));
        return ScheduledTransactionImplementation.getInstance(jsonObject.get("id").getAsInt(), jsonObject.get("description").getAsString(), transactions);
    }

    @Override
    public JsonElement serialize(ScheduledTransaction src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        if (!alreadySaved.contains(src.getId())) {
            alreadySaved.add(src.getId());
            jsonObject.add("id", new JsonPrimitive(src.getId()));
            jsonObject.add("description", new JsonPrimitive(src.getDescription()));
           // jsonObject.add("transaction", context.serialize(src.getTransactions(date)));
        }
        return jsonObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledTransactionTypeAdapter that = (ScheduledTransactionTypeAdapter) o;
        return Objects.equals(alreadySaved, that.alreadySaved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alreadySaved);
    }

    @Override
    public String toString() {
        return "ScheduledTransactionTypeAdapter{" +
                "alreadySaved=" + alreadySaved +
                '}';
    }
}
