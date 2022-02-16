package de.az.ware.common.packets;

import de.az.ware.connection.packet.Packet;

public class MatchLoginPacket implements Packet {

    private final String matchtoken;

    public MatchLoginPacket(String matchtoken) {
        this.matchtoken = matchtoken;
    }

    public String getMatchtoken() {
        return matchtoken;
    }

}
