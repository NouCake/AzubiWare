package de.united.azubiware.Packets;

public class QueuePollPacket implements IPacket{

    private final int matchType;

    public QueuePollPacket(int matchType) {
        this.matchType = matchType;
    }

    public int getMatchType() {
        return matchType;
    }
}
