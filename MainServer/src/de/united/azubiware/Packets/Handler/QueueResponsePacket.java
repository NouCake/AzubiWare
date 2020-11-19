package de.united.azubiware.Packets.Handler;

import de.united.azubiware.Packets.IPacket;

public class QueueResponsePacket implements IPacket {

    public static final int type = 12;
    private final int matchType;
    private final int queueLength;

    public QueueResponsePacket(int matchType, int queueLength) {
        this.matchType = matchType;
        this.queueLength = queueLength;
    }

    public int getMatchType() {
        return matchType;
    }

    public int getQueueLength() {
        return queueLength;
    }
}
