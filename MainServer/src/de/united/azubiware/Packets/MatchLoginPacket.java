package de.united.azubiware.Packets;

import com.google.gson.annotations.SerializedName;

public class MatchLoginPacket implements IPacket{

    public static final int type = 8;
    @SerializedName("matchtoken")
    private final String token;

    public MatchLoginPacket(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
