package de.united.azubiware.Games.TTT;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Matches.MatchUser;
import de.united.azubiware.Packets.ErrorResponsePacket;
import de.united.azubiware.Packets.Handler.APacketHandler;

public class TTTPacketHandler extends APacketHandler {

    private final TTTMatch match;

    public TTTPacketHandler(TTTMatch match){
        this.match = match;
    }


    public void onTTTPacket(IConnection connection, TTTPacket packet){
        if(!match.isMatchStarted()){
            connection.send(new ErrorResponsePacket("Nicht so schnell mein junger Freund!"));
            return;
        }
        MatchUser user = match.getPlayerFromConnection(connection);
        if(user == null){
            System.out.println("Received Packet from Invalid User");
            return;
        }
        match.doPlayerTurn(user, packet.getFieldX(), packet.getFieldY());
    }

}
