package de.united.azubiware.Connection;

import de.united.azubiware.Packets.Handler.APacketHandler;
import de.united.azubiware.Packets.MatchReadyPacket;

public class MatchPacketHandler extends APacketHandler {

    private final MatchClient matchClient;

    public MatchPacketHandler(MatchClient matchClient) {
        this.matchClient = matchClient;
    }

    public void onMatchReadyPacket(IConnection c, MatchReadyPacket p){
        matchClient.onReady();
    }

}
