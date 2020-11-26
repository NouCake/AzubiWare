package de.united.azubiware.connection.match;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Games.SSP.Packets.SSPRoundOverPacket;
import de.united.azubiware.Games.SSP.Packets.SSPRoundStartPacket;
import de.united.azubiware.Packets.Handler.APacketHandler;

public class SSPClientPacketHandler extends APacketHandler {

    private ISSPListener listener;

    public SSPClientPacketHandler(ISSPListener listener) {
        this.listener = listener;
    }

    public void onRoundOver(IConnection c, SSPRoundOverPacket packet){
        listener.onRoundResult(packet.getEnemyPick(), packet.getResult());
    }

    public void onRoundStart(IConnection c, SSPRoundStartPacket packet){
        listener.onRoundStart(packet.getRound());
    }

}
