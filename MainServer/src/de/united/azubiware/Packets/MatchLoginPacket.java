package de.united.azubiware.Packets;

import com.google.gson.annotations.SerializedName;

public class MatchLoginPacket implements IPacket{

    @SerializedName("matchtoken")
    private final String token;

    public MatchLoginPacket(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
