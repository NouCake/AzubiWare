package de.united.azubiware.Games.SSP;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Games.SSP.Packets.SSPPacket;
import de.united.azubiware.Matches.MatchUser;
import de.united.azubiware.Packets.ErrorResponsePacket;
import de.united.azubiware.Packets.Handler.APacketHandler;

public class SSPPacketHandler extends APacketHandler {

    private SSPMatch match;

    public SSPPacketHandler(SSPMatch sspMatch){
        this.match = sspMatch;
    }

    public void onSSPPacket(IConnection connection, SSPPacket packet){
        if(!match.isMatchStarted()){
            connection.send(new ErrorResponsePacket("Nicht so schnell mein junger Freund!"));
            return;
        }
        MatchUser user = match.getPlayerFromConnection(connection);
        if(user == null){
            System.out.println("Received Packet from Invalid User");
            return;
        }
        match.doPlayerPick(user, packet.getPickType());
    }

}
