package de.united.azubiware.Games.Pong;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Matches.MatchUser;
import de.united.azubiware.Packets.Handler.APacketHandler;

public class PongPacketHandler extends APacketHandler {

    private final PongMatch match;

    public PongPacketHandler(PongMatch match) {
        this.match = match;
    }

    public void onUpdatePacket(IConnection c, PongPlayerUpdatePacket packet){
        MatchUser user = match.getPlayerFromConnection(c);
        if(user == null){
            System.out.println("Got Packet from Invalid User :C");
            return;
        }
        match.onPlayerUpdate(user.getPlayerIndex(), packet.getX());
    }

}
