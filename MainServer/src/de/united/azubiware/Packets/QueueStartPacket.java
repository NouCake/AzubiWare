package de.united.azubiware.Packets;

import com.google.gson.annotations.SerializedName;

public class QueueStartPacket implements IPacket {

    public static final int type = 5;

    @SerializedName("matchtype")
    public final int matchType;

    public QueueStartPacket(int matchType) {
        this.matchType = matchType;
    }

    public int getMatchType() {
        return matchType;
    }
}
