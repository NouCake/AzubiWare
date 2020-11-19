package de.united.azubiware.Packets;

public class QueuePollPacket implements IPacket{

    public static final int type = 11;
    private final int matchType;

    public QueuePollPacket(int matchType) {
        this.matchType = matchType;
    }

    public int getMatchType() {
        return matchType;
    }
}
