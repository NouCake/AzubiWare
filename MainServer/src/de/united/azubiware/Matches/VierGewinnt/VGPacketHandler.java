package de.united.azubiware.Matches.VierGewinnt;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Matches.MatchUser;
import de.united.azubiware.Packets.ErrorResponsePacket;
import de.united.azubiware.Packets.Handler.APacketHandler;
import de.united.azubiware.Packets.TTTPacket;

public class VGPacketHandler extends APacketHandler {

    private final VGMatch match;

    public VGPacketHandler(VGMatch match) {
        this.match = match;
    }

    public void onVGPacket(IConnection connection, TTTPacket packet){
        if(!match.isMatchStarted()){
            connection.send(new ErrorResponsePacket("mot so fast my young friend"));
            return;
        }
        MatchUser user = match.getPlayerFromConnection(connection);
        match.doPlayerTurn(user, packet.getFieldX());
    }
}
