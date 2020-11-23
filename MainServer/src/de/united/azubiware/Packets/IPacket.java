package de.united.azubiware.Packets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public interface IPacket {

    default String toJsonString() {
        GsonBuilder gsonBuilder  = new GsonBuilder();
        gsonBuilder.excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT);
        Gson gson = gsonBuilder.create();
        JsonElement tree = gson.toJsonTree(this);
        tree.getAsJsonObject().addProperty("type", getClass().getSimpleName());
        return gson.toJson(tree);
    }

}
