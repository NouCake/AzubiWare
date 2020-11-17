package de.united.azubiware.Packets;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;

public interface IPacket {

    default String toJsonString() {
        return new Gson().toJson(this);
    };

}
