package de.united.azubiware.Matches;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Packets.Handler.APacketHandler;
import de.united.azubiware.Packets.MatchLoginPacket;

import java.util.UUID;

public class MatchPacketHandler extends APacketHandler {

    private final AMatch match;

    public MatchPacketHandler(AMatch match) {
        this.match = match;
    }

    public void onMatchLogin(IConnection connection, MatchLoginPacket packet){
        match.onUserConnected(connection, UUID.fromString(packet.getToken()));
    }

}
