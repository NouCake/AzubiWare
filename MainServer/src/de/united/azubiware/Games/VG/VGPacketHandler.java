package de.united.azubiware.Games.VG;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Matches.MatchUser;
import de.united.azubiware.Packets.ErrorResponsePacket;
import de.united.azubiware.Packets.Handler.APacketHandler;

public class VGPacketHandler extends APacketHandler {

    private final de.united.azubiware.Games.VG.VGMatch match;

    public VGPacketHandler(VGMatch match) {
        this.match = match;
    }

    public void onVGPacket(IConnection connection, VGPacket packet){
        if(!match.isMatchStarted()){
            connection.send(new ErrorResponsePacket("not so fast my young friend!"));
            return;
        }
        MatchUser user = match.getPlayerFromConnection(connection);
        match.doPlayerTurn(user, packet.getFieldX());
    }

    public void onHint(IConnection connection, VGTurnHint packet){
        if(!match.isMatchStarted()){
            connection.send(new ErrorResponsePacket("not so fast my young friend!"));
            return;
        }
        MatchUser user = match.getPlayerFromConnection(connection);
        int otherPlayerIndex = (user.getPlayerIndex() % 2) + 1;
        match.getPlayerFromIndex(otherPlayerIndex).send(packet);
    }

}
