package de.united.azubiware.connection.match;

import de.united.azubiware.Connection.IConnection;
import de.united.azubiware.Packets.Handler.APacketHandler;
import de.united.azubiware.Packets.TTTIllegalTurnPacket;
import de.united.azubiware.Packets.TTTNextTurnPacket;
import de.united.azubiware.Packets.TTTPacket;
import de.united.azubiware.connection.match.ITTTListener;

public class TTTClientPacketHandler extends APacketHandler {

    private final ITTTListener listener;

    public TTTClientPacketHandler(ITTTListener listener) {
        this.listener = listener;
    }

    public void onNextTurn(IConnection c, TTTNextTurnPacket packet){
        listener.onNextTurn(packet.isYourTurn());
    }

    public void onIllegalTurn(IConnection c, TTTIllegalTurnPacket packet){
        System.out.println("You did some bad Move :c");
        System.out.println(packet.getMessage());
        listener.onInvalidTurn();
    }

    public void onEnemyTurn(IConnection c, TTTPacket packet){
        listener.onEnemyTurn(packet.getFieldX(), packet.getFieldY());
    }

}