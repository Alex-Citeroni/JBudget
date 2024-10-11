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

package it.unicam.cs.pa.jbudget100625.controller.persistence.account;

import com.google.gson.*;
import it.unicam.cs.pa.jbudget100625.model.account.Account;
import it.unicam.cs.pa.jbudget100625.model.account.AccountImplementation;
import it.unicam.cs.pa.jbudget100625.model.account.AccountType;
import it.unicam.cs.pa.jbudget100625.model.movement.Movement;
import it.unicam.cs.pa.jbudget100625.model.movement.MovementImplementation;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Alex Citeroni
 */
public class AccountTypeAdapter implements JsonSerializer<Account>, JsonDeserializer<Account> {
    private Set<Integer> alreadySaved = new HashSet<>();

    @Override
    public JsonElement serialize(Account src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        if (!alreadySaved.contains(src.getId())) {
            alreadySaved.add(src.getId());
            jsonObject.add("id", new JsonPrimitive(src.getId()));
            jsonObject.add("accountType", context.serialize(src.getType()));
            jsonObject.add("name", new JsonPrimitive(src.getName()));
            jsonObject.add("description", new JsonPrimitive(src.getDescription()));
            jsonObject.add("openingBalance", new JsonPrimitive(src.getOpeningBalance()));
            if (src.getMovements() != null) jsonObject.add("movements", context.serialize(src.getMovements()));
        } else jsonObject.add("lazyId", new JsonPrimitive(src.getId()));
        return jsonObject;
    }

    @Override
    public Account deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject.get("lazyId") != null)
            return AccountImplementation.getInstance(jsonObject.get("lazyId").getAsInt(), null, null, null, 0.0);
        else {
            Account account =  AccountImplementation.getInstance(jsonObject.get("id").getAsInt(), context.deserialize(jsonObject.get("accountType"), AccountType.class), jsonObject.get("name").getAsString(), jsonObject.get("description").getAsString(), jsonObject.get("openingBalance").getAsDouble());
            JsonArray movementArray = context.deserialize(jsonObject.get("movements"), JsonArray.class);
            List<Movement> movements = new ArrayList<>();
            movementArray.forEach(element -> movements.add(context.deserialize(element, MovementImplementation.class)));
            account.getMovements().addAll(movements);
            return account;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountTypeAdapter that = (AccountTypeAdapter) o;
        return Objects.equals(alreadySaved, that.alreadySaved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alreadySaved);
    }

    @Override
    public String toString() {
        return "AccountTypeAdapter{" +
                "alreadySaved=" + alreadySaved +
                '}';
    }
}
