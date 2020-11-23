package de.united.azubiware.Packets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface IPacket {

    default String toJsonString() {
        GsonBuilder gsonBuilder  = new GsonBuilder();
        gsonBuilder.excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT);
        Gson gson = gsonBuilder.create();
        String json = gson.toJson(this);
        return json;
    }

}
