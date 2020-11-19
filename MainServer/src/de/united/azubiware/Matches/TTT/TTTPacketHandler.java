package de.united.azubiware.Matches.TTT;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Matches.MatchUser;
import de.united.azubiware.Packets.ErrorResponsePacket;
import de.united.azubiware.Packets.Handler.APacketHandler;
import de.united.azubiware.Packets.TTTPacket;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class TTTPacketHandler extends APacketHandler {

    private TTTMatch match;

    public TTTPacketHandler(){

    }

    public void setMatch(TTTMatch match) {
        this.match = match;
    }

    public void onTTTPacket(IConnection connection, TTTPacket packet){
        if(!match.isMatchStarted()){
            connection.send(new ErrorResponsePacket("Nicht so schnell mein junger Freund!"));
            return;
        }
        MatchUser user = match.getPlayerFromConnection(connection);
        match.doPlayerTurn(user, packet.getFieldX(), packet.getFieldY());
    }

}
