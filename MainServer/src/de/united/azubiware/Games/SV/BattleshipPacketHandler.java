package de.united.azubiware.Games.SV;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Matches.MatchUser;
import de.united.azubiware.Packets.ErrorResponsePacket;
import de.united.azubiware.Packets.Handler.APacketHandler;

public class BattleshipPacketHandler extends APacketHandler {

    private final BattleshipMatch match;

    public BattleshipPacketHandler(BattleshipMatch match) {
        this.match = match;
    }

    public void onTurnPacket(IConnection c, BattleshipTurnPacket packet){
        MatchUser user = match.getPlayerFromConnection(c);
        if(user == null) return;
        match.playerTurn(user.getPlayerIndex(), packet.getCellX(), packet.getCellY());
    }

}
